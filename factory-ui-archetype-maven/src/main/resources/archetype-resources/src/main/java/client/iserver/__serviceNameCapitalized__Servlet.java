#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client.iserver;

import ${package}.client.model.${resourceNameCapitalized};

import com.google.gwt.rpc.client.RpcService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("${serviceName}")
public interface ${serviceNameCapitalized}Servlet extends RpcService {

    void create${resourceNameCapitalized}(String path, String name) throws ${serviceNameCapitalized}ServiceException;

    ${resourceNameCapitalized} read${resourceNameCapitalized}(String path) throws ${serviceNameCapitalized}ServiceException;

    void update${resourceNameCapitalized}(String path, String name) throws ${serviceNameCapitalized}ServiceException;

    void delete${resourceNameCapitalized}(String path) throws ${serviceNameCapitalized}ServiceException;

}
