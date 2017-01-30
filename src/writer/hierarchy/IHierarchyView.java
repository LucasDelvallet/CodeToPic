package writer.hierarchy;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import util.RGB;

public interface IHierarchyView {
	public void addChild(IHierarchyView view);
	public RGB getNameColor();
	public RGB getCodeColor();
	public int getNumberOfLines();
	public void addSVGElement(Element root, Document doc, double deltaY, double deltaX);
}
