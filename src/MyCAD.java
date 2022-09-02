import DrawElements.DrawElement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * Draw GUI - simple drawing program
 * Eric McCreath 2015 GPL
 * Edited by Matthew Aitchison
 */
public class MyCAD implements Runnable, ActionListener {

	public static final String EDITTOOL = "EDITTOOL";

	private static final String EXITCOMMAND = "exitcommand";
	private static final String CLEARCOMMAND = "clearcommand";
	private static final String SAVECOMMAND = "savecommand";
	private static final String OPENCOMMAND = "opencommand";
	private static final String INPUT = "labelinput";
	private static final String CHOOSECOLOR = "choosecolor";
	private static final String GETMEASURE = "getmeasure";

	private String textStored;
	JFrame jframe;
	DrawArea drawArea;
	DrawElementFactory drawElementFactory;
	JSlider scaleSlider;
	Drawing drawing,elementToFill;
	ToolBar drawtool;
	ColorChooser ccButton;
	Color selectedColor = Color.BLACK;
	DrawElement m_display;

	JFileChooser filechooser = new JFileChooser();

	public MyCAD() {
		SwingUtilities.invokeLater(this);
	}

	public static void main(String[] args) {
		new MyCAD();
	}

	public void run() {
		/**
		 * Run the program.
		 */
		jframe = new JFrame("MyCAD");
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		drawElementFactory = new BasicDrawElementFactory();

		drawing = new Drawing(drawElementFactory);
		elementToFill = new Drawing(drawElementFactory);

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
		drawtool.addButton("Edit", EDITTOOL);
		drawElementFactory.addButtons(drawtool);

		// set up the draw area - this is the JComponent that enable the drawing/viewing
		// of the drawing
		drawArea = new DrawArea(this, drawElementFactory);

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

//		colortool = new ToolBar(BoxLayout.Y_AXIS);
//		colortool.addButton("BLACK", Color.black);
//		colortool.addButton("RED", Color.red);
//		colortool.addButton("BLUE", Color.blue);

		ccButton = new ColorChooser(Color.BLACK, "Choose A Color");
		ccButton.setActionCommand(CHOOSECOLOR);
		ccButton.addActionListener(this);
		actionarea.add(ccButton);


		JButton jbutton = new JButton("Clear");
		jbutton.setActionCommand(CLEARCOMMAND);
		jbutton.addActionListener(this);
		actionarea.add(Box.createHorizontalGlue());
		actionarea.add(jbutton);


		JButton label_input = new JButton("Label input");
		label_input.setActionCommand(INPUT);
		label_input.addActionListener(this);
		actionarea.add(label_input);

		JButton get_measure = new JButton("Get Measurement");
		get_measure.setActionCommand(GETMEASURE);
		get_measure.addActionListener(this);
		actionarea.add(get_measure);

		JScrollPane drawpane = new JScrollPane(drawArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		drawpane.setPreferredSize(new Dimension(600, 600));
		jframe.getContentPane().add(actionarea, BorderLayout.PAGE_START);
		jframe.getContentPane().add(drawpane, BorderLayout.CENTER);
		jframe.getContentPane().add(drawtool, BorderLayout.LINE_END);
//		jframe.getContentPane().add(colortool, BorderLayout.LINE_START);
		jframe.getContentPane().add(scaleSlider, BorderLayout.PAGE_END);
		jframe.setMinimumSize(new Dimension(100, 100));
		jframe.setVisible(true);
		jframe.pack();
	}

	/**
	 * Create a new menu item.
	 */
	private void makeMenuItem(JMenu menu, String name, String command) {
		JMenuItem menuitem = new JMenuItem(name);
		menu.add(menuitem);
		menuitem.addActionListener(this);
		menuitem.setActionCommand(command);
	}

	@Override
	/**
	 * Perform an ActionEvent.
	 */
	public void actionPerformed(ActionEvent ae) {

		if (ae.getActionCommand().equals(CLEARCOMMAND)) {
			System.out.println("clear");
			drawing.clearDrawing();
			elementToFill.clearDrawing();
			drawArea.ClearAll();
			drawArea.repaint();
		} else if (ae.getActionCommand().equals(EXITCOMMAND)) {
			System.exit(0);
		} else if (ae.getActionCommand().equals(SAVECOMMAND)) {
			if (filechooser.showOpenDialog(jframe) == JFileChooser.APPROVE_OPTION) {
				drawing.save(filechooser.getSelectedFile());
			}
		} else if (ae.getActionCommand().equals(OPENCOMMAND)) {
			if (filechooser.showOpenDialog(jframe) == JFileChooser.APPROVE_OPTION) {
				drawing = Drawing.load(filechooser.getSelectedFile(), drawElementFactory);
				drawArea.repaint();
			}
		} else if (ae.getActionCommand().equals(INPUT)) {
			textStored = new String();
			InputWindow labelInput = new InputWindow(this);
			labelInput.changeVisible();
		} else if(ae.getActionCommand().equals(CHOOSECOLOR)){
			Color newColor = JColorChooser.showDialog(null, "Choose a color", ccButton.getCurrent() );
			ccButton.getSelectedColor(newColor);
			selectedColor = newColor;
		} else if(ae.getActionCommand().equals(GETMEASURE)){
			MeasurementDisplay window = new MeasurementDisplay(this);
			window.display(this.m_display);
		}

	}
	public void getInputText(String text){
		this.textStored=text;
		//System.out.println(textStored+"in MyCAD");
	}

	public String getTextStored(){
		//System.out.println("Get Stored Text"+this.textStored);
		return this.textStored;
	}

}
