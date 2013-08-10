package com.jsportal.newrelic4tapestry5.services;

import java.io.IOException;

import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.annotations.Scope;
import org.apache.tapestry5.services.ComponentEventRequestParameters;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.PageRenderRequestParameters;
import org.apache.tapestry5.services.Request;

/**
 * @author Joost Schouten
 *
 */
@Scope(value = ScopeConstants.PERTHREAD)
public class WebTransactionsRequestFilter implements ComponentRequestFilter {
	
	public static final String NEWRELIC_AGENT_TRANSACTION_KEY = "com.newrelic.agent.TRANSACTION_NAME";
	private Request request;
	
	public WebTransactionsRequestFilter(final Request request) 
				throws ClassNotFoundException{
		this.request = request;
	}

	public void handleComponentEvent(ComponentEventRequestParameters parameters, ComponentRequestHandler handler)
			throws IOException {
		if(request.getAttribute(NEWRELIC_AGENT_TRANSACTION_KEY) == null) {			
			request.setAttribute(NEWRELIC_AGENT_TRANSACTION_KEY, parameters.getActivePageName() + "." + parameters.getNestedComponentId());
		}
		handler.handleComponentEvent(parameters);
		
	}

	public void handlePageRender(PageRenderRequestParameters parameters, ComponentRequestHandler handler)
			throws IOException {
		if(request.getAttribute(NEWRELIC_AGENT_TRANSACTION_KEY) == null) {			
			request.setAttribute(NEWRELIC_AGENT_TRANSACTION_KEY, parameters.getLogicalPageName());
		}
		handler.handlePageRender(parameters);
	}

}
