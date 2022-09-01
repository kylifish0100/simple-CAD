import DrawElements.DrawElement;

/**
 * ElementControlPoint - this class is used to reference the draw element of the control point and 
 * the index of the control of the list of control points of the element. 
 * @author Eric McCreath
 *
 */
public class ElementControlPoint {
	DrawElement element;
	int control;

	public ElementControlPoint(DrawElement element, int control) {
		super();
		this.element = element;
		this.control = control;
	}
}
