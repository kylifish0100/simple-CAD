import java.awt.Color;
import java.awt.Point;

public class BasicDrawElementFactory implements DrawElementFactory {
	/**
	 * Factory to create draw elements.
	 */

	public static final String LINETOOL = "LINETOOL";
	public static final String BOXTOOL = "BOXTOOL";
	public static final String CIRCLETOOL = "CIRCLETOOL";
	public static final String TRITOOL = "TRITOOL";
	public static final String POLYGONTOOL = "POLYGONTOOL";
	
	@Override
	public DrawElement createElementFromMousePress(String toolcommand, Color color, Point pos) {
		DrawElement drawelement = null;
		if (toolcommand.equals(LINETOOL)) { 
			drawelement = new LineElement(pos,pos);
		} else if (toolcommand.equals(BOXTOOL)) {
			drawelement = new BoxElement(pos,pos);
		} else if (toolcommand.equals(CIRCLETOOL)) {
			drawelement = new CircleElement(pos,pos);
		} else if (toolcommand.equals(TRITOOL)) {
			drawelement = new TriElement(pos,pos,pos);
		} else if (toolcommand.equals(POLYGONTOOL)) {
			drawelement = new BoxElement(pos,pos);
		}
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
		} else {
			System.err.println("Unknown Element");
		}
		Integer color = lf.getInteger("color");
		if (color != null) element = new ColorDrawElement(element,new Color(color));
		
		return element;
	}

	@Override
	public void addButtons(ToolBar drawtool) {
		drawtool.addButton("Line",LINETOOL);
     	drawtool.addButton("Box",BOXTOOL);
     	drawtool.addButton("Circle",CIRCLETOOL);
     	drawtool.addButton("Triangle",TRITOOL);
     	drawtool.addButton("Polygon",POLYGONTOOL);
	}

}
