import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * BoxElement - represents a simple rectangle defined by two points.
 * 
 * @author Eric McCreath
 * edited by Matthew Aitichson
 *
 */
public class BoxElement extends DrawElement {

	Point2D topLeft, bottomRight;

	/**
	 * Construct a box element.
	 * @param topLeft top left point
	 * @param bottomRight bottom right point
	 */
	public BoxElement(Point2D topLeft, Point2D bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}

	/**
	 * Draws the rectangle to given Graphics2D context.
	 * @param g Graphics Context.
	 */
	public void draw(Graphics2D g)
	{
		g.draw(new Line2D.Double(topLeft, new Point2D.Double(bottomRight.getX(), topLeft.getY())));
		g.draw(new Line2D.Double(new Point2D.Double(bottomRight.getX(), topLeft.getY()), bottomRight));
		g.draw(new Line2D.Double(bottomRight, new Point2D.Double(topLeft.getX(), bottomRight.getY())));
		g.draw(new Line2D.Double(new Point2D.Double(topLeft.getX(), bottomRight.getY()), topLeft));
	}

	/**
	 * ControlPoints - noting the control points for a box are: topleft, bottomright, bottomleft, topright, center
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
		else if (control == 1) // bottomright
			bottomRight = pos;
		else if (control == 2) { // bottomleft
			bottomRight = new Point2D.Double(bottomRight.getX(), pos.getY());
			topLeft = new Point2D.Double(pos.getX(), topLeft.getY());
		} else if (control == 3) { // topright
			bottomRight = new Point2D.Double(pos.getX(), bottomRight.getY());
			topLeft = new Point2D.Double(topLeft.getX(), pos.getY());
		} else if (control == 4) { // center
			Point2D vec = PUtil.sub(pos, PUtil.mid(topLeft, bottomRight));
			topLeft = PUtil.add(topLeft, vec);
			bottomRight = PUtil.add(bottomRight, vec);
		}
	}

	@Override
	public void storeElement(StoreFacade sf) {
		sf.start("BoxElement");
		sf.addPoint("topleft", topLeft);
		sf.addPoint("bottomright", bottomRight);
	}

	public static DrawElement loadElement(LoadFacade lf) {
		return new BoxElement(lf.getPoint("topleft"), lf.getPoint("bottomright"));
	}
}