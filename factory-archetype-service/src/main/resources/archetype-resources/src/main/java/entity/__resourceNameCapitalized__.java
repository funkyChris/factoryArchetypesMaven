#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.entity;

import org.qualipso.factory.FactoryNamingConvention;
import org.qualipso.factory.FactoryResource;
import org.qualipso.factory.FactoryResourceIdentifier;
import ${package}.${serviceNameCapitalized}Service;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;


@Entity
@XmlType(name = ${resourceNameCapitalized}.RESOURCE_NAME, namespace = FactoryNamingConvention.RESOURCE_NAMESPACE + ${resourceNameCapitalized}.RESOURCE_NAME, propOrder =  {
    "name"}
)
@SuppressWarnings("serial")
public class ${resourceNameCapitalized} extends FactoryResource {
    public static final String RESOURCE_NAME = "${resourceName}";
    @Id
    private String id;
    private String name;
    private String path;

    public ${resourceNameCapitalized}() {
    }

    @XmlAttribute(name = "id", required = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(name = "path", required = true)
    @Transient
    @Override
    public String getResourcePath() {
        return path;
    }

    public void setResourcePath(String path) {
        this.path = path;
    }

    @XmlElement(name = "name", required = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    @XmlTransient
    public FactoryResourceIdentifier getFactoryResourceIdentifier() {
        return new FactoryResourceIdentifier(${serviceNameCapitalized}Service.SERVICE_NAME, ${resourceNameCapitalized}.RESOURCE_NAME, getId());
    }

    @Override
    @XmlTransient
    public String getResourceName() {
        return getName();
    }
}
