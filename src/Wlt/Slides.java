package Wlt;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Double;
import javax.imageio.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;

public class Slides {
	XmlReader xml;
	final int SLIDE_FIELDS = 9; //number of fields of slide information
        Object [][] slides;
	int max_Slides; // the maximum number of slides
        int currentSlide; // the current slide 
        private int slideCount; //how many slides have been loaded
                        

	public Slides(String xmlFile) {
		xml = new XmlReader(xmlFile);
		max_Slides = xml.getNumSlides();
		Wlt.NUM_SLIDES = max_Slides;
		slides = new Object[max_Slides][SLIDE_FIELDS];
		currentSlide = 1;
		slideCount = 0;
		
		//System.out.println("HERE IS THE IMAGE TO LOAD FROM OUR XML FILE: " + xml.getImage(slideCount+1));

	}

//add a single slide.
	public boolean addSlide(String imageName, Dimension imgDim, Dimension panelDim,  ArrayList<Line2D> rightSide, ArrayList<Line2D> leftSide, ArrayList<Line2D> topSide, ArrayList<Line2D> bottomSide, String sNum) {
		if (slideCount < max_Slides) {
			
			slides[slideCount][0] = imageName;
			slides[slideCount][1] = imgDim;
			slides[slideCount][2] = panelDim;
			slides[slideCount][3] = rightSide;
			slides[slideCount][4] = leftSide;
			slides[slideCount][5] = topSide;
			slides[slideCount][6] = bottomSide;
			slides[slideCount][8] = sNum;
			slideCount++;
			
			return true;
		}else 
			return false;
		
	}


	public Dimension getCurrentImageDimensions() {
		if (currentSlide <= max_Slides) {
			return (Dimension) slides[currentSlide-1][1];		
		}
		else return null;
	}

	public Dimension getCurrentPanelDimensions() {
		if (currentSlide <= max_Slides) {
			return (Dimension) slides[currentSlide-1][2];		
		}
		else return null;
	}
/*
Method to add multiple slides from a file.  File Format:
filename:String, LineOne.x1, LineOne.y1, LineOne.x2, LineOne.y2, LineTwo.x1, LineTwo.y1, LineTwo.x1, LineTwo.x2.

*/
	public boolean loadFromFile(String filename) {
		//Moving to XML implementation....
		BufferedReader in;
		FileReader file;
		StringTokenizer tokens;
		int i;
		Line2D lineOne, lineTwo;
		lineOne = new Line2D.Double();
		lineTwo = new Line2D.Double();
		double x1,y1,x2,y2;
		String line, image, sNum;
	
			try {
				in = new BufferedReader(new FileReader(filename));
				line = null;
						
				while ((line = in.readLine()) != null) {
					tokens = new StringTokenizer(line, ",");
					
					image = tokens.nextToken();
					
					for (i=0; i<2; i++) {				
						x1 = java.lang.Double.parseDouble(tokens.nextToken());
						y1 = java.lang.Double.parseDouble(tokens.nextToken());
						x2 = java.lang.Double.parseDouble(tokens.nextToken());		
						y2 = java.lang.Double.parseDouble(tokens.nextToken());
						
						switch (i) {
		
							case 0:
								lineOne = new Line2D.Double(x1, y1, x2, y2);
								break;
							case 1:
								lineTwo = new Line2D.Double(x1, y1, x2, y2);
								break;
						}
					
					}//end for
					sNum = tokens.nextToken();
					line = null;
				}//end while
			
				in.close();
				return true;
			}catch (IOException e) {
				return false;
		 
			}

					
	}

	public boolean loadFromFile() {
		try {
		
			while (slideCount < max_Slides) {
				addSlide(xml.getImage(slideCount+1), xml.getImageDimensions(slideCount+1), xml.getPanelDimensions(slideCount+1), xml.getSide(slideCount+1, "Right"), xml.getSide(slideCount+1, "Left"), xml.getSide(slideCount+1, "Top"), xml.getSide(slideCount+1, "Bottom"), java.lang.Integer.toString(slideCount+1));
				//NOTE that slideCount is automatically incremented after we add a slide (i.e. in the addSlide method). 	
			 }
			return true;

		}catch (Exception e) {
			return false;
		}
	}

	public String getCurrentImageName() {
		return (String) slides[currentSlide-1][0];
	}
 
	public BufferedImage getCurrentImage() {
		JFrame frame = new JFrame("warning");
		BufferedImage image;
		      	
			try {
				java.net.URL url = getClass().getClassLoader().getResource(Wlt.IMG_DIR + "/" + ((String) slides[currentSlide-1][0])); //Updated 2023 for jar packaging.
				//image = ImageIO.read(new File((String) slides[currentSlide-1][0]))
				image = ImageIO.read(url);
				return image;
					
			}catch (IllegalArgumentException e) {
				JOptionPane.showMessageDialog(frame,
						"Error Loading Image (null?): " + Wlt.IMG_DIR + "/" + ((String) slides[currentSlide-1][0]) ,
						"",
						JOptionPane.WARNING_MESSAGE);
			}

			catch (IOException e) {
				JOptionPane.showMessageDialog(frame,
    				"Error Loading Image!",
   				"",
    				JOptionPane.WARNING_MESSAGE);
				
			}	
	  		
		return null;
		
	}

	public boolean advanceSlide() {
		if (currentSlide < max_Slides) {
			currentSlide++;
			return true;
		}else {
			//System.out.println("Max Slides Reached!");
			return false;
		}
	}

	public int getCurrentSlideNum() {
		return currentSlide;
	}

	public int getActualSlideNum() {
		return java.lang.Integer.parseInt( ( (String) slides[currentSlide-1][8])  );
	}

	public boolean isMax() {
		return currentSlide >= max_Slides; 
	}

	@SuppressWarnings({"unchecked"})
	public ArrayList<Line2D> getCurrentSide(int whichSide) {
		int offSet = 2;		
		switch (whichSide) {
			case 1:
			case 2:
			case 3:
			case 4:
				return (ArrayList<Line2D>) slides[currentSlide-1][offSet + whichSide];
				
			default:
				throw new  ArrayIndexOutOfBoundsException("Invalid Side: " + whichSide);
				
		} 
	}

	@SuppressWarnings({"unchecked"})
	public ArrayList<Line2D> getCurrentSide(String whichSide) {
		int side;
		int offSet = 2;
		String wS;
		whichSide = whichSide.toLowerCase().trim();
		
		if (whichSide.equals("right")) {
			side = 1;
		}else if (whichSide.equals("left")) {
			side = 2;
		
		}else if (whichSide.equals("top")) {
			side = 3;

		}else if (whichSide.equals("bottom")) {
			side = 4;
			
		}else {
			throw new  ArrayIndexOutOfBoundsException("Invalid Side: " + whichSide);
		}

		return (ArrayList<Line2D>) slides[currentSlide-1][side+offSet];
	}

	public int getMax() {
		return max_Slides;
	}

//This can be somewhat expensive...arrayCopy...use wisely!
	public void increaseMax(int newMax) {
		if (newMax > max_Slides) {
			max_Slides = newMax;
			//must resize array!
			Object [][] resizedSlides = new Object[max_Slides][SLIDE_FIELDS];
			System.arraycopy(slides, 0, resizedSlides, 0, max_Slides-1);
			slides = resizedSlides;
		
		}else
			//throw error
			System.out.println("Error:  Current Max is greater than new max");
	}

/*
public static void main(String[] args) {
Slides s = new Slides("xmlSideData.xml");
System.out.println("Max Slides = "+ s.getMax());
s.loadFromFile();
System.out.println("Current Slide Number: " + s.getCurrentSlideNum());
System.out.println(s.getCurrentImageName());
System.out.println(s.getCurrentImageDimensions().getWidth() + ", " + s.getCurrentImageDimensions().getHeight());
System.out.println(s.getCurrentPanelDimensions().getWidth() + ", " + s.getCurrentPanelDimensions().getHeight());
System.out.println(s.getCurrentSide(1).get(0).getX1());
s.advanceSlide();
System.out.println("Current Image:  " + s.getCurrentImageName());
}

*/



}//end class
