import DrawElements.DrawElement;
import transformation.CopyPaste;
import transformation.ZoomTransform;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import javax.swing.*;

/**
 * DrawArea - this class deals with the "view" and "control" for the drawing.
 * Eric McCreath 2015 GPL
 * Edited by Matthew Aitchison
 */

public class DrawArea extends JComponent implements MouseMotionListener, MouseListener, MouseWheelListener, ToolChangeObserver {

	private static final long serialVersionUID = 1L;
	ArrayList<Point2D> coordinates = new ArrayList<>();
	MyCAD drawGUI;
	ElementControlPoint currentControl;
	DrawElementFactory drawAreaFactory;
	ArrayList<DrawElement> sElementList = new ArrayList<>();
	ArrayList<DrawElement> labelList = new ArrayList<>();
	ArrayList<Point2D> PtsInDrawing = new ArrayList<>();
	Point2D zoomOrigin;
	private double zoom = 2.0;
	private CopyPaste cp = null;
	private DrawElement copyElement = null;
	String labelText = new String();


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
		this.addMouseWheelListener(this);
		drawGUI.drawtool.addChangeObserver(this);
	}

	/**
	 * Paint our canvas area.
	 * @param g Graphics2D context.
	 */
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		String command = (String) drawGUI.drawtool.getSelectCommand();

		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		AffineTransform saveTr = g2.getTransform();

		labelText = drawGUI.getTextStored();
		drawGUI.getInputText(null);


		if(zoomOrigin!=null&&command.equals("Zoom")){
			ZoomTransform zoom = new ZoomTransform(g,this.zoom,zoomOrigin);
		}

		g.setColor(Color.black);
		drawGUI.drawing.draw(g2);
		drawGUI.elementToFill.fill(g2);

		for(DrawElement de: labelList) {
			if(de == null)
				break;
			if (de.getLabelText() != null) {
				Font labelFont = new Font("Lucida", Font.PLAIN, 10);
				FontMetrics metrics = g.getFontMetrics(labelFont);
				g2.setColor(Color.BLACK);
				g2.drawString(de.getLabelText(), (int) de.controlPoints().get(0).getX(),
						(int) de.controlPoints().get(0).getY() + metrics.getHeight());
			}
		}

		if(cp!=null && copyElement!=null){
			cp.DrawPasted(g,copyElement);
		}

		if (command.equals("CURVETOOL")||command.equals("TRITOOL")||command.equals("POLYGONTOOL")){
			EditTool editTool = new EditTool(g2);
			editTool.MarkPtsWhileDraw(coordinates);
		}

		//draw the control points if in edit mode
		if (command.equals(BasicDrawElementFactory.FILLTOOL)) {
			EditTool editTool = new EditTool(g2);
			editTool.MarkPointsForFill(drawGUI);
		}
		if (command.equals(MyCAD.EDITTOOL)) {
			EditTool editTool = new EditTool(g2);
			editTool.GetAllPoints(drawGUI);
			if(sElementList != null){
				editTool.ColorSelectedControlPts(sElementList);
			}
			if(labelList == null){
				labelList = new ArrayList<>();
			}
			for(DrawElement de : labelList){
				if(de.getLabelText()==null) {
					System.out.println("Text to add " + labelText);
					de.addLabelText(labelText);
				}

			}
		}
	}
	public void ClearAll(){
		sElementList = new ArrayList<>();
		labelList = new ArrayList<>();
		zoom = 2.0;
	}
	@Override
	public void mouseDragged(MouseEvent me) {
		String command = (String) drawGUI.drawtool.getSelectCommand();
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
		String command = (String) drawGUI.drawtool.getSelectCommand();
		if(command.equals("Zoom")){
			zoomOrigin = (Point2D) me.getPoint();
		}

		if(command.equals(BasicDrawElementFactory.FILLTOOL)){
			if(SwingUtilities.isRightMouseButton(me)){
				DrawElement selectedElement = drawGUI.drawing.findElement(me.getPoint());
				PopUpMenu menu = new PopUpMenu(e -> {
					if (e.getActionCommand().equals("fillcolor")  && selectedElement!=null ) {
						//System.out.println("filling");
						DrawElement coloredEle = drawAreaFactory.fillElementWithColor(drawGUI.selectedColor,selectedElement);
						drawGUI.elementToFill.add(coloredEle);
						repaint();
					}
				});
				menu.show(drawGUI.jframe, me.getX(), me.getY());
			}
		}


		if (command.equals(MyCAD.EDITTOOL)){
			if(SwingUtilities.isLeftMouseButton(me)) {
				DrawElement selectedElement = drawGUI.drawing.findElement(me.getPoint());
				drawGUI.m_display = selectedElement;
				if (sElementList == null) {
					sElementList = new ArrayList<>();
				}
				if (labelList == null) {
					labelList = new ArrayList<>();
				}
				sElementList.add(selectedElement);
				labelList.add(selectedElement);
			} else if (SwingUtilities.isRightMouseButton(me)) {
				DrawElement selectedElement = drawGUI.drawing.findElement(me.getPoint());
				PopUpMenu menu = new PopUpMenu(e -> {
					if (e.getActionCommand().equals("cancel")) {
						System.out.println("cancel");
						if(sElementList.contains(selectedElement)) {
							sElementList.remove(selectedElement);
						}
						repaint();
					}
					if (e.getActionCommand().equals("delete")){
						if(sElementList.contains(selectedElement)) {
							sElementList.remove(selectedElement);
						}
						drawGUI.drawing.remove(selectedElement);
						repaint();
					}

				});
				menu.show(drawGUI.jframe, me.getX(), me.getY());

			}

		}

		if(command.equals(BasicDrawElementFactory.POLYGONTOOL)){
			if(SwingUtilities.isLeftMouseButton(me)) {
				coordinates.add(me.getPoint());
				//System.out.println("Left click add a point");
			}
			if(SwingUtilities.isRightMouseButton(me)){
				PopUpMenu menu = new PopUpMenu(e -> {
					if (e.getActionCommand().equals("OK")) {
						//System.out.println("OK");
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
			if(coordinates.size()==3){
				DrawElement triangle = drawAreaFactory.createElementFromMouseClicked(command,
						drawGUI.selectedColor, coordinates);
				drawGUI.drawing.add(triangle);
				coordinates = new ArrayList<>();
			}

		}

		if(command.equals(BasicDrawElementFactory.CURVETOOL)){
			coordinates.add(me.getPoint());
			if(coordinates.size()==4){
				DrawElement curve = drawAreaFactory.createElementFromMouseClicked(command,
						drawGUI.selectedColor, coordinates);
				drawGUI.drawing.add(curve);
				coordinates = new ArrayList<>();
			}

		}

		if (command.equals(BasicDrawElementFactory.LABELTOOL)){
			DrawElement labelbox = drawAreaFactory.createEmptyLabel(drawGUI.selectedColor, me.getPoint());
			drawGUI.drawing.add(labelbox);
		}

		/**
		 * bugged Copy Paste Functiom :(
		 */

//		if (SwingUtilities.isRightMouseButton(me)) {
//			PopUpMenu menu = new PopUpMenu(e -> {
//				System.out.println(e.getActionCommand());
//				if (e.getActionCommand().equals("copy") && copyElement == null) {
//					copyElement = drawGUI.drawing.findElement(me.getPoint());
//					System.out.println("New copy:" + copyElement);
//					cp = new CopyPaste(me.getPoint());
//				}
//				if (copyElement != null)
//					System.out.println("copy:" + copyElement);
//			});
//			menu.show(drawGUI.jframe, me.getX(), me.getY());
//		}
//
//		if (SwingUtilities.isRightMouseButton(me)) {
//			PopUpMenu menu = new PopUpMenu(e -> {
//				if (e.getActionCommand().equals("paste") && copyElement != null) {
//					System.out.println("paste:" + copyElement);
//					cp.GetAfterLoc(me.getPoint());
//				}
//			});
//			menu.show(drawGUI.jframe, me.getX(), me.getY());
//		}

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
		if(SwingUtilities.isLeftMouseButton(me)) {
			if (command.equals(MyCAD.EDITTOOL)) {
				currentControl = drawGUI.drawing.findControl(me.getPoint());
			} else if (command.equals(BasicDrawElementFactory.BOXTOOL) || command.equals(BasicDrawElementFactory.CIRCLETOOL)
					|| command.equals(BasicDrawElementFactory.LINETOOL)) {
				DrawElement drawelement = drawAreaFactory.createElementFromMousePress(command,
						drawGUI.selectedColor, me.getPoint());
				drawGUI.drawing.add(drawelement);
				currentControl = new ElementControlPoint(drawelement, 1);
			}
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

	/** Implement zoom function
	 *  Adapted from https://stackoverflow.com/questions/30792089/java-graphics2d-translate-and-scale
	 * @param e the event to be processed
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if(e.getPreciseWheelRotation()<0){
			zoom -= 0.05;
		} else {
			zoom += 0.05;
		}
		if(zoom<0.01){
			zoom = 0.01;
		}
		repaint();
	}


}