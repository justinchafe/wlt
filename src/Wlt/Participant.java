//Participant class

package Wlt;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.swing.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;

public class Participant {

	final String HEADER = "Participant Num, Slide Num, Start TimeStamp, End TimeStamp, End Time, First Click TimeStamp, First Click Time, Angle, Motion Selection, Apperance Selection";
	final String FILE_NAME = "results";
	final String SEPERATOR =  System.getProperty("file.separator");
	final String USERDIR = System.getProperty("user.dir");
	final static int NUM_DATA_FIELDS = 9;
	final String EXT_CSV = ".csv";
	final String EXT_XML = ".xml";
	private String fileName;
	ArrayList<String[]> responseData;
	BufferedWriter outputStream;
	String path;
	File f;
	String subjectNum;
	double startTime;
	double endTime;
	int currentDataSet, dataField;
	Document doc;


	public Participant(String subjectNum) {
				
		this.subjectNum = subjectNum;
		path =  USERDIR + SEPERATOR + FILE_NAME + subjectNum + EXT_CSV;
		fileName = USERDIR + SEPERATOR +  FILE_NAME + "_" + subjectNum + EXT_XML;	
		createFile();
		responseData = new ArrayList<String[]>();
		responseData.add(new String[NUM_DATA_FIELDS]);
		currentDataSet = 0;

       		try {
            		/////////////////////////////
            		//Creating an empty XML Document

           		//We need a Document
            		DocumentBuilderFactory dbfac = DocumentBuilderFactory.newInstance();
            		DocumentBuilder docBuilder = dbfac.newDocumentBuilder();
            		doc = docBuilder.newDocument();

            		////////////////////////
            		//Creating the XML tree

            		//create the root element and add it to the document
            		Element root = doc.createElement("Particpant");
            		doc.appendChild(root);
	    		 

        	} catch (Exception e) {
           		System.out.println(e);
       	 	}
		
			addParticipantNum();
	}

	public void addSlideElement(String currentSlide) {
 		try {
	 		
	 		Element child = doc.createElement("Slide" + currentSlide);
			//child.setAttribute("name", "value");
	 		Element root = doc.getDocumentElement();
         		root.appendChild(child);
        	} catch (Exception e) {
            		System.out.println(e);
        	}
	}

	public void addParticipantNum() {
 		try {
	 		
	 		Element child = doc.createElement("subjectNum");
			child.appendChild(doc.createTextNode(subjectNum));
			//child.setAttribute("name", "value");
	 		Element root = doc.getDocumentElement();
         		root.appendChild(child);
        	} catch (Exception e) {
            		System.out.println(e);
        	}
	}


	public void addApperanceSelection(String apperanceSelection) {
		addApperanceSel(apperanceSelection);
 		try {
	 		
	 		Element child = doc.createElement("ApperanceSelection");
			child.appendChild(doc.createTextNode(apperanceSelection));
			Element root = doc.getDocumentElement();
         		root.appendChild(child);
        	} catch (Exception e) {
			System.out.println(e);
        	}
	}

	public void addMotionSelection(String motionSelection) {
 		addMotionSel(motionSelection);
		try {
	 		
	 		Element child = doc.createElement("MotionSelection");
			child.appendChild(doc.createTextNode(motionSelection));
			Element root = doc.getDocumentElement();
         		root.appendChild(child);
        	} catch (Exception e) {
			System.out.println(e);
        	}
	}

	public void addStartTime(int slideNum) {

  	try {
	 	
	 	Element child = doc.createElement("StartTime");
	 	child.appendChild(doc.createTextNode((responseData.get(currentDataSet))[1]));
	 	NodeList list = doc.getElementsByTagName("Slide" + java.lang.Integer.toString(slideNum));
		if (list.getLength() == 1) {
			Element ele = (Element) list.item(0);
			ele.appendChild(child);
		

		}else
			System.out.println("Slide Not Found");
	
                           

        } catch (Exception e) {
            System.out.println(e);
        }
	}


	public void addEndTime(String slideNum, String endTime) {

  	try {
	 
	 	Element child = doc.createElement("EndTime");
	 	child.appendChild(doc.createTextNode(endTime));
	 	//child.setAttribute("name", "value");
		//Node node = doc.getDocumentElement();
		NodeList list = doc.getElementsByTagName("Slide" + slideNum);
		if (list.getLength() == 1) {
			Element ele = (Element) list.item(0);
			ele.appendChild(child);
		
		}else
			System.out.println("Slide Not Found");
	
                           

        } catch (Exception e) {
            System.out.println(e);
        }
	}

	public void addTotalTime(int slideNum) {
		
		try {
		String slide = java.lang.Integer.toString(slideNum);
		double start = java.lang.Double.parseDouble((responseData.get(currentDataSet))[1]);
		double end = java.lang.Double.parseDouble((responseData.get(currentDataSet))[2]);
	 	String totalTime = java.lang.Double.toString(end - start);
	 	Element child = doc.createElement("TotalTime");
	 	child.appendChild(doc.createTextNode(totalTime));
	 	//child.setAttribute("name", "value");
		//Node node = doc.getDocumentElement();
		NodeList list = doc.getElementsByTagName("Slide" + slide);
		if (list.getLength() == 1) {
			Element ele = (Element) list.item(0);
			ele.appendChild(child);
		
		}else
			System.out.println("Slide Not Found");
	
                           

        } catch (Exception e) {
            System.out.println(e);
        }
	}


	public void addMidTime(int slideNum) {
		
		try {
		String slide = java.lang.Integer.toString(slideNum);
		double start = java.lang.Double.parseDouble((responseData.get(currentDataSet))[1]);
		double mid = java.lang.Double.parseDouble((responseData.get(currentDataSet))[4]);
		String midTime = java.lang.Double.toString(mid - start);
	 	Element child = doc.createElement("MidTime");
	 	child.appendChild(doc.createTextNode(midTime));
	 	//child.setAttribute("name", "value");
		//Node node = doc.getDocumentElement();
		NodeList list = doc.getElementsByTagName("Slide" + slide);
		if (list.getLength() == 1) {
			Element ele = (Element) list.item(0);
			ele.appendChild(child);
		
		}else
			System.out.println("Slide Not Found");
	
                           

        } catch (Exception e) {
            System.out.println(e);
        }
	}

	
	public void addFirstSide(String slideNum, String firstSideClicked) {
		try {
	 
	 	Element child = doc.createElement("FirstSideClicked");
	 	child.appendChild(doc.createTextNode(firstSideClicked));
	 	//child.setAttribute("name", "value");
		//Node node = doc.getDocumentElement();
		NodeList list = doc.getElementsByTagName("Slide" + slideNum);
		if (list.getLength() == 1) {
			Element ele = (Element) list.item(0);
			ele.appendChild(child);
		
		}else
			System.out.println("Slide Not Found");
	
                           

        } catch (Exception e) {
            System.out.println(e);
        }
	}

	
	public void addSecondSide(String slideNum, String secondSideClicked) {
		try {
	 
	 	Element child = doc.createElement("SecondSideClicked");
	 	child.appendChild(doc.createTextNode(secondSideClicked));
	 	//child.setAttribute("name", "value");
		//Node node = doc.getDocumentElement();
		NodeList list = doc.getElementsByTagName("Slide" + slideNum);
		if (list.getLength() == 1) {
			Element ele = (Element) list.item(0);
			ele.appendChild(child);
		
		}else
			System.out.println("Slide Not Found");
	
                           

        } catch (Exception e) {
            System.out.println(e);
        }
	}



	public void addAngleElement(String slideNum, String angle) {

  	try {
	 
	 	Element child = doc.createElement("Angle");
	 	child.appendChild(doc.createTextNode(angle));
	 	//child.setAttribute("name", "value");
		//Node node = doc.getDocumentElement();
		NodeList list = doc.getElementsByTagName("Slide" + slideNum);
		if (list.getLength() == 1) {
			Element ele = (Element) list.item(0);
			ele.appendChild(child);
		
		}else
			System.out.println("Slide Not Found");
	
                           

        } catch (Exception e) {
            System.out.println(e);
        }
	}


	private boolean createFile() {
		File f = new File(path);
		String s;
		JFrame frame = new JFrame("dialog");

		try {
			if (f.exists()) {
				int n = JOptionPane.showConfirmDialog(frame,"Warning! file already exists, would you like to overwrite previous data?",
    				"Overwrite",
    				JOptionPane.YES_NO_OPTION);

				int k = JOptionPane.showConfirmDialog(frame,"Directory: " + System.getProperty("user.dir"),
    				"Overwrite",
    				JOptionPane.YES_NO_OPTION);
		
				if (n == JOptionPane.YES_OPTION) {
					f.delete();
					f.createNewFile();
				}else if (n == JOptionPane.NO_OPTION) {
					System.exit(0);
				}else if (n == JOptionPane.CLOSED_OPTION) {
					System.exit(0);
				}
				
			}else {
				if (f.createNewFile()) {
					;
				}else
					; //exists, handle append or overwrite
			}

		} catch (IOException e) {
			JOptionPane.showMessageDialog(frame,
    			"File IO Error",
   			"File Error",
    			JOptionPane.WARNING_MESSAGE);

		}	

		writeHeadersCSV();
		return true;
	}


	public boolean writeHeadersCSV() {
		BufferedWriter outputStream = null;
		int i;		
		try {
			outputStream = new BufferedWriter(new FileWriter(path, true));
			outputStream.write(HEADER);
			outputStream.newLine();
			outputStream.close();
			return true;
		}catch (IOException e) {
			System.out.println("File IO Error");
			return false;
		}	
	}

	public boolean writeDataToFile() {
		
		BufferedWriter outputStream = null;
		int i;		
		try {
			outputStream = new BufferedWriter(new FileWriter(path, true));
			outputStream.write(subjectNum + ", ");


			//for (i = NUM_DATA_FIELDS-1; i > 0; i--) {
			for ( i = 0; i < NUM_DATA_FIELDS; i++) {			
				if (i+1 == NUM_DATA_FIELDS) {
					outputStream.write((responseData.get(currentDataSet))[i]);
				}else {
					outputStream.write((responseData.get(currentDataSet))[i] + ", ");
				}
			}
			//outputStream.write((responseData.get(currentDataSet))[i]);		
			outputStream.newLine(); //move to the next line
			outputStream.close();
			advanceDataSet();
			return true;			
				

		}catch (IOException e) {
			System.out.println("File IO Error");
			return false;
		}

		finally {
			if (outputStream != null ) {
				//outputStream.close();
				return true;
			}
		}
	
		
	}


	public void advanceDataSet() {
		
			currentDataSet++;
			responseData.add(new String[NUM_DATA_FIELDS]);
			
	}

	public void addSlideNum(int slideNum) {
		(responseData.get(currentDataSet))[0] = java.lang.Integer.toString(slideNum);
	}


	public void addStartTime(double startTime) {
		(responseData.get(currentDataSet))[1] = java.lang.Double.toString(startTime);	
	}

	
	public void addEndTime(double endTime) {
		(responseData.get(currentDataSet))[2] = java.lang.Double.toString(endTime);
		if ((responseData.get(currentDataSet))[1] != null) {		
			java.lang.Double start = new java.lang.Double((responseData.get(currentDataSet))[1]);
			(responseData.get(currentDataSet))[3] = java.lang.Double.toString((endTime - start.doubleValue())); 
		}else
			System.out.println("Error: Start time must be entered before end time");
	}

	
	public void addClickTime(double midTime) {
		(responseData.get(currentDataSet))[4] = java.lang.Double.toString(midTime);
		if ((responseData.get(currentDataSet))[1] != null) {
			java.lang.Double start = new java.lang.Double((responseData.get(currentDataSet))[1]);
			(responseData.get(currentDataSet))[5] = java.lang.Double.toString(midTime - start.doubleValue()); //ClickTime from Start
		}else
			System.out.println("Error: Start time must be entered before end time");
	}
	
	public void addAngle(double angle) {
		(responseData.get(currentDataSet))[6] = java.lang.Double.toString(angle);
	}

	public void addMotionSel(String motionSel) {
		(responseData.get(currentDataSet))[7] = motionSel;
	}

	public void addApperanceSel(String appSel) {
 		(responseData.get(currentDataSet))[8] = appSel;
	}


	public void writeXmlFile() {
   		try {

			TransformerFactory transfac = TransformerFactory.newInstance();
        		Transformer trans = transfac.newTransformer();
        		trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no"); //from class OutputKeys!
        		trans.setOutputProperty(OutputKeys.INDENT, "yes");

	            //create string from xml tree
	        
		 	PrintWriter out  = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
	            	StreamResult result = new StreamResult(out); //holder for transform
	            	DOMSource source = new DOMSource(doc); //create our DOMSource using our Document (Passed as NODE)
		        trans.transform(source, result); //pass our DOMSource and our holder for transform (Result interface implemented by StreamResult which is constructed from our StringWriter(Buffer))
            		out.close();

           
   		} catch (Exception e) {
        	    System.out.println(e);
        	}
	}


public void printXml() {
	try {

		TransformerFactory transfac = TransformerFactory.newInstance();
        	Transformer trans = transfac.newTransformer();
        	trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        	trans.setOutputProperty(OutputKeys.INDENT, "yes");

            	//create string from xml tree
        	StringWriter sw = new StringWriter();
	 	StreamResult result = new StreamResult(sw); //holder for transform
            	DOMSource source = new DOMSource(doc); //create our DOMSource using our Document (Passed as NODE)
            	trans.transform(source, result); //pass our DOMSource and our holder for transform (Result interface implemented by StreamResult which is constructed from our StringWriter(Buffer))
          	String xmlString = sw.toString();

            	//print xml
            	System.out.println("Here's the xml:\n\n" + xmlString);
 	} catch (Exception e) {
           	 System.out.println(e);
        }
	
}
	


}//end class



