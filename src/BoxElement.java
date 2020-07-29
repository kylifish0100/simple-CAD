import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * BoxElement - represents a simple rectangle.
 * 
 * @author Eric McCreath
 *
 */

public class BoxElement extends DrawElement {

	Point2D topleft, bottomright;

	public BoxElement(Point2D s, Point2D e) {
		topleft = s;
		bottomright = e;
	}

	public void draw(Graphics2D g) {
		g.draw(new Line2D.Double(topleft, new Point2D.Double(bottomright.getX(), topleft.getY())));
		g.draw(new Line2D.Double(new Point2D.Double(bottomright.getX(), topleft.getY()), bottomright));
		g.draw(new Line2D.Double(bottomright, new Point2D.Double(topleft.getX(), bottomright.getY())));
		g.draw(new Line2D.Double(new Point2D.Double(topleft.getX(), bottomright.getY()), topleft));
	}

	// contralPoints - noting the contral points for a box are: topleft, bottomright, bottomleft, topright, center
	public ArrayList<Point2D> controlPoints() {
		ArrayList<Point2D> controlpoints = new ArrayList<Point2D>();
		controlpoints.add(topleft);
		controlpoints.add(bottomright);
		controlpoints.add(new Point2D.Double(topleft.getX(), bottomright.getY()));
		controlpoints.add(new Point2D.Double(bottomright.getX(), topleft.getY()));
		controlpoints.add(PUtil.mid(topleft, bottomright));
		return controlpoints;
	}

	public void moveControlPoint(int control, Point2D pos) {
		if (control == 0)  // topleft
			topleft = pos;
		else if (control == 1) // bottomright
			bottomright = pos;
		else if (control == 2) { // bottomleft
			bottomright = new Point2D.Double(bottomright.getX(), pos.getY());
			topleft = new Point2D.Double(pos.getX(), topleft.getY());
		} else if (control == 3) { // topright
			bottomright = new Point2D.Double(pos.getX(), bottomright.getY());
			topleft = new Point2D.Double(topleft.getX(), pos.getY());
		} else if (control == 4) { // center
			Point2D vec = PUtil.sub(pos, PUtil.mid(topleft, bottomright));
			topleft = PUtil.add(topleft, vec);
			bottomright = PUtil.add(bottomright, vec);
		}
	}

	@Override
	public void storeelement(StoreFacade sf) {
		sf.start("BoxElement");
		sf.addPoint("topleft", topleft);
		sf.addPoint("bottomright", bottomright);
	}

	public static DrawElement loadelement(LoadFacade lf) {
		return new BoxElement(lf.getPoint("topleft"), lf.getPoint("bottomright"));
	}
}
