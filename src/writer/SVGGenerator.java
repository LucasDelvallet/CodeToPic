package writer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.io.IOException;

import javax.swing.JFrame;

import org.apache.batik.anim.dom.SVGDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Element;
import org.w3c.dom.svg.SVGDocument;

import reader.ToBytes;

/**
 * Work in progress. Generate a SVG from byte[].
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
		byte[] data = covertor.getBytes();
		int imageSize = getImageSize(data.length);
		
		SVGDocument doc = createSVGDocument();
		SVGGraphics2D g = new SVGGraphics2D(doc);
		
		
		/*
		 * 
		 * Grosse boucle qui prendrait les bytes 4 par 4.
		 * Chaque byte peut prendre 256 valeurs du coup l'image serait de 256x256
		 * Le premier byte donne la forme (parmi 256 formes ça sera peut-être chaud par contre)
		 * Le second byte donne la position en x
		 * Le troisième byte donne la position en y
		 * Le quatrième byte donne la couleur (on programmerait 256 couleurs possibles)
		 * 
		 * Et s'il n'y a pas un multiple de 4, on remplit les dernières valeurs par des bytes à 0
		 * 
		 * Vu que c'est un svg, le process inverse devrait être possible.
		 * 
		 */
		
		//Tests
		Shape circle = new Ellipse2D.Double(0, 0, 50, 50);
	    g.setPaint(Color.red);
	    g.fill(circle);
	    g.translate(60, 0);
	    g.setPaint(Color.green);
	    g.fill(circle);
	    g.translate(60, 0);
	    g.setPaint(Color.blue);
	    g.fill(circle);
	    g.setSVGCanvasSize(new Dimension(256, 256));
	    
	    // Populate the document root with the generated SVG content.
	    Element root = doc.getDocumentElement();
	    g.getRoot(root);

	    // Display the document.
	    JSVGCanvas canvas = new JSVGCanvas();
	    JFrame f = new JFrame();
	    f.getContentPane().add(canvas);
	    canvas.setSVGDocument(doc);
	    f.pack();
	    f.setVisible(true);
		
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
