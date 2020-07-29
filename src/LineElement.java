import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * LineElement - represents a simple line with a start and end point.
 * 
 * @author Eric McCreath
 *
 */

public class LineElement extends DrawElement {

	private static final String LINE_ELEMENT = "LineElement";
	Point2D start, end;

	public LineElement(Point2D s, Point2D e) {
		start = s;
		end = e;
	}

	public void draw(Graphics2D g) {
		g.draw(new Line2D.Float(start, end));
	}

	// controlPoints - there is just: start, end, mid
	public ArrayList<Point2D> controlPoints() {
		ArrayList<Point2D> controlpoints = new ArrayList<Point2D>();
		controlpoints.add(start);
		controlpoints.add(end);
		controlpoints.add(PUtil.mid(start, end));
		return controlpoints;
	}

	public void moveControlPoint(int control, Point2D pos) {
		if (control == 0) // start
			start = pos;
		else if (control == 1) // end
			end = pos;
		else if (control == 2) { // mid
			Point2D vec = PUtil.sub(pos, PUtil.mid(start, end));
			start = PUtil.add(start, vec);
			end = PUtil.add(end, vec);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LineElement))
			return false;
		LineElement le = (LineElement) obj;
		return le.end.equals(end) && le.start.equals(start);
	}

	@Override
	public void storeelement(StoreFacade sf) {
		sf.start("LineElement");
		sf.addPoint("start", start);
		sf.addPoint("end", end);
	}

	public static DrawElement loadelement(LoadFacade lf) {
		return new LineElement(lf.getPoint("start"), lf.getPoint("end"));
	}
}
