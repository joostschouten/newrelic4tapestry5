package com.jsportal.newrelic4tapestry5.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.test.PageTester;
import org.jaxen.JaxenException;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.formos.tapestry.testify.core.ForComponents;
import com.formos.tapestry.xpath.TapestryXPath;
import com.jsportal.newrelic4tapestry5.services.NewRelic4Tapestry5AppModule;
import com.jsportal.newrelic4tapestry5.services.WebTransactionsRequestFilter;
import com.newrelic.api.agent.NewRelic;

public class ModuleDisabledTest {
	
	private Document doc;
	private PageTester tester;
	String appPackage = "com.jsportal.newrelic4tapestry5";
	String appName = "NewRelic4Tapestry5App";
	
	@ForComponents @Mock
	private SymbolSource symbolSource;

	@Before
	public void setupPage() {
		MockitoAnnotations.initMocks(this);
		
		System.setProperty(NewRelic4Tapestry5AppModule.NEW_RELIC_DISABLED, "true");
        
		tester = new PageTester(appPackage, appName, "src/main/webapp");
		doc = tester.renderPage("WebTransactionTestPage");
	}
	
	@After
	public void cleanup() {
		System.getProperties().remove(NewRelic4Tapestry5AppModule.NEW_RELIC_DISABLED);
	}
	
	@Test
	public void testModuleDisabled_transactionKey() throws JaxenException {
		Request request = tester.getRegistry().getService("testableRequest", Request.class);
		String webTransactionName = (String) request.getAttribute(WebTransactionsRequestFilter.NEWRELIC_AGENT_TRANSACTION_KEY);
		assertNull(webTransactionName);
	}
	
	@Test
	public void testModuleDisabled_realUserMonitoring() throws JaxenException {
		Element script = (Element) TapestryXPath.xpath("//head").selectSingleElement(doc).getChildren().get(0);
		
		assertFalse("The script should not be printed", NewRelic.getBrowserTimingHeader().equals(script.getChildMarkup()));
	}

}
