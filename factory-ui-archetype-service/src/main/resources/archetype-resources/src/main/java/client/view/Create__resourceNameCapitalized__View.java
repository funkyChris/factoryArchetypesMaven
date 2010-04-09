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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Create${resourceNameCapitalized}View extends Composite {

    interface Create${resourceNameCapitalized}ViewUiBinder extends UiBinder<Widget, Create${resourceNameCapitalized}View> {}
    private static Create${resourceNameCapitalized}ViewUiBinder uiBinder = GWT.create(Create${resourceNameCapitalized}ViewUiBinder.class);

    @UiField
    protected ${serviceNameCapitalized}Resources resources;
    @UiField
    TextBox name;
    
    @UiField
    Button create;

    ${serviceNameCapitalized}Presenter presenter;
    String path;
    
    public Create${resourceNameCapitalized}View(${serviceNameCapitalized}Presenter presenter, String path) {
        initWidget(uiBinder.createAndBindUi(this));
        resources.style().ensureInjected();
        this.presenter = presenter;
        this.path = path;
    }
    
    @UiHandler("create")
    public void create${resourceNameCapitalized}(ClickEvent e) {
        presenter.create${resourceNameCapitalized}(path, name.getText());
    }

}
