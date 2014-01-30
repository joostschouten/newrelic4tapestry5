package com.jsportal.newrelic4tapestry5.services;

import org.apache.tapestry5.ioc.Configuration;
import org.apache.tapestry5.ioc.MappedConfiguration;
import org.apache.tapestry5.ioc.OrderedConfiguration;
import org.apache.tapestry5.ioc.annotations.Contribute;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.annotations.Symbol;
import org.apache.tapestry5.services.ComponentClassResolver;
import org.apache.tapestry5.services.ComponentRequestFilter;
import org.apache.tapestry5.services.ComponentRequestHandler;
import org.apache.tapestry5.services.LibraryMapping;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;

public class NewRelic4Tapestry5AppModule {

	public static final String NEW_RELIC_DISABLED = "NEW_RELIC_4_TAPESTRY_DISABLED";
	
	public static final String MODULE_PREFIX = "NR4T5";
	
	public static void contributeApplicationDefaults(final MappedConfiguration<String, String> configuration) {
		configuration.add(NEW_RELIC_DISABLED, "false");
	}

	@Contribute(ComponentRequestHandler.class)
	public static void contributeRequestHandler(
			final OrderedConfiguration<ComponentRequestFilter> configuration,
			@Symbol(value = NEW_RELIC_DISABLED) boolean disabled
			) {
		if(!disabled) {
			configuration.addInstance("NewRelicWebTransactionsRequestFilter", WebTransactionsRequestFilter.class, "after:*");
		}
	}
	
	@Contribute(ComponentClassResolver.class)
	public void contributeComponentClassResolver(Configuration<LibraryMapping> config) {
		config.add(new LibraryMapping(MODULE_PREFIX, "com.jsportal.newrelic4tapestry5"));
	}
	
	@Contribute(MarkupRenderer.class)
	public void contributeMarkupRenderer(OrderedConfiguration<MarkupRendererFilter> configuration,
			@Symbol(value = NEW_RELIC_DISABLED) boolean disabled) {
		
		if(!disabled ) {			
			MarkupRendererFilter realUserMonitorMarkupHeaderRenderFilter = new RealUserMonitorMarkupRenderFilter(true);
			configuration.add("realUserMonitorMarkupHeaderRenderFilter", realUserMonitorMarkupHeaderRenderFilter, "after:*");
			
			MarkupRendererFilter realUserMonitorMarkupFooterRenderFilter = new RealUserMonitorMarkupRenderFilter(false);
			configuration.add("realUserMonitorMarkupFooterRenderFilter", realUserMonitorMarkupFooterRenderFilter, "after:realUserMonitorMarkupHeaderRenderFilter");
		}

    }
	
	
}
