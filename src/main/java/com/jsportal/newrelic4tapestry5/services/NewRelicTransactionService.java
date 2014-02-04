package com.jsportal.newrelic4tapestry5.services;

import org.apache.tapestry5.ioc.ScopeConstants;
import org.apache.tapestry5.ioc.annotations.Scope;

import com.newrelic.api.agent.NewRelic;

/**
 * an interface to interact with new relic. Will end up being a simple wrapper around
 * the {@link NewRelic} methods
 * @author joostschouten
 *
 */
@Scope(ScopeConstants.PERTHREAD)
public interface NewRelicTransactionService {
	
	public static final String NEWRELIC_AGENT_TRANSACTION_KEY = "com.newrelic.agent.TRANSACTION_NAME";
	
	void ignoreTransaction();

	void ignoreApdex();
	
	void setTransactionName(String name);
	
	void setUserName(String name);

	void setAccountName(String name);
	
	void setProductName(String name);

}
