import DrawElements.DrawElement;
import Facade.LoadFacade;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface DrawElementFactory {
	DrawElement createElementFromMousePress(String toolcommand, Color color, Point pos);
	DrawElement createElementFromMouseClicked(String toolcommand, Color color, ArrayList<Point2D> coordinates);
	DrawElement fillElementWithColor(Color color, DrawElement notColoredEle);
	DrawElement createEmptyLabel(Color color, Point pos);
	DrawElement createElementFromLoadFacade(String name, LoadFacade lf);
	void addButtons(ToolBar drawtool);
}
