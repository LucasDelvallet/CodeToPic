package hierarchyView.generator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import hierarchyView.RGB;

/**
 * Represent a hierarchic view
 */
public interface IHierarchyView {
	/**
	 * Add a child view or Hierachic view
	 */
	public void addChild(IHierarchyView view);
	/**
	 * Get this strokeolor definition
	 */
	public RGB getStrokeColor();
	/**
	 * Get this backgroundColor definition
	 */
	public RGB getBackgroundColor();
	/**
	 * Get quantity of recurcive line
	 */
	public int getRecursiveNumberOfLines();
	/**
	 * Get quantity of lines
	 */
	public int getNumberOfLines();
	/**
	 * Add an svg element
	 * @param root the element to add
	 * @param doc the container
	 * @param deltaY the Y axis delta of the view 
	 * @param deltaX the X axis delta of the view
	 */
	public void addSVGElement(Element root, Document doc, double deltaY, double deltaX);
}
