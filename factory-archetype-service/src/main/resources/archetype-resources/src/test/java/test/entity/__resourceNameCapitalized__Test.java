#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test.entity;

import com.bm.testsuite.BaseEntityFixture;

import ${package}.entity.${resourceNameCapitalized};


public class ${resourceNameCapitalized}Test extends BaseEntityFixture<${resourceNameCapitalized}> {
    public ${resourceNameCapitalized}Test() {
        super(${resourceNameCapitalized}.class);
    }
}
