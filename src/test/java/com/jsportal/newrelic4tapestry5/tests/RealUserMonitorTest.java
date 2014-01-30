package com.jsportal.newrelic4tapestry5.tests;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.ioc.services.SymbolSource;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.test.PageTester;
import org.jaxen.JaxenException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.formos.tapestry.testify.core.ForComponents;
import com.formos.tapestry.xpath.TapestryXPath;
import com.jsportal.newrelic4tapestry5.services.NewRelic4Tapestry5AppModule;
import com.jsportal.newrelic4tapestry5.services.WebTransactionsRequestFilter;
import com.newrelic.api.agent.NewRelic;

public class RealUserMonitorTest {
	
	private Document doc;
	
	private String appPackage;

	private String appName;

	@Before
	public void setupPage() {
		MockitoAnnotations.initMocks(this);
		
		appPackage = "com.jsportal.newrelic4tapestry5";
        appName = "NewRelic4Tapestry5App";
        PageTester tester = new PageTester(appPackage, appName, "src/main/webapp");
        doc = tester.renderPage("RealUserMonitorTestPage");
	}
	
	
	
	@Test
    public void testTimingHeaderAdded() throws JaxenException {
        assertNotNull(doc);
        Element head = TapestryXPath.xpath("//head").selectSingleElement(doc);
        assertFalse("The head should have children", head.getChildren().isEmpty());
        assertTrue("The head should have at least 2 children: The inserted script and the title", head.getChildren().size() > 2);
        Element script = (Element) head.getChildren().get(0);
        assertEquals("script", script.getName());
        assertEquals(NewRelic.getBrowserTimingHeader(), script.getChildMarkup());
    }

	

	@Test
	public void testTimingFootrerAdded() throws JaxenException {
		assertNotNull(doc);
		Element body = TapestryXPath.xpath("//body").selectSingleElement(doc);
		assertFalse("The body should have children", body.getChildren().isEmpty());
		Element script = (Element) body.getChildren().get(body.getChildren().size()-1);
		assertEquals("The last element in the body should be the script element", "script", script.getName());
		assertEquals(NewRelic.getBrowserTimingFooter(), script.getChildMarkup());
	}

}
