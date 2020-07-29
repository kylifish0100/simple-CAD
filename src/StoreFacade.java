import java.awt.geom.Point2D;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
/**
 * StoreFacade - This provides a facade into the standard XML API for saving drawings.
 * 
 * @author Eric McCreath
 *
 */
public class StoreFacade {
	private DocumentBuilderFactory dbf;
	private DocumentBuilder db;
	private Document doc;
	private Element drawinge;
	private Element currentelement;
	private File file;

	public StoreFacade(File file, String name) {
		try {
			dbf = DocumentBuilderFactory.newInstance();
			db = dbf.newDocumentBuilder();
			doc = db.newDocument();
			drawinge = doc.createElement(name);
			currentelement = null;
			this.file = file;
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}

	public void start(String name) { // create and start a new element
		
		if (currentelement != null) end();
		currentelement = doc.createElement(name);
		
		
	}
	
	private void end() { // end the element at add it to the drawing element
		drawinge.appendChild(currentelement);
		currentelement = null;
	}
	

	public void addPoint( String namea, Point2D a) {
		assert(currentelement != null);
		currentelement.appendChild(xmlpoint(namea, a, doc));
	}
	
	
	

	public void close() {
		if (currentelement != null) end();
		try {
			doc.appendChild(drawinge);
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(file);
			transformer.transform(source, result);
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Problem saving " + file + " " + e);
		}
	}
	

	static private Node xmlpoint(String name, Point2D p, Document doc) {
		Element e = doc.createElement(name);
		Element ex = doc.createElement("x");
		ex.setTextContent(Double.toString(p.getX()));
		Element ey = doc.createElement("y");
		ey.setTextContent(Double.toString(p.getY()));
		e.appendChild(ex);
		e.appendChild(ey);
		return e;
	}

	public void addInteger(String name, int value) {
		assert(currentelement != null);
		currentelement.appendChild(xmlinteger(name, value, doc));
	}

	private Node xmlinteger(String name, int value, Document doc2) {
		Element e = doc.createElement(name);
		e.setTextContent(Integer.toString(value));
		return e;
	}
	
	private Node xmldouble(String name, double value, Document doc2) {
		Element e = doc.createElement(name);
		e.setTextContent(Double.toString(value));
		return e;
	}

	public void addDouble(String name, double value) {
		
		assert(currentelement != null);
		currentelement.appendChild(xmldouble(name, value, doc));
	}

}
