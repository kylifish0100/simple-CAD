import java.awt.Color;
import java.awt.Graphics2D;
/**
 * ColorDrawElement  - gives us a way of adding color to elements.
 * Works by wrapping draw call and modifying g.setColor
 *
 * @author Eric McCreath
 * edited by Matthew Aitchison
 *
 */
public class ColorDrawElement extends DrawElementDecorator {

	Color color;

	/**
	 * Construct our ColorDrawElement.
	 * @param drawElement The base object to draw with a particular color.
	 * @param color The color.
	 */
	public ColorDrawElement(DrawElement drawElement, Color color) {
		this.drawElement = drawElement;
		this.color = color;
	}
	
	@Override
	public void draw(Graphics2D g) {
		Color oldcolor = g.getColor();
		g.setColor(color);
		drawElement.draw(g);
		g.setColor(oldcolor);
	}

	@Override
	public void Fill(Graphics2D g) {
		Color oldcolor = g.getColor();
		g.setColor(color);
		drawElement.Fill(g);
		g.setColor(oldcolor);
	}

	@Override
	public void storeElement(StoreFacade sf) {
		drawElement.storeElement(sf);
		sf.addInteger("color",color.getRGB());
	}

	@Override
	public void addLabelText(String text) {

	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ColorDrawElement) {
			ColorDrawElement ce = (ColorDrawElement) obj;
			return color.equals(ce.color) && drawElement.equals(ce.drawElement);
		}
		return false;
	}
}
