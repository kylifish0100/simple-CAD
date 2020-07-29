import java.awt.Color;
import java.awt.Point;


public class BasicDrawElementFactory implements DrawElementFactory {
	public static final String LINETOOL = "LINETOOL";
	public static final String BOXTOOL = "BOXTOOL";
	@Override
	public DrawElement createElementFromMousePress(String toolcommand, Color color, Point pos) {
		DrawElement drawelement = null;
		if (toolcommand.equals(LINETOOL)) { 
			drawelement = new LineElement(pos,pos);
		} else if (toolcommand.equals(BOXTOOL)) {
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
			element = LineElement.loadelement(lf);
		} else if (name.equals("BoxElement")) {
			element = BoxElement.loadelement(lf);
		} else {
			System.err.println("Unknow Element");
		}
		Integer color = lf.getInteger("color");
		if (color != null) element = new ColorDrawElement(element,new Color(color));
		
		return element;
	}

	@Override
	public void addbuttons(ToolBar drawtool) {
		drawtool.addbutton("Line",LINETOOL);
     	drawtool.addbutton("Box",BOXTOOL);
	}

}