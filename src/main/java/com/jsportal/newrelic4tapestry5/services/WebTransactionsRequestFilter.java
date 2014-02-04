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
	
	private NewRelicTransactionService newRelicTransactionService;
	
	public WebTransactionsRequestFilter(NewRelicTransactionService newRelicTransactionService) 
				throws ClassNotFoundException{
		this.newRelicTransactionService = newRelicTransactionService;
	}

	public void handleComponentEvent(ComponentEventRequestParameters parameters, ComponentRequestHandler handler)
			throws IOException {
		newRelicTransactionService.setTransactionName(parameters.getActivePageName() + "." + parameters.getNestedComponentId());
		handler.handleComponentEvent(parameters);
	}

	public void handlePageRender(PageRenderRequestParameters parameters, ComponentRequestHandler handler)
			throws IOException {
		newRelicTransactionService.setTransactionName(parameters.getLogicalPageName());
		handler.handlePageRender(parameters);
	}

}
