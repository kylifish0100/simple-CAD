import DrawElements.DrawElement;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class EditTool {
    DrawElement de;
    MyCAD GUIObj;
    ArrayList<Point2D> pts;
    Graphics2D pen;

    EditTool(Graphics2D g2d) {
        this.pen = g2d;
        //this.de = element;
        this.pts = new ArrayList<Point2D>();
    }

    public void MarkPointsForFill(MyCAD drawGUI) {
        for (DrawElement de : drawGUI.drawing) {
            for (Point2D p : de.controlPoints()) {
                pen.setColor(Color.BLACK);
                pen.draw(new Ellipse2D.Double(p.getX() - 2.0, p.getY() - 2.0, 4.0, 4.0));
            }
        }
    }

    public void MarkPointsForEdit(ArrayList<Point2D> pts) {
        for (Point2D p : pts) {
            pen.setColor(Color.BLACK);
            pen.draw(new Ellipse2D.Double(p.getX() - 2.0, p.getY() - 2.0, 4.0, 4.0));
            pen.setColor(Color.BLUE);
            pen.fill(new Ellipse2D.Double(p.getX() - 2.0, p.getY() - 2.0, 4.0, 4.0));
        }
    }

    public void MarkPtsWhileDraw(ArrayList<Point2D> pts) {
        for (Point2D p : pts) {
            pen.setColor(Color.RED);
            pen.draw(new Ellipse2D.Double(p.getX() - 3.0, p.getY() - 3.0, 6.0, 6.0));
            pen.fill(new Ellipse2D.Double(p.getX() - 3.0, p.getY() - 3.0, 6.0, 6.0));
        }
    }

    public void GetAllPoints(MyCAD drawGUI) {
        for (DrawElement de : drawGUI.drawing) {
            MarkPointsForEdit(de.controlPoints());
        }
    }
    public void GetSomePoints(DrawElement de) {
        for (Point2D p : de.controlPoints()){
            this.pts.add(p);
        }
    }

    public void ColorSelectedControlPts(ArrayList<DrawElement> eleList){
        pen.setColor(Color.RED);
        if(eleList == null) return;
        for(DrawElement e : eleList) {
            for (Point2D p : e.controlPoints()) {
                pen.fill(new Ellipse2D.Double(p.getX() - 2.0, p.getY() - 2.0, 4.0, 4.0));
            }
        }
    }

    public void AddLabelText(String text, LabelElement label){
        label.getLabelText(text);
    }
}
