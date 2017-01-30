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

	private Document doc;
	private NodeList circles;
	private NodeList group;
	
	public SVGToBytes(String path) {
		super(path);
	}

	@Override
	public byte[] getBytes() throws IOException {
		parseSVG();
		
		byte[] bytes = {};
		
		for(int i=0; i<circles.getLength(); i++) {
			Node circle = circles.item(i);
			NamedNodeMap namedNodeMap = circle.getAttributes();			
			
			// Recuperation des bytes du parametre fill
			String color = namedNodeMap.getNamedItem("fill").toString();
			String[] colors = color.split(",");			
			for(int j=0; j<colors.length; j++) {
				int byteInInteger = Integer.parseInt(colors[j].replaceAll("[^\\d.]", ""));
				byteInInteger -= 128;						
				bytes = concatByteArrays(bytes, intToBytes(byteInInteger));
			}
			
			// Recuperation des bytes du parametre cx
			String cx = namedNodeMap.getNamedItem("cx").toString();
			int i_cx = Integer.parseInt(cx.replaceAll("[^\\d.]", ""));
			i_cx-=128;
			bytes = concatByteArrays(bytes, intToBytes(i_cx));
			
			// Recuperation des bytes du parametre cy
			String cy = namedNodeMap.getNamedItem("cy").toString();			
			int i_cy = Integer.parseInt(cy.replaceAll("[^\\d.]", ""));
			i_cy-=128;
			bytes = concatByteArrays(bytes,  intToBytes(i_cy));
			
			// Recuperation des bytes du parametre r
			String r = namedNodeMap.getNamedItem("r").toString();
			r = r.replaceAll("[^\\d.]", "");
			r = String.valueOf(Math.round(Double.valueOf(r)*4));			
			int i_r = (Integer.parseInt(r))-128;			
			bytes = concatByteArrays(bytes, intToBytes(i_r));
		}
		
		// Recuperation des bytes de fin de fichier
		Node g = group.item(0);
		NamedNodeMap namedNodeMap = g.getAttributes();		
		for(int i=0; i<namedNodeMap.getLength(); i++) {
			String node = namedNodeMap.item(i).toString();
			int nodeValue = Integer.parseInt(node.replaceAll("[^\\d.]", ""));
			bytes = concatByteArrays(bytes, intToBytes(nodeValue));
		}
		

		return bytes;
	}
	
	private byte[] intToBytes(int value) {
		byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
		bytes = ArrayUtils.removeElement(bytes, (byte)0x00);
		bytes = ArrayUtils.removeElement(bytes, (byte)0x00);
		return ArrayUtils.removeElement(bytes, (byte)0x00);
	}
	
	private <T> byte[] concatByteArrays(byte[] bytes, byte[] b) {
		  byte[] result = Arrays.copyOf(bytes, bytes.length + b.length);
		  System.arraycopy(b, 0, result, bytes.length, b.length);
		  return result;
		}
	
	private void parseSVG() {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			System.out.println(path);
			doc = builder.parse(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		initCircles();
		initGroup();
	}
	
	private void initCircles() {
		String xpathExpression = "//circle";
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xpath = xpf.newXPath();
		XPathExpression expression;
		
		try {
			expression = xpath.compile(xpathExpression);		
			circles = (NodeList)expression.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
	
	private void initGroup() {
		String xpathExpression = "//g";
		XPathFactory xpf = XPathFactory.newInstance();
		XPath xpath = xpf.newXPath();
		XPathExpression expression;
		
		try {
			expression = xpath.compile(xpathExpression);		
			group = (NodeList)expression.evaluate(doc, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
	}
}
