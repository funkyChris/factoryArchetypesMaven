#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import javax.ejb.Remote;
import javax.jws.WebMethod;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.qualipso.factory.FactoryNamingConvention;
import org.qualipso.factory.FactoryService;
import org.qualipso.factory.binding.InvalidPathException;
import org.qualipso.factory.binding.PathAlreadyBoundException;
import org.qualipso.factory.binding.PathNotEmptyException;
import org.qualipso.factory.binding.PathNotFoundException;
import org.qualipso.factory.security.pep.AccessDeniedException;
import ${package}.entity.${resourceNameCapitalized};

@Remote
@WebService(name = ${serviceNameCapitalized}Service.SERVICE_NAME, targetNamespace = FactoryNamingConvention.SERVICE_NAMESPACE + ${serviceNameCapitalized}Service.SERVICE_NAME)
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface ${serviceNameCapitalized}Service extends FactoryService {
    public static final String SERVICE_NAME = "${serviceName}";
    public static final String[] RESOURCE_TYPE_LIST = new String[] { ${resourceNameCapitalized}.RESOURCE_NAME };

    @WebMethod
    public void create${resourceNameCapitalized}(String path, String name) throws ${serviceNameCapitalized}ServiceException, AccessDeniedException, InvalidPathException, PathAlreadyBoundException;

    @WebMethod
    @WebResult(name = "${resourceName}")
    public ${resourceNameCapitalized} read${resourceNameCapitalized}(String path) throws ${serviceNameCapitalized}ServiceException, AccessDeniedException, InvalidPathException, PathNotFoundException;

    @WebMethod
    public void update${resourceNameCapitalized}(String path, String name) throws ${serviceNameCapitalized}ServiceException, AccessDeniedException, InvalidPathException, PathNotFoundException;

    @WebMethod
    public void delete${resourceNameCapitalized}(String path) throws ${serviceNameCapitalized}ServiceException, AccessDeniedException, InvalidPathException, PathNotFoundException, PathNotEmptyException;

}
