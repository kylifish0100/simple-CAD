package DrawElements;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import Facade.*;

/**
 * TriangleElement - represents a triangle defined by three points
 * 
 * @author Eric McCreath
 * edited by Yuhan Zhang
 *
 */
public class TriElement extends DrawElement {

	Point2D vertexA, vertexB, vertexC;

	/**
	 * Construct a triangle.
	 * vertexA, vertexB and vertexC represents three vertices of a triangle.
	 */
	public TriElement(ArrayList<Point2D> coordinates) {
		this.vertexA = coordinates.get(0);
		this.vertexB = coordinates.get(1);
		this.vertexC = coordinates.get(2);
	}

	/**
	 * Draws the Triangle to given Graphics2D context.
	 * @param g Graphics Context.
	 */
	public void draw(Graphics2D g)
	{
		int x0 = (int)vertexA.getX();
		int y0 = (int)vertexA.getY();
		int x1 = (int)vertexB.getX();
		int y1 = (int)vertexB.getY();
		int x2 = (int)vertexC.getX();
		int y2 = (int)vertexC.getY();
		g.drawPolygon(new int[] {x0, x1, x2}, new int[] {y0, y1, y2}, 3);
	}

	@Override
	public void Fill(Graphics2D g) {
		int x0 = (int)vertexA.getX();
		int y0 = (int)vertexA.getY();
		int x1 = (int)vertexB.getX();
		int y1 = (int)vertexB.getY();
		int x2 = (int)vertexC.getX();
		int y2 = (int)vertexC.getY();
		g.fillPolygon(new int[] {x0, x1, x2}, new int[] {y0, y1, y2}, 3);
	}

	/**
	 * ControlPoints - noting the control points for a circle are: topleft, bottomright, bottomleft, topright, center
	 * @return array of four control points.
	 */
	public ArrayList<Point2D> controlPoints() {
		ArrayList<Point2D> controlpoints = new ArrayList<Point2D>();
		controlpoints.add(vertexA);
		controlpoints.add(vertexB);
		controlpoints.add(vertexC);
		controlpoints.add(new Point2D.Double((vertexA.getX() + vertexB.getX()+vertexC.getX())/3.0,
				(vertexA.getY() + vertexB.getY()+vertexC.getY())/3.0));
		return controlpoints;
	}

	/**
	 * Process moving of a control point.
	 * @param control index to control point
	 * @param pos new position.
	 */
	public void moveControlPoint(int control, Point2D pos) {
		if (control == 0)  // top vertex
			vertexA = pos;
		else if (control == 1) // bottom left vertex
			vertexB = pos;
		else if (control == 2) { // bottom right vertex
			vertexC = pos;
		} 
	}

	@Override
	public void storeElement(StoreFacade sf) {
		sf.start("TriangleElement");
		sf.addPoint("top", vertexA);
		sf.addPoint("bottomleft", vertexB);
		sf.addPoint("bottomright", vertexC);
	}

	@Override
	public void addLabelText(String text) {

	}

	public static DrawElement loadElement(LoadFacade lf) {
		ArrayList<Point2D> triangleVertices = new ArrayList<>();
		triangleVertices.add(lf.getPoint("top"));
		triangleVertices.add(lf.getPoint("bottomleft"));
		triangleVertices.add(lf.getPoint("bottomright"));
		return new TriElement(triangleVertices);
	}
}
