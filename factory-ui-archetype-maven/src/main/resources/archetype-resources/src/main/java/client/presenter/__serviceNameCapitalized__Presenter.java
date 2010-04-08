#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client.presenter;

import ${package}.client.iserver.${serviceNameCapitalized}Servlet;
import ${package}.client.iserver.${serviceNameCapitalized}ServletAsync;
import ${package}.client.model.${resourceNameCapitalized};
import ${package}.client.view.Create${resourceNameCapitalized}View;
import ${package}.client.view.Delete${resourceNameCapitalized}View;
import ${package}.client.view.Display${resourceNameCapitalized}View;
import ${package}.client.view.Update${resourceNameCapitalized}View;
import org.qualipso.factory.ui.shared.OpenParts.client.OPBinder;
import org.qualipso.factory.ui.shared.OpenParts.client.OPParams;
import org.qualipso.factory.ui.shared.OpenParts.client.OPShell;
import org.qualipso.factory.ui.shared.OpenParts.client.annotations.OPMandatoryParams;
import org.qualipso.factory.ui.shared.OpenParts.client.annotations.OPServiceName;
import org.qualipso.factory.ui.shared.OpenParts.client.annotations.OPViewFactory;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;

@OPServiceName("${serviceName}")
public class ${serviceNameCapitalized}Presenter implements EntryPoint {

    public interface ${serviceNameCapitalized}Binder extends OPBinder<${serviceNameCapitalized}Presenter> {}
    public final static ${serviceNameCapitalized}Binder binder = GWT.create(${serviceNameCapitalized}Binder.class);

    protected ${serviceNameCapitalized}ServletAsync ${serviceName}Service = GWT.create(${serviceNameCapitalized}Servlet.class);

    @Override
    public void onModuleLoad() {
        GWT.log("Loaded ${serviceNameCapitalized}", null);
        binder.bindPart(this);

        binder.notifyLoadingCompleted();
    }

    @OPViewFactory(resourceName = "${resourceName}", actionName = OPShell.ACTION_CREATE)
    @OPMandatoryParams("path")
    public Widget loadCreate${resourceNameCapitalized}View(final OPParams params) {
        return new Create${resourceNameCapitalized}View(this, params.getParamValue("path"));
    }

    @OPViewFactory(resourceName = "${resourceName}", actionName = OPShell.ACTION_DISPLAY)
    @OPMandatoryParams("path")
    public Widget loadDisplay${resourceNameCapitalized}View(final OPParams params) {
        return new Display${resourceNameCapitalized}View(this, params.getParamValue("path"));
    }

    @OPViewFactory(resourceName = "${resourceName}", actionName = "update")
    @OPMandatoryParams("path")
    public Widget loadUpdate${resourceNameCapitalized}View(final OPParams params) {
        return new Update${resourceNameCapitalized}View(this, params.getParamValue("path"));
    }

    @OPViewFactory(resourceName = "${resourceName}", actionName = "delete")
    @OPMandatoryParams("path")
    public Widget loadDelete${resourceNameCapitalized}View(final OPParams params) {
        return new Delete${resourceNameCapitalized}View(this, params.getParamValue("path"));
    }

    public void create${resourceNameCapitalized}(final String path, final String name) {
        ${serviceName}Service.create${resourceNameCapitalized}(path + "/" + name, name, new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void v) {
                OPShell.dispatchEvent("CHANGE_PATH", new OPParams("path", path + "/" + name));
            }

            @Override
            public void onFailure(Throwable e) {
                displayErrorMessage(e);
            }
        });
    }

    public void delete${resourceNameCapitalized}(final String path) {
        ${serviceName}Service.delete${resourceNameCapitalized}(path, new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void v) {
                OPShell.dispatchEvent("CHANGE_PATH", new OPParams("path", getParentPath(path)));
            }

            @Override
            public void onFailure(Throwable e) {
                displayErrorMessage(e);
            }
        });
    }

    public void read${resourceNameCapitalized}(String path, final ${resourceNameCapitalized}View view) {
        ${serviceName}Service.read${resourceNameCapitalized}(path, new AsyncCallback<${resourceName}>() {
            @Override
            public void onSuccess(${resourceNameCapitalized} ${resourceName}) {
                view.setData(${resourceName});
            }

            @Override
            public void onFailure(Throwable e) {
                displayErrorMessage(e);
            }
        });
    }

    public void update${resourceNameCapitalized}(final String path, String name) {
        ${serviceName}Service.update${resourceNameCapitalized}(path, name, new AsyncCallback<Void>() {
            @Override
            public void onSuccess(Void v) {
                OPShell.dispatchEvent("CHANGE_PATH", new OPParams("path", path));
            }

            @Override
            public void onFailure(Throwable e) {
                displayErrorMessage(e);
            }
        });
    }

    public String getParentPath(String path) {
        return path.substring(0, path.lastIndexOf('/'));
    }

    private void displayErrorMessage(Throwable e) {
        final PopupPanel popup = new PopupPanel();
        popup.add(new Label(e.getMessage()));
        popup.add(new Button("OK", new ClickHandler() {
            @Override
            public void onClick(ClickEvent arg0) {
                popup.hide();
            }
        }));
        popup.center();
        popup.show();
    }

}
