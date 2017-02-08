package hierarchyView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public interface IHierarchyView {
	public void addChild(IHierarchyView view);
	public RGB getStrokeColor();
	public RGB getBackgroundColor();
	public int getRecursiveNumberOfLines();
	public int getNumberOfLines();
	public void addSVGElement(Element root, Document doc, double deltaY, double deltaX);
}
