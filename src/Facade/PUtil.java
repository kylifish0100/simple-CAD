package Facade;

import java.awt.geom.Point2D;

/**
 * Facade.PUtil - a utility class for calculations on points
 * Eric McCreath 2020
 */
public class PUtil {

	public static Point2D mid (Point2D p1, Point2D p2) {
		return new Point2D.Double((p1.getX() + p2.getX())/2.0,(p1.getY() + p2.getY())/2.0) ;
	}
	
	public static Point2D sub (Point2D p1, Point2D p2) {
		return new Point2D.Double((p1.getX() - p2.getX()),(p1.getY() - p2.getY())) ;
	}
	
	public static Point2D add (Point2D p1, Point2D p2) {
		return new Point2D.Double((p1.getX() + p2.getX()),(p1.getY() + p2.getY())) ;
	}
	
	public static Point2D scale (Point2D p1, double s) {
		return new Point2D.Double((p1.getX() * s),(p1.getY() * s)) ;
	}
}
