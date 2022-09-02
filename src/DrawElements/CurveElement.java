package DrawElements;

import Facade.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;

public class CurveElement extends DrawElement {

    ArrayList<Point2D> coordinates;
    Integer verticesCount;
    private Map<String, Double> measurement;
    public CurveElement(ArrayList<Point2D> c){
        this.coordinates = c;
        this.verticesCount = c.size();
    }
    @Override
    public void draw(Graphics2D g) {
        double x0 = coordinates.get(0).getX();
        double y0 = coordinates.get(0).getY();
        double x1 = coordinates.get(1).getX();
        double y1 = coordinates.get(1).getY();
        double x2 = coordinates.get(2).getX();
        double y2 = coordinates.get(2).getY();
        double x3 = coordinates.get(3).getX();
        double y3 = coordinates.get(3).getY();

        for (double u = 0.0; u < 1.0; u += 0.001) {
            double x = Math.pow(1-u,3)*x0 + 3*(1-u)*(1-u)*u*x1 +3*(1-u)*u*u*x2 + Math.pow(u,3)*x3;
            double y = Math.pow(1-u,3)*y0 + 3*(1-u)*(1-u)*u*y1 +3*(1-u)*u*u*y2 + Math.pow(u,3)*y3;
            g.fillRect((int) x, (int) y, 1, 1);
        }


    }


    @Override
    public void Fill(Graphics2D g) {

    }

    @Override
    public ArrayList<Point2D> controlPoints() {
        ArrayList<Point2D> controlpoints = coordinates;
        return controlpoints;
    }

    @Override
    public Map<String, Double> getMeasurement() {
        return null;
    }

    @Override
    public void moveControlPoint(int control, Point2D pos) {
        coordinates.set(control,pos);
    }

    @Override
    public void storeElement(StoreFacade sf) {

    }

    @Override
    public void addLabelText(String text) {

    }

    @Override
    public String getLabelText() {
        return null;
    }
}
