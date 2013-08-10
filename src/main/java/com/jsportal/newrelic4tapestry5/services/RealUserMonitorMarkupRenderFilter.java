package com.jsportal.newrelic4tapestry5.services;

import org.apache.tapestry5.MarkupWriter;
import org.apache.tapestry5.dom.Document;
import org.apache.tapestry5.dom.Element;
import org.apache.tapestry5.services.MarkupRenderer;
import org.apache.tapestry5.services.MarkupRendererFilter;
import org.jaxen.JaxenException;

import com.formos.tapestry.xpath.TapestryXPath;
import com.newrelic.api.agent.NewRelic;

public class RealUserMonitorMarkupRenderFilter implements MarkupRendererFilter {

	private final boolean isHeader;

	public RealUserMonitorMarkupRenderFilter(boolean isHeader) {
		this.isHeader = isHeader;
	}

	public void renderMarkup(MarkupWriter writer, MarkupRenderer renderer) {
		renderer.renderMarkup(writer);
       
        Document document = writer.getDocument();
		Element bodyElement;
		Element headElement;	
		try {
			bodyElement = TapestryXPath.xpath("/html/body").selectSingleElement(document);
			headElement = TapestryXPath.xpath("/html/head").selectSingleElement(document);	
		} catch (JaxenException e) {
			throw new RuntimeException(e);
		}	
		
		if(bodyElement != null && headElement != null) {
			if(isHeader) {				
				String rawHeader = NewRelic.getBrowserTimingHeader();
				Element headerElement = headElement
						.element("script", "type", "text/javascript")
						.raw(clearScriptTags(rawHeader));
				headerElement.moveToTop(headElement);
			}
			else {				
				String rawFooter = NewRelic.getBrowserTimingFooter();
				Element footerElement = bodyElement
						.element("script", "type", "text/javascript")
						.raw(clearScriptTags(rawFooter));
				footerElement.moveToBottom(bodyElement);
			}
			
		}

	}
	
	private static String clearScriptTags(String javascript){
		return javascript.replaceAll("<script[^>]*>", "").replaceAll("</script>", "");
	}

}
