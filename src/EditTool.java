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

    public void MarkPoints(ArrayList<Point2D> pts) {
        for (Point2D p : pts) {
            pen.draw(new Ellipse2D.Double(p.getX() - 2.0, p.getY() - 2.0, 4.0, 4.0));
        }
    }

    public void GetAllPoints(MyCAD drawGUI) {
        for (DrawElement de : drawGUI.drawing) {
            System.out.println(de.getClass());
            MarkPoints(de.controlPoints());
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
