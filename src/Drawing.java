import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;

/**
 * Drawing - this is a list of draw elements which make up the drawing
 * @author Eric McCreath
 */

public class Drawing extends ArrayList<DrawElement> {
	private static final long serialVersionUID = 1L;
	private static final String DRAWING = "Drawing";
	private static final int CONTROLPOINTRADIUS = 5;

	DrawElementFactory drawElementFactory;

	public Drawing(DrawElementFactory drawElementFactory) {
		this.drawElementFactory = drawElementFactory;
	}

	/**
	 * Draw each element in our 'drawing'.
	 * @param g Graphics2D context.
	 */
	public void draw(Graphics2D g) {
		for (DrawElement d : this) d.draw(g);
	}

	public ElementControlPoint findControl(Point point) {
		for (DrawElement d : this) {
			ArrayList<Point2D>  cps = d.controlPoints();
			for (int i = 0;i<cps.size();i++) {  // return the first control point within the limited radius
				if (cps.get(i).distance(point) < CONTROLPOINTRADIUS) return new ElementControlPoint(d,i);
			}
		}
		return null;
	}

	public void clearDrawing() {
		clear();
	}

	public void save(File file) {
		StoreFacade sf = new StoreFacade(file, "Drawing");
		for (DrawElement de : this) de.storeElement(sf);
		sf.close();
	}

	public static Drawing load(File file, DrawElementFactory drawElementFactory) {
		LoadFacade lf = LoadFacade.load(file);
		Drawing drawing = new Drawing(drawElementFactory);
		String name;
		while ((name = lf.nextElement()) != null) {
			DrawElement element = drawElementFactory.createElementFromLoadFacade(name, lf);
			drawing.add(element);
		}
		return drawing;
	}
}
