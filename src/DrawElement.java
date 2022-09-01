import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

/**
 * DrawElement - all elements that make up the drawing will extend this abstract class. 
 * @author Eric McCreath
 * Edited by Matthew Aitchison
 */
public abstract class DrawElement

{
     /**
      * Draw element to Graphics2D context.
      */
     abstract public void draw(Graphics2D g);
     abstract public void Fill(Graphics2D g);

     abstract public ArrayList<Point2D> controlPoints();

     /**
      * Move a control point
      * @param control index to control point
      * @param pos new position for control point.
      */
     abstract public void moveControlPoint(int control, Point2D pos);

     /**
      * Save element.
      */
     abstract public void storeElement(StoreFacade sf);
     abstract public void addLabelText(String text);
}
