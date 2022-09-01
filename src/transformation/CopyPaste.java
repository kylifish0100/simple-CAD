package transformation;

import DrawElements.DrawElement;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class CopyPaste {
    AffineTransform at = new AffineTransform();
    Point2D before;
    Point2D after;

    public CopyPaste(Point2D b){
        this.before=b;
    }

    public CopyPaste(Point2D b, Point2D a){
        this.before=b;
        this.after=a;
    }

    public Point2D GetAfterLoc(Point2D a){
        this.after = a;
        return after;
    }

    public AffineTransform Paste(){

        AffineTransform at = new AffineTransform();
        double x0 = before.getX();
        double y0 = before.getY();
        double x1 = before.getX();
        double y1 = before.getY();

        at.translate(x1-x0,y1-y0);
        return at;
    }

    public void DrawPasted(Graphics g, DrawElement de){
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform saveState = g2.getTransform();
        g2.setTransform(Paste());
        de.draw(g2);
        g2.setTransform(saveState);
    }
}
