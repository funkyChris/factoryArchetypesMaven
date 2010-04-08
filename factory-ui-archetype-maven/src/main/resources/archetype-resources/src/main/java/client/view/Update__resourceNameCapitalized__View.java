#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client.view;

import ${package}.client.model.${resourceNameCapitalized};
import ${package}.client.presenter.${resourceNameCapitalized}View;
import ${package}.client.presenter.${serviceNameCapitalized}Presenter;
import ${package}.client.resources.${serviceNameCapitalized}Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Update${resourceNameCapitalized}View extends Composite implements ${resourceNameCapitalized}View {

    interface Update${resourceNameCapitalized}ViewUiBinder extends UiBinder<Widget, Update${resourceNameCapitalized}View> {}
    private static Update${resourceNameCapitalized}ViewUiBinder uiBinder = GWT.create(Update${resourceNameCapitalized}ViewUiBinder.class);

    @UiField
    protected ${serviceNameCapitalized}Resources resources;
    @UiField
    TextBox name;
    
    @UiField
    Button update;
    
    ${serviceNameCapitalized}Presenter presenter;
    String path;
    
    public Update${resourceNameCapitalized}View(${serviceNameCapitalized}Presenter presenter, String path) {
        initWidget(uiBinder.createAndBindUi(this));
        resources.style().ensureInjected();
        this.presenter = presenter;
        this.path = path;
        presenter.read${resourceNameCapitalized}(path, this);
    }

    @Override
    public void setData(${resourceNameCapitalized} ${resourceName}) {
        name.setText(${resourceName}.getName());
    }

    @UiHandler("update")
    public void update${resourceNameCapitalized}(ClickEvent e) {
        presenter.update${resourceNameCapitalized}(path, name.getText());
    }
    
}
