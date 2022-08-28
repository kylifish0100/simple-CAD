import java.awt.Color;
import java.awt.Point;

public interface DrawElementFactory {
	DrawElement createElementFromMousePress(String toolcommand, Color color, Point pos);
	DrawElement createElementFromMouseClicked(String toolcommand, Color color, Point pos);
	DrawElement createElementFromLoadFacade(String name, LoadFacade lf);
	void addButtons(ToolBar drawtool);
}
