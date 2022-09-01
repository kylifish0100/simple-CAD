import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;

/**
 * DrawArea - this class deals with the "view" and "control" for the drawing.
 * Eric McCreath 2015 GPL
 * Edited by Matthew Aitchison
 */

public class DrawArea extends JComponent implements MouseMotionListener, MouseListener, ToolChangeObserver {

	private static final long serialVersionUID = 1L;
	ArrayList<Point2D> coordinates = new ArrayList<>();
	MyCAD drawGUI;
	//EditTool editTool;

	ElementControlPoint currentControl;
	DrawElementFactory drawAreaFactory;
	ArrayList<DrawElement> sElementList = new ArrayList<>();
	ArrayList<DrawElement> tBFElement = new ArrayList<>();


	/**
	 * Construct a DrawArea Object.
	 * @param drawGUI gui to draw to.
	 * @param drawCanvasFactory Factory for constructing a canvas.
	 */
	public DrawArea(MyCAD drawGUI, DrawElementFactory drawCanvasFactory) {
		this.drawGUI = drawGUI;
		this.drawAreaFactory = drawCanvasFactory;
		currentControl = null;
		this.setPreferredSize(new Dimension(700, 500));
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
		drawGUI.drawtool.addChangeObserver(this);
	}

	/**
	 * Paint our canvas area.
	 * @param g Graphics2D context.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.black);
		drawGUI.drawing.draw(g2);
		drawGUI.drawing.fill(tBFElement,g2);

		String command = (String) drawGUI.drawtool.getSelectCommand();

//		editTool = new EditTool(g2);
//		System.out.println(editTool);

		//draw the control points if in edit mode
		if (command.equals(MyCAD.EDITTOOL)) {
			String labelText = new String();
			EditTool editTool = new EditTool(g2);
			editTool.GetAllPoints(drawGUI);
			if(sElementList != null){
				editTool.ColorSelectedControlPts(sElementList);
			}
			labelText = drawGUI.getTextStored();
			if(sElementList == null){
				sElementList = new ArrayList<>();
			}
			for(DrawElement de : sElementList){
				de.addLabelText(labelText);
				System.out.println(de.controlPoints().get(0).getX());
				if(labelText!=null) {
					Font labelFont = new Font("Lucida", Font.PLAIN, 10);
					FontMetrics metrics = g.getFontMetrics(labelFont);
					g2.drawString(labelText, (int) de.controlPoints().get(0).getX(),
							(int) de.controlPoints().get(0).getY()+metrics.getHeight());
				}
			}
		}
//			for (DrawElement de : drawGUI.drawing) {
//				ArrayList<Point2D> pts = de.controlPoints();
//				for (Point2D p : pts) {
//					g2.draw(new Ellipse2D.Double(p.getX() - 2.0, p.getY() - 2.0, 4.0, 4.0));
//				}
//		}
	}
	public void ClearAll(){
		sElementList = new ArrayList<>();
		tBFElement = new ArrayList<>();
		System.out.println(sElementList);
	}
	@Override
	public void mouseDragged(MouseEvent me) {
		String command = (String) drawGUI.drawtool.getSelectCommand();
		if (currentControl != null) {
			currentControl.element.moveControlPoint(currentControl.control, me.getPoint());
			System.out.println("drag point input" + currentControl.element.toString());
		}
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

	@Override
	public void mouseClicked(MouseEvent me) {
		String command = (String) drawGUI.drawtool.getSelectCommand();
		if(SwingUtilities.isRightMouseButton(me)){
			tBFElement = new ArrayList<>();
			DrawElement selectedElement = drawGUI.drawing.findElement(me.getPoint());
			System.out.println("selectedElement "+selectedElement);
			PopUpMenu menu = new PopUpMenu(e -> {
				if (e.getActionCommand().equals("fillcolor")  && selectedElement!=null ) {
					tBFElement.add(selectedElement);
					System.out.println(tBFElement);
					repaint();
				}
			});
			menu.show(drawGUI.jframe, me.getX(), me.getY());
		}
		if (command.equals(MyCAD.EDITTOOL)){
			if(SwingUtilities.isLeftMouseButton(me)) {
				DrawElement selectedElement = drawGUI.drawing.findElement(me.getPoint());
				if (sElementList == null) {
					sElementList = new ArrayList<>();
				}
				sElementList.add(selectedElement);
			} else if (SwingUtilities.isRightMouseButton(me)) {
				PopUpMenu menu = new PopUpMenu(e -> {
					if (e.getActionCommand().equals("cancel")) {
						System.out.println("cancel");
						sElementList = new ArrayList<>();
						repaint();
					}
				});
				menu.show(drawGUI.jframe, me.getX(), me.getY());

			}

		}

		if(command.equals(BasicDrawElementFactory.POLYGONTOOL)){
			if(SwingUtilities.isLeftMouseButton(me)) {
				coordinates.add(me.getPoint());
				System.out.println("Left click add a point");
			}
			System.out.println(coordinates);
			if(SwingUtilities.isRightMouseButton(me)){
				PopUpMenu menu = new PopUpMenu(e -> {
					if (e.getActionCommand().equals("OK")) {
						System.out.println("OK");
						DrawElement polygon = drawAreaFactory.createElementFromMouseClicked(command,
								drawGUI.selectedColor, coordinates);
						drawGUI.drawing.add(polygon);
						coordinates = new ArrayList<>();
						repaint();
					}
				});
				menu.show(drawGUI.jframe,me.getX(),me.getY());
			}
		}


		if(command.equals(BasicDrawElementFactory.TRITOOL)){

			coordinates.add(me.getPoint());
			System.out.println(coordinates);
			if(coordinates.size()==3){
				System.out.println("adding triangle");
				DrawElement triangle = drawAreaFactory.createElementFromMouseClicked(command,
						drawGUI.selectedColor, coordinates);
				drawGUI.drawing.add(triangle);
				coordinates = new ArrayList<>();
			}

		}

		if (command.equals(BasicDrawElementFactory.LABELTOOL)){
			DrawElement labelbox = drawAreaFactory.createEmptyLabel(drawGUI.selectedColor, me.getPoint());
			drawGUI.drawing.add(labelbox);
		}

		repaint();
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
		System.out.println("press true");
		if (command.equals(MyCAD.EDITTOOL)) {
			currentControl = drawGUI.drawing.findControl(me.getPoint());
		} else if (command.equals(BasicDrawElementFactory.BOXTOOL)||command.equals(BasicDrawElementFactory.CIRCLETOOL)
				||command.equals(BasicDrawElementFactory.LINETOOL)) {
			DrawElement drawelement = drawAreaFactory.createElementFromMousePress(command,
					drawGUI.selectedColor, me.getPoint());
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
