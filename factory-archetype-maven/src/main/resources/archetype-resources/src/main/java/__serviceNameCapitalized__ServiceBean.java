#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import java.util.UUID;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ws.annotation.EndpointConfig;
import org.jboss.wsf.spi.annotation.WebContext;
import org.qualipso.factory.FactoryException;
import org.qualipso.factory.FactoryNamingConvention;
import org.qualipso.factory.FactoryResource;
import org.qualipso.factory.FactoryResourceIdentifier;
import org.qualipso.factory.FactoryResourceProperty;
import org.qualipso.factory.binding.BindingService;
import org.qualipso.factory.binding.InvalidPathException;
import org.qualipso.factory.binding.PathAlreadyBoundException;
import org.qualipso.factory.binding.PathHelper;
import org.qualipso.factory.binding.PathNotEmptyException;
import org.qualipso.factory.binding.PathNotFoundException;
import org.qualipso.factory.indexing.IndexableContent;
import org.qualipso.factory.indexing.IndexableService;
import org.qualipso.factory.indexing.IndexingService;
import org.qualipso.factory.membership.MembershipService;
import org.qualipso.factory.membership.MembershipServiceException;
import org.qualipso.factory.notification.Event;
import org.qualipso.factory.notification.NotificationService;
import org.qualipso.factory.security.SecurityHelper;
import org.qualipso.factory.security.pap.PAPService;
import org.qualipso.factory.security.pep.AccessDeniedException;
import org.qualipso.factory.security.pep.PEPService;
import ${package}.entity.${resourceNameCapitalized};


@Stateless(name = ${serviceNameCapitalized}Service.SERVICE_NAME, mappedName = FactoryNamingConvention.SERVICE_PREFIX + ${serviceNameCapitalized}Service.SERVICE_NAME)
@WebService(endpointInterface = "${package}.${serviceNameCapitalized}Service", targetNamespace = FactoryNamingConvention.SERVICE_NAMESPACE + ${serviceNameCapitalized}Service.SERVICE_NAME, serviceName = ${serviceNameCapitalized}Service.SERVICE_NAME)
@WebContext(contextRoot = FactoryNamingConvention.WEB_SERVICE_ROOT_MODULE_CONTEXT + "-" + ${serviceNameCapitalized}Service.SERVICE_NAME, urlPattern = FactoryNamingConvention.WEB_SERVICE_URL_PATTERN_PREFIX + ${serviceNameCapitalized}Service.SERVICE_NAME)
@SOAPBinding(style = Style.RPC)
@SecurityDomain(value = "JBossWSDigest")
@EndpointConfig(configName = "Standard WSSecurity Endpoint")
public class ${serviceNameCapitalized}ServiceBean implements ${serviceNameCapitalized}Service, IndexableService {
    private static Log logger = LogFactory.getLog(${serviceNameCapitalized}ServiceBean.class);
    private BindingService binding;
    private PEPService pep;
    private PAPService pap;
    private NotificationService notification;
    private MembershipService membership;
    private IndexingService indexing;
    private SessionContext ctx;
    private EntityManager em;

    public ${serviceNameCapitalized}ServiceBean() {
    }

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return this.em;
    }

    @Resource
    public void setSessionContext(SessionContext ctx) {
        this.ctx = ctx;
    }

    public SessionContext getSessionContext() {
        return this.ctx;
    }

    @EJB
    public void setBindingService(BindingService binding) {
        this.binding = binding;
    }

    public BindingService getBindingService() {
        return this.binding;
    }

    @EJB
    public void setPEPService(PEPService pep) {
        this.pep = pep;
    }

    public PEPService getPEPService() {
        return this.pep;
    }

    @EJB
    public void setPAPService(PAPService pap) {
        this.pap = pap;
    }

    public PAPService getPAPService() {
        return this.pap;
    }

    @EJB
    public void setNotificationService(NotificationService notification) {
        this.notification = notification;
    }

    public NotificationService getNotificationService() {
        return this.notification;
    }

    @EJB
    public void setIndexingService(IndexingService indexing) {
        this.indexing = indexing;
    }

    public IndexingService getIndexingService() {
        return this.indexing;
    }

    @EJB
    public void setMembershipService(MembershipService membership) {
        this.membership = membership;
    }

    public MembershipService getMembershipService() {
        return this.membership;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void create${resourceNameCapitalized}(String path, String name) throws ${serviceNameCapitalized}ServiceException, AccessDeniedException, InvalidPathException, PathAlreadyBoundException {
        logger.info("create${resourceNameCapitalized}(...) called");
        logger.debug("params : path=" + path + ", name=" + name);

        try {
            //Checking if the connected user has the permission to create a resource giving pep : 
            //  - the profile path of the connected user (caller)
            //  - the parent of the path (we check the 'create' permission on the parent of the given path)
            //  - the name of the permission to check ('create')
            String caller = membership.getProfilePathForConnectedIdentifier();
            pep.checkSecurity(membership.getConnectedIdentifierSubjects(), PathHelper.getParentPath(path), "create");

            //STARTING SPECIFIC EXTERNAL SERVICE RESOURCE CREATION OR METHOD CALL
            ${resourceNameCapitalized} ${resourceName} = new ${resourceNameCapitalized}();
            ${resourceName}.setId(UUID.randomUUID().toString());
            ${resourceName}.setName(name);
            em.persist(${resourceName});
            //END OF EXTERNAL INVOCATION

            //Binding the external resource in the naming using the generated resource ID : 
            binding.bind(${resourceName}.getFactoryResourceIdentifier(), path);

            //Need to set some properties on the node : 
            binding.setProperty(path, FactoryResourceProperty.CREATION_TIMESTAMP, "" + System.currentTimeMillis());
            binding.setProperty(path, FactoryResourceProperty.LAST_UPDATE_TIMESTAMP, "" + System.currentTimeMillis());
            binding.setProperty(path, FactoryResourceProperty.AUTHOR, caller);

            //Need to create a new security policy for this resource : 
            //Giving the caller the Owner permission (aka all permissions)
            String policyId = UUID.randomUUID().toString();
            pap.createPolicy(policyId, SecurityHelper.buildPolicy(policyId, caller, path));

            //Setting security properties on the node : 
            binding.setProperty(path, FactoryResourceProperty.OWNER, caller);
            binding.setProperty(path, FactoryResourceProperty.POLICY_ID, policyId);

            //Using the notification service to throw an event : 
            notification.throwEvent(new Event(path, caller, ${resourceNameCapitalized}.RESOURCE_NAME, Event.buildEventType(${serviceNameCapitalized}Service.SERVICE_NAME, ${resourceNameCapitalized}.RESOURCE_NAME, "create"), ""));
            
            indexing.index(path);
      } catch (AccessDeniedException e) {
			  ctx.setRollbackOnly();
			  throw e;
		  } catch (InvalidPathException e) {
			  ctx.setRollbackOnly();
			  throw e;
		  } catch (PathAlreadyBoundException e) {
			  ctx.setRollbackOnly();
			  throw e;
		  } catch (FactoryException e) {
			  ctx.setRollbackOnly();
			  throw new ${serviceNameCapitalized}ServiceException("unable to create the ${resourceName} at path " + path, e);
		  }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ${resourceNameCapitalized} read${resourceNameCapitalized}(String path) throws ${serviceNameCapitalized}ServiceException, AccessDeniedException, InvalidPathException, PathNotFoundException {
        return read${resourceNameCapitalized}(path, false);
    }
    
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    private ${resourceNameCapitalized} read${resourceNameCapitalized}(String path, boolean bypassSecurity) throws ${serviceNameCapitalized}ServiceException, AccessDeniedException, InvalidPathException, PathNotFoundException {
    	  logger.info("read${resourceNameCapitalized}(...) called");
        logger.debug("params : path=" + path);

        try {
            //Checking if the connected user has the permission to read the resource giving pep : 
            String caller = membership.getProfilePathForConnectedIdentifier();
            
            if ( !bypassSecurity ) {
            	pep.checkSecurity(membership.getConnectedIdentifierSubjects(), path, "read");
            }

            //Performing a lookup in the naming to recover the Resource Identifier 
            FactoryResourceIdentifier identifier = binding.lookup(path);

            //Checking if this resource identifier is really a resource managed by this service (a ${resourceNameCapitalized} resource)
            checkResourceType(identifier, ${resourceNameCapitalized}.RESOURCE_NAME);

            //STARTING SPECIFIC EXTERNAL SERVICE RESOURCE LOADING OR METHOD CALLS
            ${resourceNameCapitalized} ${resourceName} = em.find(${resourceNameCapitalized}.class, identifier.getId());

            if (${resourceName} == null) {
                throw new ${serviceNameCapitalized}ServiceException("unable to find a ${resourceName} for id " + identifier.getId());
            }

            ${resourceName}.setResourcePath(path);
            //END OF EXTERNAL SERVICE INVOCATION

            //Using the notification service to throw an event : 
            notification.throwEvent(new Event(path, caller, ${resourceNameCapitalized}.RESOURCE_NAME, Event.buildEventType(${serviceNameCapitalized}Service.SERVICE_NAME, ${resourceNameCapitalized}.RESOURCE_NAME, "read"), ""));

            return resource;
        } catch (AccessDeniedException e) {
			      throw e;
		    } catch (InvalidPathException e) {
			      throw e;
		    } catch (PathNotFoundException e) {
			      throw e;
		    } catch (FactoryException e) {
			      throw new ${serviceNameCapitalized}ServiceException("unable to read the ${resourceName} at path " + path, e);
		    }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void update${resourceNameCapitalized}(String path, String name) throws ${serviceNameCapitalized}ServiceException, AccessDeniedException, InvalidPathException, PathNotFoundException {
        logger.info("update${resourceNameCapitalized}(...) called");
        logger.debug("params : path=" + path + ", name=" + name);

        try {
            //Checking if the connected user has the permission to update the resource giving pep : 
            String caller = membership.getProfilePathForConnectedIdentifier();
            pep.checkSecurity(membership.getConnectedIdentifierSubjects(), path, "update");

            //Performing a lookup in the naming to recover the Resource Identifier 
            FactoryResourceIdentifier identifier = binding.lookup(path);

            //Checking if this resource identifier is really a resource managed by this service (a ${resourceNameCapitalized} resource)
            checkResourceType(identifier, ${resourceNameCapitalized}.RESOURCE_NAME);

            //STARTING SPECIFIC EXTERNAL SERVICE RESOURCE LOADING OR METHOD CALLS
            ${resourceNameCapitalized} ${resourceName} = em.find(${resourceNameCapitalized}.class, identifier.getId());

            if (${resourceName} == null) {
                throw new ${serviceNameCapitalized}ServiceException("unable to find a ${resourceName} for id " + identifier.getId());
            }

            ${resourceName}.setName(name);
            em.merge(${resourceName});
            //END OF EXTERNAL SERVICE INVOCATION

            //Need to set some properties on the node : 
            binding.setProperty(path, FactoryResourceProperty.LAST_UPDATE_TIMESTAMP, System.currentTimeMillis() + "");

            //Using the notification service to throw an event : 
            notification.throwEvent(new Event(path, caller, ${resourceNameCapitalized}.RESOURCE_NAME, Event.buildEventType(${serviceNameCapitalized}Service.SERVICE_NAME, ${resourceNameCapitalized}.RESOURCE_NAME, "update"), ""));
            
            indexing.reindex(path);
        } catch (AccessDeniedException e) {
			      ctx.setRollbackOnly();
			      throw e;
		    } catch (InvalidPathException e) {
			      ctx.setRollbackOnly();
			      throw e;
		    } catch (PathNotFoundException e) {
			      ctx.setRollbackOnly();
			      throw e;
		    } catch (FactoryException e) {
			      ctx.setRollbackOnly();
			      throw new ${serviceNameCapitalized}ServiceException("unable to update the ${resourceName} at path " + path, e);
		    }
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete${resourceNameCapitalized}(String path) throws ${serviceNameCapitalized}ServiceException, AccessDeniedException, InvalidPathException, PathNotFoundException, PathNotEmptyException {
        logger.info("delete${resourceNameCapitalized}(...) called");
        logger.debug("params : path=" + path);

        try {
            //Checking if the connected user has the permission to delete the resource giving pep : 
            String caller = membership.getProfilePathForConnectedIdentifier();
            pep.checkSecurity(membership.getConnectedIdentifierSubjects(), path, "delete");

            //Performing a lookup in the naming to recover the Resource Identifier 
            FactoryResourceIdentifier identifier = binding.lookup(path);

            //Checking if this resource identifier is really a resource managed by this service (a ${resourceNameCapitalized} resource)
            checkResourceType(identifier, ${resourceNameCapitalized}.RESOURCE_NAME);

            //STARTING SPECIFIC EXTERNAL SERVICE RESOURCE LOADING OR METHOD CALLS
            ${resourceNameCapitalized} ${resourceName} = em.find(${resourceNameCapitalized}.class, identifier.getId());

            if (${resourceName} == null) {
                throw new ${serviceNameCapitalized}ServiceException("unable to find a ${resourceName} for id " + identifier.getId());
            }

            em.remove(${resourceName});
            //END OF EXTERNAL SERVICE INVOCATION

            //Removing the security policy of this node : 
            String policyId = binding.getProperty(path, FactoryResourceProperty.POLICY_ID, false);
            pap.deletePolicy(policyId);

            //Unbinding the resource from this path : 
            binding.unbind(path);

            //Using the notification service to throw an event : 
            notification.throwEvent(new Event(path, caller, ${resourceNameCapitalized}.RESOURCE_NAME, Event.buildEventType(${serviceNameCapitalized}Service.SERVICE_NAME, ${resourceNameCapitalized}.RESOURCE_NAME, "delete"), ""));
            
            indexing.remove(path);
        } catch (AccessDeniedException e) {
			      ctx.setRollbackOnly();
			      throw e;
		    } catch (InvalidPathException e) {
			      ctx.setRollbackOnly();
			      throw e;
		    } catch (PathNotFoundException e) {
			      ctx.setRollbackOnly();
			      throw e;
		    } catch (PathNotEmptyException e) {
			      ctx.setRollbackOnly();
			      throw e;
		    } catch (FactoryException e) {
			      ctx.setRollbackOnly();
			      throw new ${serviceNameCapitalized}ServiceException("unable to delete the ${resourceName} at path " + path, e);
		    }
    }

    private void checkResourceType(FactoryResourceIdentifier identifier, String resourceType)
        throws MembershipServiceException {
        if (!identifier.getService().equals(getServiceName())) {
            throw new MembershipServiceException("resource identifier " + identifier + " does not refer to service " + getServiceName());
        }

        if (!identifier.getType().equals(resourceType)) {
            throw new MembershipServiceException("resource identifier " + identifier + " does not refer to a resource of type " + resourceType);
        }
    }

    @Override
    public String[] getResourceTypeList() {
        return RESOURCE_TYPE_LIST;
    }

    @Override
    public String getServiceName() {
        return SERVICE_NAME;
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public FactoryResource findResource(String path) throws FactoryException {
        logger.info("findResource(...) called");
        logger.debug("params : path=" + path);

        try {
            FactoryResourceIdentifier identifier = binding.lookup(path);

            if (!identifier.getService().equals(${serviceNameCapitalized}Service.SERVICE_NAME)) {
                throw new ${serviceNameCapitalized}ServiceException("Resource " + identifier + " is not managed by " + ${serviceNameCapitalized}Service.SERVICE_NAME);
            }

            if (identifier.getType().equals(${resourceNameCapitalized}.RESOURCE_NAME)) {
                return readMyResource(path);
            }

            throw new ${serviceNameCapitalized}ServiceException("Resource " + identifier + " is not managed by service " + ${serviceNameCapitalized}Service.SERVICE_NAME);
        } catch (Exception e) {
            logger.error("unable to find the resource at path " + path, e);
            throw new ${serviceNameCapitalized}ServiceException("unable to find the resource at path " + path, e);
        }
    }
    
    @Override
    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public IndexableContent getIndexableContent(String path) throws FactoryException {
        logger.info("getIndexableContent(...) called");
        logger.debug("params : path=" + path);
        
        FactoryResourceIdentifier identifier = binding.lookup(path);
        
        if (!identifier.getService().equals(${serviceNameCapitalized}Service.SERVICE_NAME)) {
			    throw new ${serviceNameCapitalized}ServiceException("Resource " + identifier + " is not managed by service " + ${serviceNameCapitalized}Service.SERVICE_NAME);
		    }

        IndexableContent content = new IndexableContent();
        
        if (identifier.getType().equals(MyResource.RESOURCE_NAME)) {
			    ${resourceNameCapitalized} ${resourceName} = read${resourceNameCapitalized}(path, true);
			    content.addContentPart(${resourceName}.getName());
			    return content;
		    }

		    throw new ${serviceNameCapitalized}ServiceException("resource " + identifier + " is not managed by service " + ${serviceNameCapitalized}Service.SERVICE_NAME);
    }
}
