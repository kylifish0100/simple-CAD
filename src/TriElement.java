import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * TriangleElement - represents a triangle defined by three points
 * 
 * @author Eric McCreath
 * edited by Yuhan Zhang
 *
 */
public class TriElement extends DrawElement {

	Point2D topLeft, bottomLeft, bottomRight;

	/**
	 * Construct a box element.
	 * @param topLeft top left point
	 * @param bottomRight bottom right point
	 */
	public TriElement(Point2D topLeft, Point2D bottomLeft, Point2D bottomRight) {
		this.topLeft = topLeft;
		this.bottomLeft = bottomLeft;
		this.bottomRight = bottomRight;
	}

	/**
	 * Draws the Triangle to given Graphics2D context.
	 * @param g Graphics Context.
	 */
	public void draw(Graphics2D g)
	{
//		double x1 = topLeft.getX();
//		double y1 = topLeft.getY();
//		double x2 = bottomLeft.getX();
//		double y2 = bottomLeft.getY();
//		double x3 = bottomRight.getX();
//		double y3 = bottomRight.getY();
		g.draw(new Line2D.Double(topLeft,bottomLeft));
		g.draw(new Line2D.Double(topLeft,bottomRight));
		g.draw(new Line2D.Double(bottomLeft,bottomRight));

//		g.drawPolygon(new int[] {x1, x2, x3}, new int[] {y1, y2, y3}, 3);
	}

	/**
	 * ControlPoints - noting the control points for a circle are: topleft, bottomright, bottomleft, topright, center
	 * @return array of four control points.
	 */
	public ArrayList<Point2D> controlPoints() {
		ArrayList<Point2D> controlpoints = new ArrayList<Point2D>();
		controlpoints.add(topLeft);
		controlpoints.add(bottomRight);
		controlpoints.add(new Point2D.Double(topLeft.getX(), bottomRight.getY()));
		controlpoints.add(new Point2D.Double(bottomRight.getX(), topLeft.getY()));
		controlpoints.add(PUtil.mid(topLeft, bottomRight));
		return controlpoints;
	}

	/**
	 * Process moving of a control point.
	 * @param control index to control point
	 * @param pos new position.
	 */
	public void moveControlPoint(int control, Point2D pos) {
		if (control == 0)  // topleft
			topLeft = pos;
		else if (control == 1) // bottomleft
			bottomRight = pos;
		else if (control == 2) { // bottomright
			bottomRight = pos;
		} 
	}

	@Override
	public void storeElement(StoreFacade sf) {
		sf.start("TriElement");
		sf.addPoint("topleft", topLeft);
		sf.addPoint("bottomleft", bottomLeft);
		sf.addPoint("bottomright", bottomRight);
	}

	public static DrawElement loadElement(LoadFacade lf) {
		return new TriElement(lf.getPoint("topleft"), lf.getPoint("bottomleft"), lf.getPoint("bottomright"));
	}
}
