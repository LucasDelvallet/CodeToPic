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
	private ArrayList<IHierarchyView> children;

	public CircleViewGenerator(ToBytes convertor, String targetFolder) {
		super(convertor, targetFolder);
		children = new ArrayList<>();
	}

	@Override
	public void generate(Extension e) throws IOException {
		String diameter = Integer.toString(getRecursiveNumberOfLines());
		Document doc = createSVGDocument();
		Element root = doc.getDocumentElement();
		root.setAttributeNS(null, "width", diameter);
        root.setAttributeNS(null, "height", diameter);
        addSVGElement(root, doc, 0, 0);
		saveFile(doc);
	}
	
	@Override
	public void addSVGElement(Element root, Document doc, double deltaY, double deltaX) {
		double rayon = getRecursiveNumberOfLines() / (double)2;
		
		Element element;
		element = doc.createElementNS(SVGDOMImplementation.SVG_NAMESPACE_URI, "circle");
		element.setAttributeNS(null, "cx", Double.toString(rayon + deltaX));
		element.setAttributeNS(null, "cy", Double.toString(rayon + deltaY));
		element.setAttributeNS(null, "r", Double.toString(rayon));
		RGB fill = getCodeColor();
		element.setAttributeNS(null, "fill", "rgb("+ fill.getRed() +","+ fill.getGreen() +","+ fill.getBlue() + ")");
		RGB stroke = getNameColor();
		element.setAttributeNS(null, "stroke", "rgb("+ stroke.getRed() +","+ stroke.getGreen() +","+ stroke.getBlue() + ")");
		root.appendChild(element);
		
		int childDeltaX = 0;
		for(IHierarchyView c : children) {
			c.addSVGElement(root, doc, rayon - (c.getRecursiveNumberOfLines()/2), deltaX + childDeltaX);
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
