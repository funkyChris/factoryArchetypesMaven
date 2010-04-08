#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client.presenter;

import ${package}.client.model.${resourceNameCapitalized};

public interface ${resourceNameCapitalized}View {

    void setData(${resourceNameCapitalized} ${resourceName});

}
