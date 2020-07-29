import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.JComponent;

// DrawArea - this class deal with the "view" and "control" for the drawing.
// Eric McCreath 2015 GPL

public class DrawArea extends JComponent implements MouseMotionListener, MouseListener, ToolChangeObserver {
	private static final long serialVersionUID = 1L;

	MyCAD drawGUI;

	ElementControlPoint currentControl;
	DrawElementFactory drawAreaFactory;

	public DrawArea(MyCAD drawGUI, DrawElementFactory drawAreaFactory) {
		this.drawGUI = drawGUI;
		this.drawAreaFactory = drawAreaFactory;
		currentControl = null;
		this.setPreferredSize(new Dimension(700, 500));
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		drawGUI.drawtool.addChangeObserver(this);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.black);
		drawGUI.drawing.draw(g2);

		g.setColor(Color.black);
		String command = (String) drawGUI.drawtool.getSelectCommand();
		
		// draw the control points if in edit mode
		if (command.equals(MyCAD.EDITTOOL)) {
			for (DrawElement de : drawGUI.drawing) {
				ArrayList<Point2D> pts = de.controlPoints();
				for (Point2D p : pts) {
					g2.draw(new Ellipse2D.Double(p.getX() - 2.0, p.getY() - 2.0, 4.0, 4.0));
				}
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		if (currentControl != null) {
			currentControl.element.moveControlPoint(currentControl.control, me.getPoint());
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent me) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
		String command = (String) drawGUI.drawtool.getSelectCommand();

		if (command.equals(MyCAD.EDITTOOL)) {
			currentControl = drawGUI.drawing.findControl(me.getPoint());
		} else {
			DrawElement drawelement = drawAreaFactory.createElementFromMousePress(command,
					(Color) drawGUI.colortool.getSelectCommand(), me.getPoint());
			drawGUI.drawing.add(drawelement);
			currentControl = new ElementControlPoint(drawelement, 1);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		currentControl = null;
	}

	@Override
	public void update() {
		repaint();
	}
}
