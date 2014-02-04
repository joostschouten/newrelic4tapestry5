package com.jsportal.newrelic4tapestry5.tests;

import static org.junit.Assert.assertEquals;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.test.PageTester;
import org.jaxen.JaxenException;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.jsportal.newrelic4tapestry5.services.NewRelicTransactionService;
import com.jsportal.newrelic4tapestry5.services.WebTransactionsRequestFilter;

public class WebTransactionTest {
	
	private Document doc;
	private PageTester tester;
	private String appPackage = "com.jsportal.newrelic4tapestry5";
	private String appName = "NewRelic4Tapestry5App";
	
	@Before
	public void setupPage() {
        tester = new PageTester(appPackage, appName, "src/main/webapp");
	}
	
	@Test
    public void testSimplePageRequest() throws JaxenException {
		doc = tester.renderPage("WebTransactionTestPage");
		
		Request request = tester.getRegistry().getService("testableRequest", Request.class);
		String webTransactionName = (String) request.getAttribute(NewRelicTransactionService.NEWRELIC_AGENT_TRANSACTION_KEY);
		assertEquals("WebTransactionTestPage", webTransactionName);
    }

	@Test
	public void testSimplePageRequest_withActivationParameters() throws JaxenException {
		doc = tester.renderPage("WebTransactionTestPage/some/activation/parameters");
		
		Request request = tester.getRegistry().getService("testableRequest", Request.class);
		String webTransactionName = (String) request.getAttribute(NewRelicTransactionService.NEWRELIC_AGENT_TRANSACTION_KEY);
		assertEquals("The activation parameters should be stripped", "WebTransactionTestPage", webTransactionName);
	}

	@Test
	@Ignore(value="The PageTester does not seem to support request paramerets")
	public void testSimplePageRequest_withUrlParameters() throws JaxenException {
		doc = tester.renderPage("WebTransactionTestPage?some=value");
		
		Request request = tester.getRegistry().getService("testableRequest", Request.class);
		String webTransactionName = (String) request.getAttribute(NewRelicTransactionService.NEWRELIC_AGENT_TRANSACTION_KEY);
		assertEquals("The url parameters should be stripped", "WebTransactionTestPage", webTransactionName);
	}

	@Test
	public void testClickActionLink() throws JaxenException {
		doc = tester.renderPage("WebTransactionTestPage");
		
		//clean up the testable request, as in the test env the attribute is persisted across requests.
		Request request = tester.getRegistry().getService("testableRequest", Request.class);
		request.setAttribute(NewRelicTransactionService.NEWRELIC_AGENT_TRANSACTION_KEY, null);
		Element actionLink = doc.getElementById("actionLink");
		tester.clickLink(actionLink);
		
		request = tester.getRegistry().getService("testableRequest", Request.class);
		String webTransactionName = (String) request.getAttribute(NewRelicTransactionService.NEWRELIC_AGENT_TRANSACTION_KEY);
		assertEquals("The component id should be added to the web transaction", "WebTransactionTestPage.actionlink", webTransactionName);
	}
}
