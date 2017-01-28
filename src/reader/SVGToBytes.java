package reader;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.ArrayUtils;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 * @author Quentin
 *
 */
public class SVGToBytes extends ToBytes {

	private Document svgDocument;
	
	public SVGToBytes(String path) {
		super(path);
	}

	@Override
	public byte[] getBytes() throws IOException {
		NodeList circles = parseSVG();
		
		byte[] bytes = {};
		
		for(int i=0; i<circles.getLength(); i++) {
			Node circle = circles.item(i);
			NamedNodeMap NNM = circle.getAttributes();
			
			String color = NNM.getNamedItem("fill").toString();
			String[] colors = color.split(",");
			
			for(int j=0; j<colors.length; j++) {
				int byteInInteger = Integer.parseInt(colors[j].replaceAll("[^\\d.]", ""));
				byteInInteger -= 128;
				byte[] b = ByteBuffer.allocate(4).putInt(byteInInteger).array();	
				b = ArrayUtils.removeElement(b, (byte)0x00);
				b = ArrayUtils.removeElement(b, (byte)0x00);
				b = ArrayUtils.removeElement(b, (byte)0x00);
						
				bytes = concatByteArrays(bytes, b);
			}
			
			String cx = NNM.getNamedItem("cx").toString();
			int i_cx = Integer.parseInt(cx.replaceAll("[^\\d.]", ""));
			byte[] b_cx = ByteBuffer.allocate(4).putInt(i_cx).array();	
			b_cx = ArrayUtils.removeElement(b_cx, (byte)0x00);
			b_cx = ArrayUtils.removeElement(b_cx, (byte)0x00);
			b_cx = ArrayUtils.removeElement(b_cx, (byte)0x00);
			bytes = concatByteArrays(bytes, b_cx);
			
			String cy = NNM.getNamedItem("cy").toString();
			int i_cy = Integer.parseInt(cy.replaceAll("[^\\d.]", ""));
			byte[] b_cy = ByteBuffer.allocate(4).putInt(i_cy).array();	
			b_cy = ArrayUtils.removeElement(b_cy, (byte)0x00);
			b_cy = ArrayUtils.removeElement(b_cy, (byte)0x00);
			b_cy = ArrayUtils.removeElement(b_cy, (byte)0x00);
			bytes = concatByteArrays(bytes, b_cy);
			
			String r = NNM.getNamedItem("r").toString();
			r = r.replaceAll("[^\\d.]", "");
			r = String.valueOf(Math.round(Double.valueOf(r)*4));
			int i_r = (Integer.parseInt(r))-128;
			byte[] b_r = ByteBuffer.allocate(4).putInt(i_r).array();
			b_r = ArrayUtils.removeElement(b_r, (byte)0x00);
			b_r = ArrayUtils.removeElement(b_r, (byte)0x00);
			b_r = ArrayUtils.removeElement(b_r, (byte)0x00);
			bytes = concatByteArrays(bytes, b_r);
		}		

		return bytes;
	}
	
	private <T> byte[] concatByteArrays(byte[] bytes, byte[] b) {
		  byte[] result = Arrays.copyOf(bytes, bytes.length + b.length);
		  System.arraycopy(b, 0, result, bytes.length, b.length);
		  return result;
		}
	
	private NodeList parseSVG() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		Document document = null;
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			System.out.println(path);
			document = builder.parse(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String xpathExpression = "//circle";
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xpath = xpf.newXPath();
		XPathExpression expression;
		NodeList circles = null;
		
		try {
			expression = xpath.compile(xpathExpression);		
			circles = (NodeList)expression.evaluate(document, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		return circles;
	}

}
