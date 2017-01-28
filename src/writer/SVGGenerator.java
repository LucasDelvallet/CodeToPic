package writer;

import java.awt.Color;
import java.awt.Dimension;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.svg.SVGDocument;

import reader.ToBytes;

/**
 * Work in progress. Generate a SVG from byte[].
 * https://xmlgraphics.apache.org/batik/using/svg-generator.html
 * 
 * @author Quentin
 *
 */
public class SVGGenerator extends Generator {

	public SVGGenerator(ToBytes codeCovertor, String imagePath) {
		super(codeCovertor, imagePath);
	}

	@Override
	public void generate(Extension e) throws IOException {
		byte[] data = convertor.getBytes();
		int imageSize = getImageSize(data.length);
		
		SVGDocument doc = createSVGDocument();
		SVGGraphics2D g = new SVGGraphics2D(doc);
		
		g.setSVGCanvasSize(new Dimension(256, 256));
		
		int rest = data.length % 6;
		
		for(int i=0; i<data.length-rest; i+=6) {
			int data1 = data[i]+128;
			int data2 = data[i+1]+128;
			int data3 = data[i+2]+128;
			int data4 = data[i+3];
			int data5 = data[i+4];
			int data6 = data[i+5]+128;

			Color c = new Color(data1, data2, data3);
			g.setColor(c);
			g.fillOval(data4, data5, data6, data6);
		}
		
	    // Save SVG as file
	    OutputStream outputStream = new FileOutputStream(targetFolder + "file.svg");
		Writer out = new OutputStreamWriter(outputStream, "UTF-8");
		g.stream(out, true);
		outputStream.flush();
		outputStream.close();
		
		System.out.println("Done !");
	}
	
	private SVGDocument createSVGDocument() {
		// Create an SVG document.
	    DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
	    String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
	    return (SVGDocument) impl.createDocument(svgNS, "svg", null);
	}
	
	private int getImageSize(int nbByte) {
		int nbRVB = (nbByte / 3) + (nbByte % 3 > 0 ? 1 : 0);
		int imageSize = (int) Math.sqrt(nbRVB);
		if((imageSize * imageSize) < nbRVB) {
			imageSize ++;
		}
		return imageSize;
	}
}
