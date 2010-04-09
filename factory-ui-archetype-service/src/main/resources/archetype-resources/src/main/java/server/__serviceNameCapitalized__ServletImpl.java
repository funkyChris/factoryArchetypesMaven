#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.qualipso.factory.Factory;
import org.qualipso.factory.FactoryException;
import ${servicePackage}.${serviceNameCapitalized}Service;
import ${package}.client.iserver.${serviceNameCapitalized}ServiceException;
import ${package}.client.iserver.${serviceNameCapitalized}Servlet;
import ${package}.client.model.${resourceNameCapitalized};

import com.google.gwt.rpc.server.RpcServlet;

@SuppressWarnings("serial")
public class ${serviceNameCapitalized}ServletImpl extends RpcServlet implements ${serviceNameCapitalized}Servlet {

    private static Log logger = LogFactory.getLog(${serviceNameCapitalized}ServletImpl.class);

    protected ${serviceNameCapitalized}Service ${serviceName}Service;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            ${serviceName}Service = (${serviceNameCapitalized}Service) Factory.findService(${serviceNameCapitalized}Service.SERVICE_NAME);
        } catch (FactoryException e) {
            logger.error("Unable to access factory services: ", e);
            throw new ServletException(e);
        }
    }

    @Override
    public void create${resourceNameCapitalized}(String path, String name) throws ${serviceNameCapitalized}ServiceException {
        try {
            ${serviceName}Service.create${resourceNameCapitalized}(path, name);
        } catch (Exception e) {
            throw new ${serviceNameCapitalized}ServiceException("error creating ${resourceName}", e);
        }
    }

    @Override
    public void delete${resourceNameCapitalized}(String path) throws ${serviceNameCapitalized}ServiceException {
        try {
            ${serviceName}Service.delete${resourceNameCapitalized}(path);
        } catch (Exception e) {
            throw new ${serviceNameCapitalized}ServiceException("error deleting ${resourceName}", e);
        }
    }

    @Override
    public ${resourceNameCapitalized} read${resourceNameCapitalized}(String path) throws ${serviceNameCapitalized}ServiceException {
        try {
            ${servicePackage}.entity.${resourceNameCapitalized} ${resourceName} = ${serviceName}Service.read${resourceNameCapitalized}(path);
            return create${resourceNameCapitalized}FromFactory${resourceNameCapitalized}(${resourceName});
        } catch (Exception e) {
            throw new ${serviceNameCapitalized}ServiceException("error reading ${resourceName}", e);
        }
    }

    @Override
    public void update${resourceNameCapitalized}(String path, String name) throws ${serviceNameCapitalized}ServiceException {
        try {
            ${serviceName}Service.update${resourceNameCapitalized}(path, name);
        } catch (Exception e) {
            throw new ${serviceNameCapitalized}ServiceException("error updating ${resourceName}", e);
        }
    }

    private ${resourceNameCapitalized} create${resourceNameCapitalized}FromFactory${resourceNameCapitalized}(${servicePackage}.entity.${resourceNameCapitalized} factory${resourceNameCapitalized}) {
        ${resourceNameCapitalized} ${resourceName} = new ${resourceNameCapitalized}();
        ${resourceName}.setPath(factory${resourceNameCapitalized}.getResourcePath());
        ${resourceName}.setName(factory${resourceNameCapitalized}.getName());
        return ${resourceName};
    }

}
