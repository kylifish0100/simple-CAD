package Facade;

import java.awt.geom.Point2D;
import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Facade.LoadFacade - This provides a facade into the standard XML API for loading
 * drawings.
 * 
 * @author Eric McCreath
 *
 */

public class LoadFacade {
	NodeList nl;
	int nodepos;
	Node currentelement;

	public static LoadFacade load(File file) {
		DocumentBuilderFactory dbf;
		DocumentBuilder db;
		LoadFacade res = new LoadFacade();
		try {
			// load the xml tree
			dbf = DocumentBuilderFactory.newInstance();

			db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);

			// parse the tree and obtain the person info
			Node drawinge = doc.getFirstChild();

			res.nl = drawinge.getChildNodes();

			res.nodepos = 0;
			return res;
		} catch (Exception e) {
			System.err.println("Problem loading " + file);
		}
		return null;
	}

	public String nextElement() {
		if (nodepos == nl.getLength())
			return null;
		currentelement = nl.item(nodepos);
		nodepos++;
		return currentelement.getNodeName();
	}

	public Point2D getPoint(String name) {
		NodeList list = currentelement.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals(name)) {
				return extractpoint(list.item(i));
			}
		}
		return null;
	}

	public Integer getInteger(String name) {
		NodeList list = currentelement.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals(name)) {
				return extractinteger(list.item(i));
			}
		}
		return null;
	}

	private Integer extractinteger(Node item) {
		return Integer.parseInt(item.getTextContent());
	}

	private Double extractdouble(Node item) {
		return Double.parseDouble(item.getTextContent());
	}

	public static Point2D extractpoint(Node p) {
		NodeList nl = p.getChildNodes();
		return new Point2D.Double(Double.parseDouble(nl.item(0).getTextContent()), Double.parseDouble(nl.item(1).getTextContent()));
	}

	public Double getDouble(String name) {
		NodeList list = currentelement.getChildNodes();
		for (int i = 0; i < list.getLength(); i++) {
			if (list.item(i).getNodeName().equals(name)) {
				return extractdouble(list.item(i));
			}
		}
		return null;
	}
}
