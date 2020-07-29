import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import org.junit.Test;

/*
 * testDrawGUI - doing some very basic tests on the gui
 * Eric McCreath
 */


public class testDrawGUI implements Runnable {

	MyCAD gui;
	
	@Test
	public void drawlinetest() {
		gui = new MyCAD();	
		try {
			SwingUtilities.invokeAndWait(this);
			
			Drawing expected = new Drawing(new BasicDrawElementFactory());
			expected.add(new ColorDrawElement(new LineElement(new Point2D.Double(100,110),new Point2D.Double(200,101)), Color.BLACK));
			assertEquals(gui.drawing,expected);
			
		} catch (InvocationTargetException e) {
			 fail();
		} catch (InterruptedException e) {
			 fail();	
		}
	}
	
	@Override
	public void run() {
		gui.drawtool.getButton(1).doClick();
		gui.drawarea.mousePressed(new MouseEvent(gui.drawarea,0,0L,0,100,110,1,false));
		gui.drawarea.mouseDragged(new MouseEvent(gui.drawarea,0,0L,0,200,101,1,false));
		gui.drawarea.mouseReleased(new MouseEvent(gui.drawarea,0,0L,0,200,101,1,false));
	}
	
}
