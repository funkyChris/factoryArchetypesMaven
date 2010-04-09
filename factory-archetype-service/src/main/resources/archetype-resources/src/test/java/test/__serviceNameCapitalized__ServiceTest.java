#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.test;

import static org.hamcrest.text.StringContains.containsString;
import static ${package}.test.jmock.action.SaveParamsAction.saveParams;
import static ${package}.test.jmock.matcher.EventWithTypeEqualsToMatcher.anEventWithTypeEqualsTo;

import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.Sequence;
import org.qualipso.factory.FactoryResourceIdentifier;
import org.qualipso.factory.FactoryResourceProperty;
import org.qualipso.factory.binding.BindingService;
import org.qualipso.factory.binding.entity.Node;
import org.qualipso.factory.indexing.IndexingService;
import org.qualipso.factory.membership.MembershipService;
import org.qualipso.factory.membership.entity.Profile;
import org.qualipso.factory.notification.NotificationService;
import org.qualipso.factory.security.pap.PAPService;
import org.qualipso.factory.security.pep.PEPService;
import ${package}.${serviceNameCapitalized}Service;
import ${package}.${serviceNameCapitalized}ServiceBean;
import ${package}.entity.${resourceNameCapitalized};

import com.bm.testsuite.BaseSessionBeanFixture;


public class ${serviceNameCapitalized}ServiceTest extends BaseSessionBeanFixture<${serviceNameCapitalized}ServiceBean> {
    private static Log logger = LogFactory.getLog(${serviceNameCapitalized}ServiceTest.class);
    @SuppressWarnings("unchecked")
    private static final Class[] usedBeans = { ${resourceNameCapitalized}.class, Node.class, Profile.class };
    private Mockery mockery;
    private BindingService binding;
    private MembershipService membership;
    private PEPService pep;
    private PAPService pap;
    private NotificationService notification;
    private IndexingService indexing;

    public ${serviceNameCapitalized}ServiceTest() {
        super(${serviceNameCapitalized}ServiceBean.class, usedBeans);
    }

    public void setUp() throws Exception {
        super.setUp();
        logger.debug("injecting mock partners session beans");
        mockery = new Mockery();
        binding = mockery.mock(BindingService.class);
        membership = mockery.mock(MembershipService.class);
        pep = mockery.mock(PEPService.class);
        pap = mockery.mock(PAPService.class);
        notification = mockery.mock(NotificationService.class);
        indexing = mockery.mock(IndexingService.class);
        getBeanToTest().setMembershipService(membership);
        getBeanToTest().setNotificationService(notification);
        getBeanToTest().setBindingService(binding);
        getBeanToTest().setPEPService(pep);
        getBeanToTest().setPAPService(pap);
        getBeanToTest().setIndexingService(indexing);
    }

    public void testCRUD${resourceNameCapitalized}() {
        logger.debug("testing CRUD${resourceNameCapitalized}(...)");

        final Sequence sequence1 = mockery.sequence("sequence1");
        final Vector<Object> params1 = new Vector<Object>();
        final Vector<Object> params2 = new Vector<Object>();

        try {
            mockery.checking(new Expectations() {

                    {
                        //All calls to partners services are mocked and expectations are defined to ensure correct calls.
                        //This call sequence expectation define what partners methods are called while creating a ${resourceNameCapitalized} resource in the ${serviceNameCapitalized} service :
                        //in this case, we assume that the caller is identified as jayblanc so it's profile path is /profiles/jayblanc
                        //note that because of binding mock service, the binding is not really called so we have to store the resource id to be able to get it back we looking up in the binding. 
                        oneOf(membership).getProfilePathForConnectedIdentifier();
                        will(returnValue("/profiles/jayblanc"));
                        inSequence(sequence1);
                        oneOf(membership).getConnectedIdentifierSubjects();
                        will(returnValue(new String[] { "/profiles/jayblanc" }));
                        inSequence(sequence1);
                        oneOf(pep).checkSecurity(with(equal(new String[] { "/profiles/jayblanc" })), with(equal("/${resourceName}s")), with(equal("create")));
                        inSequence(sequence1);
                        oneOf(binding).bind(with(any(FactoryResourceIdentifier.class)), with(equal("/${resourceName}s/sheldon")));
                        will(saveParams(params1));
                        inSequence(sequence1);
                        oneOf(binding)
                            .setProperty(with(equal("/${resourceName}s/sheldon")), with(equal(FactoryResourceProperty.CREATION_TIMESTAMP)), with(any(String.class)));
                        inSequence(sequence1);
                        oneOf(binding)
                            .setProperty(with(equal("/${resourceName}s/sheldon")), with(equal(FactoryResourceProperty.LAST_UPDATE_TIMESTAMP)), with(any(String.class)));
                        inSequence(sequence1);
                        oneOf(binding).setProperty(with(equal("/${resourceName}s/sheldon")), with(equal(FactoryResourceProperty.AUTHOR)), with(equal("/profiles/jayblanc")));
                        inSequence(sequence1);
                        oneOf(pap).createPolicy(with(any(String.class)), with(containsString("/${resourceName}s/sheldon")));
                        inSequence(sequence1);
                        oneOf(binding).setProperty(with(equal("/${resourceName}s/sheldon")), with(equal(FactoryResourceProperty.OWNER)), with(equal("/profiles/jayblanc")));
                        inSequence(sequence1);
                        oneOf(binding).setProperty(with(equal("/${resourceName}s/sheldon")), with(equal(FactoryResourceProperty.POLICY_ID)), with(any(String.class)));
                        inSequence(sequence1);
                        oneOf(notification).throwEvent(with(anEventWithTypeEqualsTo("${serviceName}.${resourceName}.create")));
                        inSequence(sequence1);
                        oneOf(indexing).index(with(equal("/${resourceName}s/sheldon")));
                        inSequence(sequence1);

                        //Second time for second ${resourceName} : 
                        oneOf(membership).getProfilePathForConnectedIdentifier();
                        will(returnValue("/profiles/jayblanc"));
                        inSequence(sequence1);
                        oneOf(membership).getConnectedIdentifierSubjects();
                        will(returnValue(new String[] { "/profiles/jayblanc" }));
                        inSequence(sequence1);
                        oneOf(pep).checkSecurity(with(equal(new String[] { "/profiles/jayblanc" })), with(equal("/${resourceName}s")), with(equal("create")));
                        inSequence(sequence1);
                        oneOf(binding).bind(with(any(FactoryResourceIdentifier.class)), with(equal("/${resourceName}s/howard")));
                        will(saveParams(params2));
                        inSequence(sequence1);
                        oneOf(binding)
                            .setProperty(with(equal("/${resourceName}s/howard")), with(equal(FactoryResourceProperty.CREATION_TIMESTAMP)), with(any(String.class)));
                        inSequence(sequence1);
                        oneOf(binding)
                            .setProperty(with(equal("/${resourceName}s/howard")), with(equal(FactoryResourceProperty.LAST_UPDATE_TIMESTAMP)), with(any(String.class)));
                        inSequence(sequence1);
                        oneOf(binding).setProperty(with(equal("/${resourceName}s/howard")), with(equal(FactoryResourceProperty.AUTHOR)), with(equal("/profiles/jayblanc")));
                        inSequence(sequence1);
                        oneOf(pap).createPolicy(with(any(String.class)), with(containsString("/${resourceName}s/howard")));
                        inSequence(sequence1);
                        oneOf(binding).setProperty(with(equal("/${resourceName}s/howard")), with(equal(FactoryResourceProperty.OWNER)), with(equal("/profiles/jayblanc")));
                        inSequence(sequence1);
                        oneOf(binding).setProperty(with(equal("/${resourceName}s/howard")), with(equal(FactoryResourceProperty.POLICY_ID)), with(any(String.class)));
                        inSequence(sequence1);
                        oneOf(notification).throwEvent(with(anEventWithTypeEqualsTo("${serviceName}.${resourceName}.create")));
                        inSequence(sequence1);
                        oneOf(indexing).index(with(equal("/${resourceName}s/howard")));
                        inSequence(sequence1);
                    }
                });

            ${serviceNameCapitalized}Service service = getBeanToTest();
            service.create${resourceNameCapitalized}("/${resourceName}s/sheldon", "Sheldon Cooper");
            service.create${resourceNameCapitalized}("/${resourceName}s/howard", "Howard Wolowitz");

            mockery.checking(new Expectations() {

                    {
                        //Reading the first ${resourceName} : 
                        oneOf(membership).getProfilePathForConnectedIdentifier();
                        will(returnValue("/profiles/jayblanc"));
                        inSequence(sequence1);
                        oneOf(membership).getConnectedIdentifierSubjects();
                        will(returnValue(new String[] { "/profiles/jayblanc" }));
                        inSequence(sequence1);
                        oneOf(pep).checkSecurity(with(equal(new String[] { "/profiles/jayblanc" })), with(equal("/${resourceName}s/sheldon")), with(equal("read")));
                        inSequence(sequence1);
                        oneOf(binding).lookup(with(equal("/${resourceName}s/sheldon")));
                        will(returnValue(params1.get(0)));
                        inSequence(sequence1);
                        oneOf(notification).throwEvent(with(anEventWithTypeEqualsTo("${serviceName}.${resourceName}.read")));
                        inSequence(sequence1);

                        //Reading the second ${resourceName} : 
                        oneOf(membership).getProfilePathForConnectedIdentifier();
                        will(returnValue("/profiles/jayblanc"));
                        inSequence(sequence1);
                        oneOf(membership).getConnectedIdentifierSubjects();
                        will(returnValue(new String[] { "/profiles/jayblanc" }));
                        inSequence(sequence1);
                        oneOf(pep).checkSecurity(with(equal(new String[] { "/profiles/jayblanc" })), with(equal("/${resourceName}s/howard")), with(equal("read")));
                        inSequence(sequence1);
                        oneOf(binding).lookup(with(equal("/${resourceName}s/howard")));
                        will(returnValue(params2.get(0)));
                        inSequence(sequence1);
                        oneOf(notification).throwEvent(with(anEventWithTypeEqualsTo("${serviceName}.${resourceName}.read")));
                        inSequence(sequence1);
                    }
                });

            assertTrue(service.read${resourceNameCapitalized}("/${resourceName}s/sheldon").getName().equals("Sheldon Cooper"));
            assertTrue(service.read${resourceNameCapitalized}("/${resourceName}s/howard").getName().equals("Howard Wolowitz"));

            mockery.checking(new Expectations() {

                    {
                        //Update the first ${resourceName} : 
                        oneOf(membership).getProfilePathForConnectedIdentifier();
                        will(returnValue("/profiles/jayblanc"));
                        inSequence(sequence1);
                        oneOf(membership).getConnectedIdentifierSubjects();
                        will(returnValue(new String[] { "/profiles/jayblanc" }));
                        inSequence(sequence1);
                        oneOf(pep).checkSecurity(with(equal(new String[] { "/profiles/jayblanc" })), with(equal("/${resourceName}s/sheldon")), with(equal("update")));
                        inSequence(sequence1);
                        oneOf(binding).lookup(with(equal("/${resourceName}s/sheldon")));
                        will(returnValue(params1.get(0)));
                        inSequence(sequence1);
                        oneOf(binding)
                            .setProperty(with(equal("/${resourceName}s/sheldon")), with(equal(FactoryResourceProperty.LAST_UPDATE_TIMESTAMP)), with(any(String.class)));
                        inSequence(sequence1);
                        oneOf(notification).throwEvent(with(anEventWithTypeEqualsTo("${serviceName}.${resourceName}.update")));
                        inSequence(sequence1);
                        oneOf(indexing).reindex(with(equal("/${resourceName}s/sheldon")));
                        inSequence(sequence1);

                        //Update the second ${resourceName} : 
                        oneOf(membership).getProfilePathForConnectedIdentifier();
                        will(returnValue("/profiles/jayblanc"));
                        inSequence(sequence1);
                        oneOf(membership).getConnectedIdentifierSubjects();
                        will(returnValue(new String[] { "/profiles/jayblanc" }));
                        inSequence(sequence1);
                        oneOf(pep).checkSecurity(with(equal(new String[] { "/profiles/jayblanc" })), with(equal("/${resourceName}s/howard")), with(equal("update")));
                        inSequence(sequence1);
                        oneOf(binding).lookup(with(equal("/${resourceName}s/howard")));
                        will(returnValue(params2.get(0)));
                        inSequence(sequence1);
                        oneOf(binding)
                            .setProperty(with(equal("/${resourceName}s/howard")), with(equal(FactoryResourceProperty.LAST_UPDATE_TIMESTAMP)), with(any(String.class)));
                        inSequence(sequence1);
                        oneOf(notification).throwEvent(with(anEventWithTypeEqualsTo("${serviceName}.${resourceName}.update")));
                        inSequence(sequence1);
                        oneOf(indexing).reindex(with(equal("/${resourceName}s/howard")));
                        inSequence(sequence1);

                        //Reading the first ${resourceName} : 
                        oneOf(membership).getProfilePathForConnectedIdentifier();
                        will(returnValue("/profiles/jayblanc"));
                        inSequence(sequence1);
                        oneOf(membership).getConnectedIdentifierSubjects();
                        will(returnValue(new String[] { "/profiles/jayblanc" }));
                        inSequence(sequence1);
                        oneOf(pep).checkSecurity(with(equal(new String[] { "/profiles/jayblanc" })), with(equal("/${resourceName}s/sheldon")), with(equal("read")));
                        inSequence(sequence1);
                        oneOf(binding).lookup(with(equal("/${resourceName}s/sheldon")));
                        will(returnValue(params1.get(0)));
                        inSequence(sequence1);
                        oneOf(notification).throwEvent(with(anEventWithTypeEqualsTo("${serviceName}.${resourceName}.read")));
                        inSequence(sequence1);

                        //Reading the second ${resourceName} : 
                        oneOf(membership).getProfilePathForConnectedIdentifier();
                        will(returnValue("/profiles/jayblanc"));
                        inSequence(sequence1);
                        oneOf(membership).getConnectedIdentifierSubjects();
                        will(returnValue(new String[] { "/profiles/jayblanc" }));
                        inSequence(sequence1);
                        oneOf(pep).checkSecurity(with(equal(new String[] { "/profiles/jayblanc" })), with(equal("/${resourceName}s/howard")), with(equal("read")));
                        inSequence(sequence1);
                        oneOf(binding).lookup(with(equal("/${resourceName}s/howard")));
                        will(returnValue(params2.get(0)));
                        inSequence(sequence1);
                        oneOf(notification).throwEvent(with(anEventWithTypeEqualsTo("${serviceName}.${resourceName}.read")));
                        inSequence(sequence1);
                    }
                });

            service.update${resourceNameCapitalized}("/${resourceName}s/sheldon", "Sheldon Cooper **");
            service.update${resourceNameCapitalized}("/${resourceName}s/howard", "Howard Wolowitz *");
            assertTrue(service.read${resourceNameCapitalized}("/${resourceName}s/sheldon").getName().equals("Sheldon Cooper **"));
            assertTrue(service.read${resourceNameCapitalized}("/${resourceName}s/howard").getName().equals("Howard Wolowitz *"));

            mockery.checking(new Expectations() {

                    {
                        //Delete the first ${resourceName} : 
                        oneOf(membership).getProfilePathForConnectedIdentifier();
                        will(returnValue("/profiles/jayblanc"));
                        inSequence(sequence1);
                        oneOf(membership).getConnectedIdentifierSubjects();
                        will(returnValue(new String[] { "/profiles/jayblanc" }));
                        inSequence(sequence1);
                        oneOf(pep).checkSecurity(with(equal(new String[] { "/profiles/jayblanc" })), with(equal("/${resourceName}s/sheldon")), with(equal("delete")));
                        inSequence(sequence1);
                        oneOf(binding).lookup(with(equal("/${resourceName}s/sheldon")));
                        will(returnValue(params1.get(0)));
                        inSequence(sequence1);
                        oneOf(binding).getProperty(with(equal("/${resourceName}s/sheldon")), with(equal(FactoryResourceProperty.POLICY_ID)), with(equal(false)));
                        inSequence(sequence1);
                        oneOf(pap).deletePolicy(with(any(String.class)));
                        inSequence(sequence1);
                        oneOf(binding).unbind(with(equal("/${resourceName}s/sheldon")));
                        inSequence(sequence1);
                        oneOf(notification).throwEvent(with(anEventWithTypeEqualsTo("${serviceName}.${resourceName}.delete")));
                        inSequence(sequence1);
                        oneOf(indexing).remove(with(equal("/${resourceName}s/sheldon")));
                        inSequence(sequence1);

                        //Delete the second ${resourceName} : 
                        oneOf(membership).getProfilePathForConnectedIdentifier();
                        will(returnValue("/profiles/jayblanc"));
                        inSequence(sequence1);
                        oneOf(membership).getConnectedIdentifierSubjects();
                        will(returnValue(new String[] { "/profiles/jayblanc" }));
                        inSequence(sequence1);
                        oneOf(pep).checkSecurity(with(equal(new String[] { "/profiles/jayblanc" })), with(equal("/${resourceName}s/howard")), with(equal("delete")));
                        inSequence(sequence1);
                        oneOf(binding).lookup(with(equal("/${resourceName}s/howard")));
                        will(returnValue(params2.get(0)));
                        inSequence(sequence1);
                        oneOf(binding).getProperty(with(equal("/${resourceName}s/howard")), with(equal(FactoryResourceProperty.POLICY_ID)), with(equal(false)));
                        inSequence(sequence1);
                        oneOf(pap).deletePolicy(with(any(String.class)));
                        inSequence(sequence1);
                        oneOf(binding).unbind(with(equal("/${resourceName}s/howard")));
                        inSequence(sequence1);
                        oneOf(notification).throwEvent(with(anEventWithTypeEqualsTo("${serviceName}.${resourceName}.delete")));
                        inSequence(sequence1);
                        oneOf(indexing).remove(with(equal("/${resourceName}s/howard")));
                        inSequence(sequence1);
                    }
                });

            service.delete${resourceNameCapitalized}("/${resourceName}s/sheldon");
            service.delete${resourceNameCapitalized}("/${resourceName}s/howard");

            mockery.assertIsSatisfied();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            fail(e.getMessage());
        }
    }
}
