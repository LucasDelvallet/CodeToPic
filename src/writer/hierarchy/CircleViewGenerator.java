package writer.hierarchy;

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
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import reader.ToBytes;
import util.RGB;
import writer.Generator;

public abstract class CircleViewGenerator extends Generator implements IHierarchyView {
	protected ArrayList<IHierarchyView> children;
	private static int elementID = 0;

	public CircleViewGenerator(ToBytes convertor, String targetFolder) {
		super(convertor, targetFolder);
		children = new ArrayList<>();
	}
	
	public abstract String getName();
	
	@Override
	public void generate(Extension e) throws IOException {
		String diameter = Integer.toString(getRecursiveNumberOfLines());
		Document doc = createSVGDocument();
		Element root = doc.getDocumentElement();
		root.setAttributeNS(null, "width", diameter);
        root.setAttributeNS(null, "height", diameter);
        addStyle(root, doc);
        addSVGElement(root, doc, 0, 0);
		saveFile(doc);
	}
	
	@Override
	public void addSVGElement(Element root, Document doc, double deltaY, double deltaX) {
		double rayon = getRecursiveNumberOfLines() / (double)2;
		elementID ++;
		
		addCircle(root, doc, elementID+"", rayon + deltaX, rayon + deltaY, rayon, getBackgroundColor(), getStrokeColor());
		addLabelOnHover(root, doc, elementID + "", getName());
		
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
		root.appendChild(element);
	}
	
	private void addLabelOnHover(Element root, Document doc, String id, String label) {
		Element text, attibute;
		text = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "text");
		double x = Double.parseDouble(root.getAttribute("width"));
		text.setAttributeNS(null, "x", Double.toString(x/2));
		double y = Double.parseDouble(root.getAttribute("height"));
		text.setAttributeNS(null, "y", Double.toString(y*9/10));
		text.setAttributeNS(null, "font-size", "10");
		text.setAttributeNS(null, "fill", "orange");
		text.setAttributeNS(null, "visibility", "hidden");
		text.setTextContent(label);
		
		attibute = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "set");
		attibute.setAttributeNS(null, "attributeName", "visibility");
		attibute.setAttributeNS(null, "from", "hidden");
		attibute.setAttributeNS(null, "to", "visible");
		attibute.setAttributeNS(null, "begin", id + ".mouseover");
		attibute.setAttributeNS(null, "end", id + ".mouseout");
		
		text.appendChild(attibute);
		root.appendChild(text);
	}
	
	private void addStyle(Element root, Document doc) {
		Element style = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "style");
		style.setTextContent("circle:hover{opacity:0.7;}");
		root.appendChild(style);
	}

	private void saveFile(Document doc) throws IOException {
		try {
			// Determine output type:
			SVGTranscoder t = new SVGTranscoder();

			// Set transcoder input/output
			TranscoderInput input = new TranscoderInput(doc);
			ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
			OutputStreamWriter ostream = new OutputStreamWriter(bytestream);
			TranscoderOutput output = new TranscoderOutput(ostream);

			// Perform transcoding
			t.transcode(input, output);
			ostream.flush();
			ostream.close();

			FileUtils.writeByteArrayToFile(new File(targetFolder + fileName + ".svg"), bytestream.toByteArray());

		} catch (IOException | TranscoderException e) {
			e.printStackTrace();
		}
	}

	private Document createSVGDocument() {
		// Create an SVG document.
		DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		return impl.createDocument(svgNS, "svg", null);
	}
	
	
}
