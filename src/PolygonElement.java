import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class PolygonElement extends DrawElement{
    Integer verticesCount;
    Polygon p;
    Point2D vertexA, vertexB, vertexC;
    ArrayList<Point2D> coordinates;

    /**
     * Construct a Polygons with up to 50 sides.
     * @param vertexA, vertexB and vertexC represents three vertices of a Polygon.
     */
    public PolygonElement(ArrayList<Point2D> coordinates) {
        this.verticesCount = coordinates.size();
        this.coordinates = coordinates;
        this.p = new Polygon();
        for (Point2D pt : coordinates){
            p.addPoint((int)pt.getX(),(int)pt.getY());
        }
        Integer index_a = verticesCount*1/3;
        Integer index_b = verticesCount*2/3;
        this.vertexA = coordinates.get(index_a);
        this.vertexB = coordinates.get(index_b);
        this.vertexC = coordinates.get(verticesCount-1);
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
        ArrayList<Point2D> controlpoints = new ArrayList<Point2D>();
        controlpoints.add(vertexA);
        controlpoints.add(vertexB);
        controlpoints.add(vertexC);
        controlpoints.add(new Point2D.Double((vertexA.getX() + vertexB.getX()+vertexC.getX())/3.0,
                (vertexA.getY() + vertexB.getY()+vertexC.getY())/3.0));
        return controlpoints;
    }

    /**
     * Process moving of a control point.
     * @param control index to control point
     * @param pos new position.
     */
    public void moveControlPoint(int control, Point2D pos) {
        if (control == 0)  // top vertex
            vertexA = pos;
        else if (control == 1) // bottom left vertex
            vertexB = pos;
        else if (control == 2) { // bottom right vertex
            vertexC = pos;
        }
    }


    public void storeElement(StoreFacade sf) {
        sf.start("PolygonElement");
        sf.addPoint("PointA", vertexA);
        sf.addPoint("PointB", vertexB);
        sf.addPoint("PointC", vertexC);
    }


    public void addLabelText(String text) {

    }

    public static DrawElement loadElement(LoadFacade lf) {
        ArrayList<Point2D> triangleVertices = new ArrayList<>();
        triangleVertices.add(lf.getPoint("top"));
        triangleVertices.add(lf.getPoint("bottomleft"));
        triangleVertices.add(lf.getPoint("bottomright"));
        return new TriElement(triangleVertices);
    }
}





