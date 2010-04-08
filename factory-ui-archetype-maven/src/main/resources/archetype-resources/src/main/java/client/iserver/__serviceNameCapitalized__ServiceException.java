#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client.iserver;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ${serviceNameCapitalized}ServiceException extends Exception implements Serializable {

    public ${serviceNameCapitalized}ServiceException() {}

    public ${serviceNameCapitalized}ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

}
