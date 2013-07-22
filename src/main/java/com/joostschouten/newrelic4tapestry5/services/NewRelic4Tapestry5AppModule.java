package com.joostschouten.newrelic4tapestry5.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.MarkupRendererFilter;

public class NewRelic4Tapestry5AppModule {

	public static final String MODULE_PREFIX = "NR4T5";

	@Contribute(ComponentRequestHandler.class)
	public static void contributeRequestHandler(final OrderedConfiguration<ComponentRequestFilter> configuration) {
		configuration.addInstance("NewRelicWebTransactionsRequestFilter", WebTransactionsRequestFilter.class, "after:*");
	}
	
	public void contributeComponentClassResolver(Configuration<LibraryMapping> config) {
		config.add(new LibraryMapping(MODULE_PREFIX, "com.joostschouten.newrelic4tapestry5"));
	}
	
	public void contributeMarkupRenderer(OrderedConfiguration<MarkupRendererFilter> configuration) {
        MarkupRendererFilter realUserMonitorMarkupHeaderRenderFilter = new RealUserMonitorMarkupRenderFilter(true);
        configuration.add("realUserMonitorMarkupHeaderRenderFilter", realUserMonitorMarkupHeaderRenderFilter, "before:*");

        MarkupRendererFilter realUserMonitorMarkupFooterRenderFilter = new RealUserMonitorMarkupRenderFilter(false);
        configuration.add("realUserMonitorMarkupFooterRenderFilter", realUserMonitorMarkupFooterRenderFilter, "after:*");
    }
	
	
}
