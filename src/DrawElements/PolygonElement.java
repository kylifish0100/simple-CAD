package DrawElements;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;

import Facade.*;

public class PolygonElement extends DrawElement {
    Integer verticesCount;
    Polygon p;

    ArrayList<Point2D> coordinates;
    private Map<String, Double> measurement;

    /**
     * Construct a Polygons with up to 50 sides.
     */
    public PolygonElement(ArrayList<Point2D> coordinates) {
        this.verticesCount = coordinates.size();
        this.coordinates = coordinates;
        this.p = new Polygon();
        for (Point2D pt : coordinates){
            p.addPoint((int)pt.getX(),(int)pt.getY());
        }
    }

    /**
     * Draws the Polygon to given Graphics2D context.
     * @param g Graphics Context.
     */
    public void draw(Graphics2D g)
    {
        int[] coor_x_array = new int[verticesCount];
        int[] coor_y_array = new int[verticesCount];
        ArrayList<Integer> coor_x = new ArrayList<>();
        ArrayList<Integer> coor_y = new ArrayList<>();
        for(int i = 0;i< verticesCount;i++){
            coor_x_array[i]=(int)coordinates.get(i).getX();
            coor_y_array[i]=(int)coordinates.get(i).getY();
        }
        g.drawPolygon(coor_x_array,coor_y_array,verticesCount);
    }

    @Override
    public void Fill(Graphics2D g) {
        g.fillPolygon(p);
    }

    /**
     * ControlPoints - noting the control points for a circle are: topleft, bottomright, bottomleft, topright, center
     * @return array of four control points.
     */
    public ArrayList<Point2D> controlPoints() {
        ArrayList<Point2D> controlpoints = coordinates;
        return controlpoints;
    }

    @Override
    public Map<String, Double> getMeasurement() {
        return null;
    }

    /**
     * Process moving of a control point.
     * @param control index to control point
     * @param pos new position.
     */
    public void moveControlPoint(int control, Point2D pos) {
       coordinates.set(control,pos);
    }


    public void storeElement(StoreFacade sf) {
        sf.start("DrawElements.PolygonElement");
        for(Point2D p : coordinates) {
            sf.addPoint(p.toString(), p);

        }
    }


    public void addLabelText(String text) {

    }

    @Override
    public String getLabelText() {
        return null;
    }

    public static DrawElement loadElement(LoadFacade lf) {
        ArrayList<Point2D> PolygonVertices = new ArrayList<>();
        return new PolygonElement(PolygonVertices);
    }
}





