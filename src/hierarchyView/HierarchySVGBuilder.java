package hierarchyView;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class HierarchySVGBuilder {
	private Document doc;
	private Element root;

	public HierarchySVGBuilder() {
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		doc = impl.createDocument(svgNS, "svg", null);
		root = doc.getDocumentElement();
	}
	
}
