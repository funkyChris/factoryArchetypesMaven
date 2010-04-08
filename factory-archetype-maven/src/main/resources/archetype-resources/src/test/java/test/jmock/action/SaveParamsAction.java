#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.jmock.action;

import org.hamcrest.Description;

import org.jmock.api.Action;
import org.jmock.api.Invocation;

import java.util.Vector;


public class SaveParamsAction implements Action {
    private Vector<Object> params;

    public SaveParamsAction(Vector<Object> params) {
        this.params = params;
    }

    public Object invoke(Invocation invocation) throws Throwable {
        for (Object param : invocation.getParametersAsArray()) {
            params.add(param);
        }

        return null;
    }

    public void describeTo(Description description) {
        description.appendText("save params in ");
        description.appendValue(params);
    }

    public static <T> Action saveParams(Vector<Object> params) {
        return new SaveParamsAction(params);
    }
}
