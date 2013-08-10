New Relic Module for Tapestry5
==================

This Tapestry 5 Module will add [Real User Monitoring](https://newrelic.com/docs/features/real-user-monitoring) and 
properly named [Web Transactions](https://newrelic.com/docs/applications-dashboards/web-transactions) to your 
[New Relic](http://www.newrelic.com) monitored [Tapestry5](http://tapestry.apache.org) application. 

Out of the box New Relic will only capture the [Tapestry Filter](http://tapestry.apache.org/request-processing.html#RequestProcessing-TapestryFilter) for every request. 
[NewRelic4Tapestry5](https://github.com/joostschouten/newrelic4tapestry5) will track the correct page and event name for every request. 
The locale, request parameters and context parameters are stripped to allow for meaningfull performance monitoring of your pages and components.

A generic [MarkupRendererFilter](http://tapestry.apache.org/current/apidocs/org/apache/tapestry5/services/MarkupRendererFilter.html) is used to add [Real User Monitoring](https://newrelic.com/docs/features/real-user-monitoring)
to each pagerequest to also track your page loading and dom evaluation.

Usage
===

Include [NewRelic4Tapestry5](https://github.com/joostschouten/newrelic4tapestry5) in your [Maven](http://maven.apache.org) dependencies
```
<dependency>
	<groupId>com.jsportal</groupId>
	<artifactId>NewRelic4Tapestry5</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```

To include it in your own tapestry project simply add the [NewRelic4Tapestry5AppModule](https://github.com/joostschouten/newrelic4tapestry5/blob/master/src/main/java/com/jsportal/newrelic4tapestry5/services/NewRelic4Tapestry5AppModule.java) as a [@SubModule](http://tapestry.apache.org/current/apidocs/index.html?org/apache/tapestry5/javadoc/package-summary.html) to your own [Tapestry AppModule](http://tapestry.apache.org/configuration.html#Configuration-YourApplication%27sModuleClass) like so:

```
@SubModule({NewRelic4Tapestry5AppModule.class})
public class YourAppModule {
	... your AppModule code ...
}
```
