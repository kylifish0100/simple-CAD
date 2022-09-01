import DrawElements.DrawElement;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/*
 * DrawElementDecorator - the decorator class for elements
 * Eric McCreath
 */


public abstract class DrawElementDecorator extends DrawElement {

	DrawElement drawElement; // this is the one being decorated
	
	@Override
	public void draw(Graphics2D g) {  // often this would get overwritten 
		drawElement.draw(g);
	}
	public void Fill(Graphics2D g) { drawElement.Fill(g); }
	@Override
	public ArrayList<Point2D> controlPoints() {
		return drawElement.controlPoints();    // this can just pass through
	}

	@Override
	public void moveControlPoint(int control, Point2D pos) {
		drawElement.moveControlPoint(control, pos); // this can also just pass through
	}
}
