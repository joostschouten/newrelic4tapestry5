package com.jsportal.newrelic4tapestry5.pages;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.OnEvent;
import org.apache.tapestry5.corelib.components.ActionLink;

public class WebTransactionTestPage {
	
	@Component(id = "actionLink", parameters = {"context=literal:someContext"})
	private ActionLink actionLink;
	
	@OnEvent(component = "actionLink")
	private Object handleActionLink(String context) {
		return null;
	}
	
	
}
