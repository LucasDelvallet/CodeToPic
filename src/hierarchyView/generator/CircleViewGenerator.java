package hierarchyView.generator;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.svg2svg.SVGTranscoder;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.CDATASection;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hierarchyView.util.RGB;

public abstract class CircleViewGenerator extends Generator implements IHierarchyView {
	protected ArrayList<IHierarchyView> children;
	private static int elementID = 0;

	public CircleViewGenerator(String originFile, String targetFolder) {
		super(originFile, targetFolder);
		children = new ArrayList<>();
	}
	
	public abstract String getName();
	
	@Override
	public void generate() throws IOException {
		String diameter = Integer.toString(getRecursiveNumberOfLines());
		Document doc = createSVGDocument();
		Element root = doc.getDocumentElement();
		root.setAttributeNS(null, "width", diameter);
        root.setAttributeNS(null, "height", diameter);
        root.setAttributeNS(null, "onload", "init(evt)");
        addSVGElement(root, doc, 0, 0);
        addStyle(root, doc);
        addScript(root, doc);
        addTooltip(root, doc);
		saveFile(doc);
	}
	
	@Override
	public void addSVGElement(Element root, Document doc, double deltaY, double deltaX) {
		double rayon = getRecursiveNumberOfLines() / (double)2;
		elementID ++;
		
		addCircle(root, doc, elementID+"", rayon + deltaX, rayon + deltaY, rayon, getBackgroundColor(), getStrokeColor());
		
		int childDeltaX = 0;
		for(IHierarchyView c : children) {
			c.addSVGElement(root, doc, rayon - (c.getRecursiveNumberOfLines()/2) + deltaY, deltaX + childDeltaX);
			childDeltaX += c.getRecursiveNumberOfLines();
		}
	}
	
	@Override
	public void addChild(IHierarchyView view) {
		children.add(view);
	}
	
	@Override
	public int getRecursiveNumberOfLines() {
		int childrenSize = 0;
		for(IHierarchyView c : children) {
			childrenSize += c.getRecursiveNumberOfLines();
		}
		return childrenSize + getNumberOfLines();
	}
	
	private void addCircle(Element root, Document doc, String id, double cx, double cy, double r, RGB fillRGB, RGB strokeRGB) {
		Element element;
		element = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "circle");
		element.setAttributeNS(null, "id", id);
		element.setAttributeNS(null, "cx", Double.toString(cx));
		element.setAttributeNS(null, "cy", Double.toString(cy));
		element.setAttributeNS(null, "r", Double.toString(r));
		element.setAttributeNS(null, "fill", "rgb("+ fillRGB.getRed() +","+ fillRGB.getGreen() +","+ fillRGB.getBlue() + ")");
		element.setAttributeNS(null, "stroke", "rgb("+ strokeRGB.getRed() +","+ strokeRGB.getGreen() +","+ strokeRGB.getBlue() + ")");
		element.setAttributeNS(null, "name", getName());
		element.setAttributeNS(null, "onmousemove", "ShowTooltip(evt)");
		element.setAttributeNS(null, "onmouseout", "HideTooltip()");
		root.appendChild(element);
	}
	
	private void addStyle(Element root, Document doc) {
		Element style = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "style");
		style.setTextContent("circle:hover{opacity:0.7;} .tooltip{font-size: 12px;} .tooltip_bg{fill: white;stroke: black;stroke-width: 1;opacity: 0.85;}");
		root.appendChild(style);
	}
	
	private void addScript(Element root, Document doc) {
		Element script = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "script");
		script.setAttributeNS(null, "type", "text/ecmascript");	
		
		CDATASection s = doc.createCDATASection("function init(evt) {" +
					"if ( window.svgDocument == null ) {"+
						"svgDocument = evt.target.ownerDocument;"+
					"}"+
					"tooltip = svgDocument.getElementById('tooltip');"+
					"tooltip_bg = svgDocument.getElementById('tooltip_bg');"+
				"}"+
				"function ShowTooltip(evt) {"+
					"tooltip.setAttributeNS(null,\"x\",evt.clientX+12+window.pageXOffset);"+
					"tooltip.setAttributeNS(null,\"y\",evt.clientY+28+window.pageYOffset);"+
					"tooltip.setAttributeNS(null,\"visibility\",\"visible\");"+
					"tooltip.firstChild.data = evt.target.getAttributeNS(null,\"name\");"+
					"length = tooltip.getComputedTextLength();"+
					"tooltip_bg.setAttributeNS(null,\"width\",length+8);"+
					"tooltip_bg.setAttributeNS(null,\"x\",evt.clientX+8+window.pageXOffset);"+
					"tooltip_bg.setAttributeNS(null,\"y\",evt.clientY+16+window.pageYOffset);"+
					"tooltip_bg.setAttributeNS(null,\"visibility\",\"visible\");"+
				"}"+
				"function HideTooltip() {"+
					"tooltip.setAttributeNS(null,\"visibility\",\"hidden\");"+
					"tooltip_bg.setAttributeNS(null,\"visibility\",\"hidden\");"+
				"}");
		
		script.appendChild(s);
		root.appendChild(script);
	}

	private void addTooltip(Element root, Document doc) {
		Element rect = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "rect");
		rect.setAttributeNS(null, "class", "tooltip_bg");
		rect.setAttributeNS(null, "id", "tooltip_bg");
		rect.setAttributeNS(null, "x", "0");
		rect.setAttributeNS(null, "y", "0");
		rect.setAttributeNS(null, "rx", "4");
		rect.setAttributeNS(null, "ry", "4");
		rect.setAttributeNS(null, "width", "52");
		rect.setAttributeNS(null, "height", "16");
		rect.setAttributeNS(null, "visibility", "hidden");
		root.appendChild(rect);
		
		Element text = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
		text.setAttributeNS(null, "class", "tooltip");
		text.setAttributeNS(null, "id", "tooltip");
		text.setAttributeNS(null, "x", "0");
		text.setAttributeNS(null, "y", "0");
		text.setAttributeNS(null, "visibility", "hidden");
		text.setAttributeNS(null, "fill", "black");
		text.setTextContent("Tooltip");
		root.appendChild(text);
	}
	
	private void saveFile(Document doc) throws IOException {
		try {
			// Determine output type:
			SVGTranscoder t = new SVGTranscoder();

			// Set transcoder input/output
			TranscoderInput input = new TranscoderInput(doc);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			OutputStreamWriter ostream = new OutputStreamWriter(bytestream, "utf-8");
			TranscoderOutput output = new TranscoderOutput(ostream);
			
			// Perform transcoding
			t.transcode(input, output);
			ostream.flush();
			ostream.close();
			
			FileUtils.writeByteArrayToFile(new File(targetFolder + originFileName + ".svg"), bytestream.toByteArray());
			
		} catch (IOException | TranscoderException e) {
			e.printStackTrace();
		}
	}

	/*
	* Create an SVG document.
	*/
	private Document createSVGDocument() {
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		return impl.createDocument(svgNS, "svg", null);
	}
	
	
}
