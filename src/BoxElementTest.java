import static org.junit.Assert.assertEquals;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class BoxElementTest {

	private Point2D p1;
	private Point2D p2;
	private BoxElement be;

	@Before
	public void setup() {
		p1 = new Point2D.Double(100, 130);
		p2 = new Point(120, 135);
		be = new BoxElement(p1, p2);
	}

	@Test
	public void testConstructor() {

		assertEquals(p1, be.topleft);
		assertEquals(p2, be.bottomright);
	}

	@Test
	public void testcontrolPoints() {
		ArrayList<Point2D> points = be.controlPoints();
		
		ArrayList<Point2D> expected = new ArrayList<Point2D>();
		expected.add(p1);
		expected.add(p2);
		expected.add(new Point2D.Double(p1.getX(),p2.getY()));
		expected.add(new Point2D.Double(p2.getX(),p1.getY()));
		assertEquals(points, expected);
	}
	
	@Test
	public void test1moveControlPoint() {
		Point2D p3 = new Point2D.Double(23,40);
		be.moveControlPoint(0, p3);
		assertEquals(be.topleft,p3);
		assertEquals(be.bottomright, p2);
	}
	
	@Test
	public void test2moveControlPoint() {
		Point2D p3 = new Point2D.Double(105,140);
		be.moveControlPoint(2, p3);
		assertEquals(be.topleft,new Point2D.Double(105,p1.getY()));
		assertEquals(be.bottomright, new Point2D.Double(p2.getX(),140));
	}
}
