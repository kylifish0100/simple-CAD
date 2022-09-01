package transformation;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

public class ZoomTransform {
    double zoom;
    Point2D origin;

    public ZoomTransform(Graphics g,double z, Point2D o){
        Graphics2D g2d = (Graphics2D) g;
        this.zoom = z;
        this.origin = o;

        AffineTransform at = new AffineTransform();

        at.translate((2-zoom)*origin.getX(),(2-zoom)*origin.getY());
        at.scale(zoom,zoom);



        g2d.setTransform(at);
    }
}
