import java.awt.Color;
import java.awt.Graphics2D;
/**
 * ColorDrawElement  - gives us a way of adding color to elements
 * @author Eric McCreath
 *
 */

public class ColorDrawElement extends DrawElementDecorator {

	Color color;	
	public ColorDrawElement(DrawElement de, Color color) {
		drawElement = de;
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
	public void storeelement(StoreFacade sf) {
		drawElement.storeelement(sf);
		sf.addInteger("color",color.getRGB());
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
