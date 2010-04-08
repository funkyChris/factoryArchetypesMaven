#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import org.qualipso.factory.FactoryException;

import javax.xml.ws.WebFault;


@WebFault
@SuppressWarnings("serial")
public class ${serviceNameCapitalized}ServiceException extends FactoryException {
    public ${serviceNameCapitalized}ServiceException(String message, Exception rootCause) {
        super(message, rootCause);
    }

    public${serviceNameCapitalized}ServiceException(String message) {
        super(message);
    }

    public ${serviceNameCapitalized}ServiceException(Exception rootCause) {
        super(rootCause);
    }
}
