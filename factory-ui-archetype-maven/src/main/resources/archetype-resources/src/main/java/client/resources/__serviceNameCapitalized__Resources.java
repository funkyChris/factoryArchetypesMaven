#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client.resources;

import ${package}.client.resources.css.Style;

import com.google.gwt.resources.client.ClientBundle;

public interface ${serviceNameCapitalized}Resources extends ClientBundle {

    @Source("${packageInPathFormat}/client/resources/css/style.css")
    Style style();

}
