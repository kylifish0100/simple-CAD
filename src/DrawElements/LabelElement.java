package DrawElements;

import Facade.PUtil;
import Facade.LoadFacade;
import Facade.StoreFacade;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;

public class LabelElement extends DrawElement {

    String text;
    Point2D topLeft, bottomRight;

    public LabelElement(Point2D topLeft, Point2D bottomRight){
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }
    public LabelElement(String text){
        this.text = text;
    }


    @Override
    public void draw(Graphics2D g) {
        g.draw(new Line2D.Double(topLeft, new Point2D.Double(bottomRight.getX(), topLeft.getY())));
        g.draw(new Line2D.Double(new Point2D.Double(bottomRight.getX(), topLeft.getY()), bottomRight));
        g.draw(new Line2D.Double(bottomRight, new Point2D.Double(topLeft.getX(), bottomRight.getY())));
        g.draw(new Line2D.Double(new Point2D.Double(topLeft.getX(), bottomRight.getY()), topLeft));
    }

    @Override
    public void Fill(Graphics2D g) {

    }

    @Override
    public ArrayList<Point2D> controlPoints() {
        ArrayList<Point2D> controlpoints = new ArrayList<Point2D>();
        controlpoints.add(topLeft);
        controlpoints.add(bottomRight);
        controlpoints.add(new Point2D.Double(topLeft.getX(), bottomRight.getY()));
        controlpoints.add(new Point2D.Double(bottomRight.getX(), topLeft.getY()));
        controlpoints.add(PUtil.mid(topLeft, bottomRight));
        return controlpoints;
    }

    @Override
    public Map<String, Double> getMeasurement() {
        return null;
    }

    public void moveControlPoint(int control, Point2D pos) {
        if (control == 0)  // topleft
            topLeft = pos;
        else if (control == 1) // bottomright
            bottomRight = pos;
        else if (control == 2) { // bottomleft
            bottomRight = new Point2D.Double(bottomRight.getX(), pos.getY());
            topLeft = new Point2D.Double(pos.getX(), topLeft.getY());
        } else if (control == 3) { // topright
            bottomRight = new Point2D.Double(pos.getX(), bottomRight.getY());
            topLeft = new Point2D.Double(topLeft.getX(), pos.getY());
        } else if (control == 4) { // center
            Point2D vec = PUtil.sub(pos, PUtil.mid(topLeft, bottomRight));
            topLeft = PUtil.add(topLeft, vec);
            bottomRight = PUtil.add(bottomRight, vec);
        }
    }

    @Override
    public void storeElement(StoreFacade sf) {
        sf.start("DrawElements.LabelElement");
        sf.addPoint("topleft", topLeft);
        sf.addPoint("bottomright", bottomRight);
        //sf.addText(text);
    }

    @Override
    public void addLabelText(String text) {
        this.text = text;
        System.out.println("Text Stored in Label: "+text);
    }

    public String getLabelText() {
        return this.text;
    }

    public static DrawElement loadElement(LoadFacade lf) {
        return new LabelElement(lf.getPoint("topleft"), lf.getPoint("bottomright"));
    }

}
