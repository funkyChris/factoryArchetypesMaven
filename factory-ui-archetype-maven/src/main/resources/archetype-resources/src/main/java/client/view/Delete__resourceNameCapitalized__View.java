#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client.view;

import ${package}.client.presenter.${serviceNameCapitalized}Presenter;
import ${package}.client.resources.${serviceNameCapitalized}Resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class Delete${resourceNameCapitalized}View extends Composite {

    interface Delete${resourceNameCapitalized}ViewUiBinder extends UiBinder<Widget, Delete${resourceNameCapitalized}View> {}
    private static Delete${resourceNameCapitalized}ViewUiBinder uiBinder = GWT.create(Delete${resourceNameCapitalized}ViewUiBinder.class);

    @UiField
    protected ${serviceNameCapitalized}Resources resources;
    @UiField
    Label name;
    
    @UiField
    Button delete;
    
    ${serviceNameCapitalized}Presenter presenter;
    String path;
    
    public Delete${resourceNameCapitalized}View(${serviceNameCapitalized}Presenter presenter, String path) {
        initWidget(uiBinder.createAndBindUi(this));
        resources.style().ensureInjected();
        this.presenter = presenter;
        this.path = path;
        name.setText(path);
    }

    @UiHandler("delete")
    public void delete${resourceNameCapitalized}(ClickEvent e) {
        presenter.delete${resourceNameCapitalized}(path);
    }
    
}
