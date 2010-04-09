#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client.view;

import ${package}.client.model.${resourceNameCapitalized};
import ${package}.client.presenter.${resourceNameCapitalized}View;
import ${package}.client.presenter.${serviceNameCapitalized}Presenter;
import ${package}.client.resources.${serviceNameCapitalized}Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Display${resourceNameCapitalized}View extends Composite implements ${resourceNameCapitalized}View {

    interface Display${resourceNameCapitalized}ViewUiBinder extends UiBinder<Widget, Display${resourceNameCapitalized}View> {}
    private static Display${resourceNameCapitalized}ViewUiBinder uiBinder = GWT.create(Display${resourceNameCapitalized}ViewUiBinder.class);

    @UiField
    protected ${serviceNameCapitalized}Resources resources;
    @UiField
    Label name;
    @UiField
    Label path;

    public Display${resourceNameCapitalized}View(${serviceNameCapitalized}Presenter presenter, String path) {
        initWidget(uiBinder.createAndBindUi(this));
        resources.style().ensureInjected();
        presenter.read${resourceNameCapitalized}(path, this);
    }
    
    public void setData(${resourceNameCapitalized} ${resourceName}) {
        name.setText(${resourceName}.getName());
        path.setText(${resourceName}.getPath());
    }

}
