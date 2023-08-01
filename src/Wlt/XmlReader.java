package Wlt;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.StringTokenizer;
import java.awt.geom.Line2D;
import java.awt.Dimension;
import java.util.ArrayList;



public class XmlReader {
	Document doc;

public XmlReader(String filename) {
	System.out.println(filename);
	try {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
  		DocumentBuilder db = dbf.newDocumentBuilder();
		java.io.InputStream is = this.getClass().getResourceAsStream(filename);
  		doc = db.parse(is); 
  		doc.getDocumentElement().normalize();
  		
		

	} catch (Exception e) {
		e.printStackTrace();
	}

}

//zero indicates error as we cannot have zero slides
public int getNumSlides() {
	try {
		NodeList list = doc.getElementsByTagName("NumSlides");
		if (list.getLength() == 1) {
			return java.lang.Integer.parseInt(list.item(0).getTextContent());
		}else
			throw new ParserConfigurationException("Malformed XML: getNumSlides, NumSlides missing");
	}catch (Exception e) {
			e.printStackTrace();

	}
	return 0;
}

public Dimension getPanelDimensions(int sCount) {
	int i;
	Node n;
	try {
		NodeList list = doc.getElementsByTagName("Slide" + sCount);
		if (list.getLength() == 1) {
			list = ((Element) list.item(0)).getElementsByTagName("panelDimensions");
			if (list.getLength() == 1) {
				NodeList panelList = ((Element) list.item(0)).getElementsByTagName("width"); 
				int width = java.lang.Integer.parseInt(panelList.item(0).getTextContent());
				panelList = ((Element) list.item(0)).getElementsByTagName("height"); 
				int height = java.lang.Integer.parseInt(panelList.item(0).getTextContent());
				return new Dimension(width, height); 
			}else
				throw new ParserConfigurationException("Malformed XML: getImage, panelDimensions tag not found");
		}else 
			throw new ParserConfigurationException("Malformed XML: getImage, Slide tag: Slide" + sCount +" not found");

	}catch (Exception e) {
		e.printStackTrace();
		
	}

	return null;
}

public Dimension getImageDimensions(int sCount) {
	int i;
	Node n;
	try {
	
	NodeList list = doc.getElementsByTagName("Slide" + sCount);
	if (list.getLength() == 1) {
		NodeList imgList = ((Element) list.item(0)).getElementsByTagName("img"); 
		if (imgList.getLength() == 1) {
			n = (Node) imgList.item(0);
			if (n.hasAttributes()) {		
				//return ((Element) n).getAttributeNode("dimensions").getValue();
				StringTokenizer tokens = new StringTokenizer( ((Element) n).getAttributeNode("dimensions").getValue(), ",");
				int width = java.lang.Integer.parseInt(tokens.nextToken().trim());
				int height = java.lang.Integer.parseInt(tokens.nextToken().trim());
				return new Dimension(width, height);
			}else 
				throw new ParserConfigurationException("Malformed XML: getImage, Image does not have Dimension Attribute");
			}else 
				throw new ParserConfigurationException("MalformedXML: getImage, image tag not found");	
		}else
			throw new ParserConfigurationException("Malformed XML: getImage, Slide tag: Slide" + sCount +" not found");
	
	}catch (Exception e) {
		e.printStackTrace();
		
	}
	return null;
}

public String getImage(int sCount) {
	int i;
	Node n;
	try {
	
	NodeList list = doc.getElementsByTagName("Slide" + sCount);
	if (list.getLength() == 1) {
		NodeList imgList = ((Element) list.item(0)).getElementsByTagName("img"); //TODO add Entity/Text check (NODE TYPE)
		n = (Node) imgList.item(0);
		
		if (!n.hasAttributes()) {		
			throw new ParserConfigurationException("Malformed XML: getImage, Image does not have Dimension Attribute");
		}			
	
		if  ( n.getNodeType() == Node.ELEMENT_NODE ) {
			return  n.getTextContent(); //((Node) imgList.item(0)).getTextContent();
		}else 
			 throw new ParserConfigurationException("Malformed XML: getImage, unknown structure");
		
	}else
		
		throw new ParserConfigurationException("Malformed XML: getImage, Slide" + sCount +" not found");
		

	}catch (Exception e) {
		e.printStackTrace();
		
	}
	 
	return null;

}
public ArrayList<Line2D> getSide(int sCount, String whichSide) {
	int i, numLines;
	double x1, y1, x2, y2;
	ArrayList<Line2D> sideList;	
	Node n;
	try {
		
		NodeList list = doc.getElementsByTagName("Slide" + sCount);
		if (list.getLength() == 1) {
        		list = ((Element) list.item(0)).getElementsByTagName("Side" + whichSide);
			if (list.getLength() == 1) {
				//while (  i < list.getLength()&& !(list.item(i).getNodeName().equals("Side" + whichSide) )  ) {
					//System.out.println("Tag Names: " +  list.item(i).getNodeName());
					//i++;
				//}
				//System.out.println("This should NOT be a text node (Side node is an ELEMENT: " + ((Element) list.item(0)).getTagName());
				//WE SHOULD CHECK TO MAKE SURE (list.item(i).getNodeType() != TEXT_NODE)
				//if ( ( (Element) list.item(0)).getTagName().equals("Side" + whichSide) )  {
				n = list.item(0);
				if (n.hasAttributes()) {		
					numLines = java.lang.Integer.parseInt(((Element) n).getAttributeNode("numLines").getValue());
					//System.out.println("numLines Attribute: " + numLines);
					sideList = new ArrayList<Line2D>();
					for (i = 1;  i <= numLines; i++) {
						NodeList lineList = ((Element) n).getElementsByTagName("L" + i); 
						//System.out.println(lineList.item(0).getNodeName());
						StringTokenizer tokens = new StringTokenizer(lineList.item(0).getTextContent(), ",");
						if (!(tokens.countTokens() != 4) ) {
							x1 = java.lang.Double.parseDouble(tokens.nextToken().trim());
							y1 = java.lang.Double.parseDouble(tokens.nextToken().trim());
							x2 = java.lang.Double.parseDouble(tokens.nextToken().trim());
							y2 = java.lang.Double.parseDouble(tokens.nextToken().trim());
							sideList.add(new Line2D.Double(x1,y1,x2,y2));
							//System.out.println(x1 + ", " + y1 + ", " + x2 + ", " + y2);
						}else 
							throw new ParserConfigurationException("Malformed XML: getSide, unexpected number of lines");
					}
				sideList.trimToSize();
				return sideList;
				  
				}else 
				throw new ParserConfigurationException("Malformed XML, numLines Attribute error"); 
			}else
				throw new ParserConfigurationException("Malformed XML, Unknown Side: " + whichSide); 
		}else
			throw new ParserConfigurationException("Malformed XML, Side Data Missing"); 
	

	}catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}
/*
public static void main(String[] args) {
	ArrayList<Line2D> s;
	XmlReader xml;
	xml = new XmlReader("xmlFootastic_1.xml");
	s = xml.getSide(1, "Bottom");
	Line2D l = s.get(0);
	System.out.println(l.getX1() + ", " + l.getY1() + ", " + l.getX2() + ", " + l.getY2());
	l = s.get(1);
	System.out.println(l.getX1() + ", " + l.getY1() + ", " + l.getX2() + ", " + l.getY2());
	System.out.println("ArrayList Size: " + s.size());
	xml.getImage(1);
	System.out.println("Printing out Image Dimensions: " + xml.getImageDimensions(1).getWidth() + ", " + xml.getImageDimensions(1).getHeight());
	System.out.println("Printing out Panel Dimensions: " + xml.getPanelDimensions(2).getWidth() + ", " + xml.getPanelDimensions(2).getHeight());

}

*/




}

