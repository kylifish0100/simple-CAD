import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * DrawElement - all elements that make up the drawing will extend this abstract class. 
 * @author Eric McCreath
 *
 */

public abstract class DrawElement {
     abstract public void draw(Graphics2D g);
     abstract public ArrayList<Point2D> controlPoints();
     abstract public void moveControlPoint(int control, Point2D pos);
     abstract public void storeelement(StoreFacade sf);
}
