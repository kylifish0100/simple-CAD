import java.awt.*;
import java.awt.geom.AffineTransform;

public class ZoomTransform {
    double width;
    double height;
    double zoom;

    public ZoomTransform(Graphics g,double width,double height,double zoom){
        Graphics2D g2d = (Graphics2D) g;
        this.width = width;
        this.height = height;
        this.zoom = zoom;

        double zoomWidth = width*zoom;
        double zoomHeight = height*zoom;

        double anchorx = (width-zoomWidth) /2;
        double anchory = (height-zoomHeight) / 2;

        AffineTransform at = new AffineTransform();
        at.translate(anchorx,anchory);
        at.scale(zoom,zoom);
        at.translate(-100,-100);

        g2d.setTransform(at);
    }
}
