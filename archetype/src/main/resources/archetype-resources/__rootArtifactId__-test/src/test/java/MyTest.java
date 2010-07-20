#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
import org.jboss.seam.mock.SeamTest;
import ${package}.sample.session.Authenticator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class MyTest extends SeamTest {
	private static Logger LOGGER = LoggerFactory.getLogger(MyTest.class);
	@Test(groups = { "ui" })
	public void testMe() throws Exception {
		new FacesRequest() {
			@Override
			protected void processValidations() throws Exception {
			}

			@Override
			protected void updateModelValues() throws Exception {
			}

			@Override
			protected void invokeApplication() throws Exception {
			}

			@Override
			protected void renderResponse() throws Exception {
			}
		}.run();
	}
}
