import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;

// Draw GUI - simple drawing program
// Eric McCreath 2015 GPL

public class MyCAD implements Runnable, ActionListener {

	public static final String EDITTOOL = "EDITTOOL";

	private static final String EXITCOMMAND = "exitcommand";
	private static final String CLEARCOMMAND = "clearcommand";
	private static final String SAVECOMMAND = "savecommand";
	private static final String OPENCOMMAND = "opencommand";
	JFrame jframe;
	DrawArea drawarea;
	DrawElementFactory drawAreaFactory;
	JSlider scaleSlider;
	Drawing drawing;
	ToolBar drawtool, colortool;

	JFileChooser filechooser = new JFileChooser();

	public MyCAD() {
		SwingUtilities.invokeLater(this);
	}

	public static void main(String[] args) {
		new MyCAD();
	}

	public void run() {
		jframe = new JFrame("MyCAD");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		drawAreaFactory = new BasicDrawElementFactory();

		drawing = new Drawing(drawAreaFactory);

		// set up the menu bar
		JMenuBar bar = new JMenuBar();
		JMenu menu = new JMenu("File");
		bar.add(menu);
		makeMenuItem(menu, "New", CLEARCOMMAND);
		makeMenuItem(menu, "Open", OPENCOMMAND);
		makeMenuItem(menu, "Save", SAVECOMMAND);
		makeMenuItem(menu, "Exit", EXITCOMMAND);

		jframe.setJMenuBar(bar);

		// set up the tool bar at the top of the window that enable actions like clear
		JPanel actionarea = new JPanel();
		actionarea.setLayout(new BoxLayout(actionarea, BoxLayout.X_AXIS));

		// set up the tool bar at the right the enable different drawing functions
		// (edit, line, box, text,...)

		drawtool = new ToolBar(BoxLayout.Y_AXIS);
		drawtool.addbutton("Edit", EDITTOOL);
		drawAreaFactory.addbuttons(drawtool);

		// set up the draw area - this is the JComponent that enable the drawing/viewing
		// of the drawing
		drawarea = new DrawArea(this, drawAreaFactory);

		drawtool.addChangeObserver(new ToolChangeObserver() {
			@Override
			public void update() {
				if (((String) drawtool.getSelectCommand()).equals("EDITTOOL")) {
					jframe.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				} else {
					jframe.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				}
			}
		});
		scaleSlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 1);

		colortool = new ToolBar(BoxLayout.Y_AXIS);
		colortool.addbutton("BLACK", Color.black);
		colortool.addbutton("RED", Color.red);
		colortool.addbutton("BLUE", Color.blue);

		JButton jbutton = new JButton("Clear");
		jbutton.setActionCommand(CLEARCOMMAND);
		jbutton.addActionListener(this);
		actionarea.add(jbutton);
		JScrollPane drawpane = new JScrollPane(drawarea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		drawpane.setPreferredSize(new Dimension(200, 200));
		jframe.getContentPane().add(actionarea, BorderLayout.PAGE_START);
		jframe.getContentPane().add(drawpane, BorderLayout.CENTER);
		jframe.getContentPane().add(drawtool, BorderLayout.LINE_END);
		jframe.getContentPane().add(colortool, BorderLayout.LINE_START);
		jframe.getContentPane().add(scaleSlider, BorderLayout.PAGE_END);
		jframe.setMinimumSize(new Dimension(100, 100));
		jframe.setVisible(true);
		jframe.pack();
	}

	private void makeMenuItem(JMenu menu, String name, String command) {
		JMenuItem menuitem = new JMenuItem(name);
		menu.add(menuitem);
		menuitem.addActionListener(this);
		menuitem.setActionCommand(command);
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getActionCommand().equals(CLEARCOMMAND)) {
			System.out.println("clear");
			drawing.clearDrawing();
			drawarea.repaint();
		} else if (ae.getActionCommand().equals(EXITCOMMAND)) {
			System.exit(0);
		} else if (ae.getActionCommand().equals(SAVECOMMAND)) {
			if (filechooser.showOpenDialog(jframe) == JFileChooser.APPROVE_OPTION) {
				drawing.save(filechooser.getSelectedFile());
			}
		} else if (ae.getActionCommand().equals(OPENCOMMAND)) {
			if (filechooser.showOpenDialog(jframe) == JFileChooser.APPROVE_OPTION) {
				drawing = Drawing.load(filechooser.getSelectedFile(), drawAreaFactory);
				drawarea.repaint();
			}
		}
	}

}