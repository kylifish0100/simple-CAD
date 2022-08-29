import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BasicDrawElementFactory implements DrawElementFactory {
	/**
	 * Factory to create draw elements.
	 */

	public static final String LINETOOL = "LINETOOL";
	public static final String BOXTOOL = "BOXTOOL";
	public static final String CIRCLETOOL = "CIRCLETOOL";
	public static final String TRITOOL = "TRITOOL";
	public static final String POLYGONTOOL = "POLYGONTOOL";
	public static final String LABELTOOL = "LABELTOOL";
	public static final String FILLERTOOL = "FILLERTOOL";
	
	@Override
	public DrawElement createElementFromMousePress(String toolcommand, Color color, Point pos) {
		DrawElement drawelement = null;
		if (toolcommand.equals(LINETOOL)) { 
			drawelement = new LineElement(pos,pos);
		} else if (toolcommand.equals(BOXTOOL)) {
			drawelement = new BoxElement(pos,pos);
		} else if (toolcommand.equals(CIRCLETOOL)) {
			drawelement = new CircleElement(pos,pos);
		}
		System.out.println("pos.x"+pos.getX()+"pos.y"+pos.getY());
		drawelement = new ColorDrawElement(drawelement,
				color);
		return drawelement;
	}
	public DrawElement createElementFromMouseClicked(String toolcommand, Color color, ArrayList<Point2D> coordinates){
		DrawElement drawelement = null;
		if(toolcommand.equals(TRITOOL)){
			drawelement = new TriElement(coordinates);
		} else if (toolcommand.equals(POLYGONTOOL)) {
			drawelement = new TriElement(coordinates);
		}
		drawelement = new ColorDrawElement(drawelement,
				color);
		return drawelement;
	}

	@Override
	public DrawElement createEmptyLabel(Color color, Point pos) {
		Point pos_start = new Point(pos.x-50,pos.y-15);
		Point pos_end = new Point(pos.x+50,pos.y+15);
		DrawElement drawelement = new LabelElement(pos_start,pos_end);
		System.out.println("pos"+pos+"pos_end"+pos_end);
		drawelement = new ColorDrawElement(drawelement,
				color);
		return drawelement;
	}

	@Override
	public DrawElement createElementFromLoadFacade(String name, LoadFacade lf) {
		DrawElement element = null;
		if (name.equals("LineElement")) {
			element = LineElement.loadElement(lf);
		} else if (name.equals("BoxElement")) {
			element = BoxElement.loadElement(lf);
		} else if (name.equals("CircleElement")) {
			element = CircleElement.loadElement(lf);
		} else if (name.equals("TriangleElement")) {
			element = TriElement.loadElement(lf);
		} else if (name.equals("CircleElement")) {
			element = CircleElement.loadElement(lf);
		} else if (name.equals("LabelElement")) {
			element = CircleElement.loadElement(lf);
		} else {
			System.err.println("Unknown Element");
		}
		Integer color = lf.getInteger("color");
		if (color != null) element = new ColorDrawElement(element,new Color(color));
		
		return element;
	}

	@Override
	public void addButtons(ToolBar drawtool) {
		drawtool.addButton("Line", LINETOOL);
		drawtool.addButton("Box", BOXTOOL);
		drawtool.addButton("Circle", CIRCLETOOL);
		drawtool.addButton("Triangle", TRITOOL);
		drawtool.addButton("Polygon", POLYGONTOOL);
		drawtool.addButton("Add label",LABELTOOL);
	}

}
