#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test;

import org.junit.runner.RunWith;

import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ${package}.test.entity.${resourceNameCapitalized}Test;
import ${package}.test.${serviceNameCapitalized}ServiceTest;


@RunWith(Suite.class)
@SuiteClasses(value =  {
    ${resourceNameCapitalized}Test.class, ${serviceNameCapitalized}ServiceTest.class}
)
public class AllTests {
}
