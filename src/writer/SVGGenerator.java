package writer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;

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
		System.out.print("Generating SVG document...");
		
		byte[] data = convertor.getBytes();
		
		Document doc = createSVGDocument();
		Element root = doc.getDocumentElement();
		root.setAttributeNS(null, "width", "256");
        root.setAttributeNS(null, "height", "256");
		
		int rest = data.length % 6;
		String svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI;
		
		for(int i=0; i<data.length-rest; i+=6) {
			int data1 = data[i]+128;
			int data2 = data[i+1]+128;
			int data3 = data[i+2]+128;
			int data4 = data[i+3];
			int data5 = data[i+4];
			int data6 = data[i+5]+128;

			Element element;
			element = doc.createElementNS(svgNS, "circle");
			element.setAttributeNS(null, "cx", String.valueOf(data4));
			element.setAttributeNS(null, "cy", String.valueOf(data5));
			element.setAttributeNS(null, "r", String.valueOf((float)data6/4));
			element.setAttributeNS(null, "fill", "rgb("+ data1 +","+ data2 +","+ data3 + ")");
			root.appendChild(element);
		}
		
		Element element;
		element = doc.createElementNS(svgNS, "g");
		for(int i=0; i<rest; i++){
			String end = "end";
			for(int j=0; j<i; j++) end += "end";
			element.setAttributeNS(null, end, String.valueOf(data[i+(data.length-rest)]));			
		}
		root.appendChild(element);		
		
	    saveFile(doc);
		
		System.out.println(" Done !");
	}
	
	private void saveFile(Document doc) throws IOException {
	  try {
	        //Determine output type:
	        SVGTranscoder t = new SVGTranscoder();

	        //Set transcoder input/output
	        TranscoderInput input = new TranscoderInput(doc);
	        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
	        OutputStreamWriter ostream = new OutputStreamWriter(bytestream);
	        TranscoderOutput output = new TranscoderOutput(ostream);

	        //Perform transcoding
	        t.transcode(input, output);
	        ostream.flush();
	        ostream.close();
	        
	        FileUtils.writeByteArrayToFile(new File(targetFolder + "file.svg"), bytestream.toByteArray());

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
