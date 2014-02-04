package com.jsportal.newrelic4tapestry5.services;

import java.util.Collection;

import org.apache.tapestry5.services.Request;

import com.newrelic.api.agent.NewRelic;

public class NewRelicTransactionServiceImpl implements NewRelicTransactionService {
	
	private Request request;

	public NewRelicTransactionServiceImpl(Request request) {
		this.request = request;
	}

	@Override
	public void setTransactionName(String transactionName) {
		if(request.getAttribute(NEWRELIC_AGENT_TRANSACTION_KEY) == null) {			
			request.setAttribute(NEWRELIC_AGENT_TRANSACTION_KEY, transactionName);
		}
	}

	@Override
	public void ignoreTransaction() {
		NewRelic.ignoreTransaction();
	}

	@Override
	public void ignoreApdex() {
		NewRelic.ignoreApdex();
	}


	@Override
	public void setUserName(String name) {
		NewRelic.setUserName(name);
	}

	@Override
	public void setAccountName(String name) {
		NewRelic.setAccountName(name);
	}

	@Override
	public void setProductName(String name) {
		NewRelic.setProductName(name);
	}

}
