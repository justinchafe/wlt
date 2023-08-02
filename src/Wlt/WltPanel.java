//ADD INITIALIZATION ROUTINE, CALL AFTER DIALOG.  Participant should be init() and updated (& slides?)
package Wlt;

import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder; 
import java.awt.image.*;
import java.awt.FontMetrics;
import java.lang.StringBuilder;
import java.net.URI;
import java.util.StringTokenizer;
import java.io.*;


public class WltPanel extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, ActionListener {
	final Dimension RADIO_SIZE = new Dimension(75,75);
	private int ENDSCREEN;
	JRadioButton tiltButton, luckButton, shapeButton,  gravityButton, intuitionButton, movingButton, restButton;
	ButtonGroup group, motionGroup;
	boolean drawLine, hasPainted, drawBottle, hasClicked;
	Slides mySlides;
	JLabel testLabel;
	JPanel radioPanel, bottomPanel, centerPanel;	
	JEditorPane centerEditorPane, topEditorPane, bottomEditorPane;
	JScrollPane centerScrollPane, topScrollPane, bottomScrollPane;
	Participant p;
	LineManager l;
	double ratioX;
	double ratioY;
	int screenNum, oldX, oldY;

//NOTE USE System.nanoTime() for higher precision if required (millisecond should be good enough and has better JRE Compatibility, convert from nano-> mill: java.util.concurrent.TimeUnit.NANOSECONDS.toMillis
	
	
//Intro Slide text, Slide with vertical bottle (draw line). bottles / break / others / questions / end
//switch (screenNum) case 0:  display slide text, cont = page++
//case 1: show vertical bottle and show how to click!
//case 2-x: run through slides as normal.
//case x+1: show instructions
//case x+2 - n: continue running through slides.
//case n+1: outro data
//case n+2: final page


	public WltPanel(Participant p) {
		
		this.p = p;
		this.setPreferredSize(new Dimension(1024,768));
		this.setMinimumSize(new Dimension(1024, 768));
		this.setMaximumSize(new Dimension(1024, 768));
		
		screenNum = 0; //what page/screen we are on, 0 = no screen 1 through n = part of experiment.
		ENDSCREEN = -1;	
		hasPainted = hasClicked = drawLine = false;
		drawBottle = false;  //used to show the model of the bottle, debugging
		this.setLayout(new BorderLayout());
		this.setBackground(Color.white);
		
		bottomPanel = new JPanel();
		bottomPanel.setBackground(Color.white);	
		centerPanel = new JPanel();
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.PAGE_AXIS) );		
		centerPanel.setBackground(Color.white);	
		this.add(bottomPanel, BorderLayout.PAGE_END);
		this.add(centerPanel, BorderLayout.CENTER);
		//mySlides = new Slides("../xmldata/XmlIntroSideData.xml"); //removed 2023
		mySlides = new Slides(Wlt.XML_INTRO); //added 2023

		mySlides.loadFromFile();
		l = new LineManager(mySlides.getCurrentSide("right"), mySlides.getCurrentSide("left"), mySlides.getCurrentSide("top"),mySlides.getCurrentSide("bottom") );
		//testLabel = new JLabel("" + screenNum);
		centerScrollPane = new JScrollPane();
		centerEditorPane = new JEditorPane();
		centerEditorPane.setEditable(false);
		//java.net.URL introInstr = getClass().getClassLoader().getResource(Wlt.HTML_DIR + "/" + "introText.html");
		//System.out.println("Host: " + introInstr.getHost());
		//System.out.println("Path: " + introInstr.getPath());
		//System.out.println("File: " + introInstr.getFile());
		//System.out.println("External Form: " + introInstr.toExternalForm());
		//addEditorPane(introInstr.getPath().substring(1), true, "CENTER");
		addEditorPane(Wlt.HTML_DIR + "/" + "introText.html", true, "CENTER");
		centerScrollPane.setBorder(new EmptyBorder(0,0,0,0));
		centerPanel.add(centerScrollPane);
		//bottomPanel.add(testLabel, BorderLayout.PAGE_END);
		//testLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		//testLabel.setText("" + screenNum);
		repaint();
		
	}

	private void addRadioButtons() {
	
		radioPanel = new JPanel();
		radioPanel.setBackground(Color.white);
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.LINE_AXIS));	

		tiltButton = new JRadioButton("Tilt of container");
   		tiltButton.setMnemonic(KeyEvent.VK_B);
    		tiltButton.setActionCommand("A");
		tiltButton.setPreferredSize(RADIO_SIZE);
		tiltButton.setBackground(Color.white);	
		tiltButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		tiltButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);	
   		
   		luckButton = new JRadioButton("Luck (Chance)");
   	 	luckButton.setMnemonic(KeyEvent.VK_C);
   	 	luckButton.setActionCommand("B");
	 	luckButton.setPreferredSize(RADIO_SIZE);	
		luckButton.setBackground(Color.white);	
		luckButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		luckButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);   	

		shapeButton = new JRadioButton("Shape of container");
   	 	shapeButton.setMnemonic(KeyEvent.VK_D);
   		shapeButton.setActionCommand("C");
		shapeButton.setPreferredSize(RADIO_SIZE);
		shapeButton.setBackground(Color.white);	
		shapeButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		shapeButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);		

    		gravityButton = new JRadioButton("Law of gravitation");
    		gravityButton.setMnemonic(KeyEvent.VK_R);
    		gravityButton.setActionCommand("D");
		gravityButton.setPreferredSize(RADIO_SIZE);
		gravityButton.setBackground(Color.white);	
		gravityButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		gravityButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);
	    	
		intuitionButton = new JRadioButton("Intuition");
	    	intuitionButton.setMnemonic(KeyEvent.VK_P);
	    	intuitionButton.setActionCommand("E");
		intuitionButton.setPreferredSize(RADIO_SIZE);
		intuitionButton.setBackground(Color.white);	
		intuitionButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		intuitionButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);   
		
		 //Group the radio buttons.
   		group = new ButtonGroup();
    		group.add(tiltButton);
    		group.add(luckButton);
    		group.add(shapeButton);
    		group.add(gravityButton);
    		group.add(intuitionButton);
		
		radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

		radioPanel.add(tiltButton, "center");
		radioPanel.add(luckButton, "center");
		radioPanel.add(shapeButton, "center");
		radioPanel.add(gravityButton, "center");
		radioPanel.add(intuitionButton, "center");
		centerPanel.add(radioPanel);
			
		
	}	

	private void addMotionRadioButtons() {

		radioPanel = new JPanel();
		radioPanel.setBackground(Color.white);
		radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.LINE_AXIS));	
		
		movingButton = new JRadioButton("Moving?");
   		movingButton.setMnemonic(KeyEvent.VK_B);
    		movingButton.setActionCommand("A");
		movingButton.setPreferredSize(RADIO_SIZE);
		movingButton.setBackground(Color.white);	
		movingButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		movingButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);	
   		
   		restButton = new JRadioButton("At rest?");
   	 	restButton.setMnemonic(KeyEvent.VK_C);
   	 	restButton.setActionCommand("A");
	 	restButton.setPreferredSize(RADIO_SIZE);	
		restButton.setBackground(Color.white);	
		restButton.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		restButton.setVerticalAlignment(javax.swing.SwingConstants.CENTER);  

		motionGroup = new ButtonGroup();
    		motionGroup.add(movingButton);
    		motionGroup.add(restButton);

		radioPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
		radioPanel.add(movingButton, "center");
		radioPanel.add(restButton, "center");
		centerPanel.add(radioPanel);
		
	}
	


	private void addEditorPane(String filename, boolean isVisible, String where) {

		if (where.equals("CENTER")) {
			try {
				java.net.URL helpURL = getClass().getClassLoader().getResource(filename); //added 2023 - jar
				///java.net.URL helpURL = this.getClass().getResource(filename); //old
			
			if (helpURL != null) {
    				centerEditorPane.setPage(helpURL);
    			} else {
    				System.err.println("Couldn't find file: " + filename);
			}
			} catch (Exception e) {
       				 System.err.println("Attempted to read a bad URL: ");
  			}
			
			centerScrollPane.setViewportView(centerEditorPane);
			centerScrollPane.setVisible(isVisible);		
		
		}else if (where.equals("TOP")) {
			
			try {
				java.net.URL helpURL = getClass().getClassLoader().getResource(filename); //added 2023 - jar
				//java.net.URL helpURL = this.getClass().getResource(filename); //old
			
			if (helpURL != null) {
    				topEditorPane.setPage(helpURL);

			} else {
    				System.err.println("Couldn't find file: " + filename);
			}
			} catch (Exception e) {
       				 System.err.println("Attempted to read a bad URL: ");
  			}
						 
			topScrollPane.setViewportView(topEditorPane);
			topScrollPane.setVisible(isVisible);	

		}else if (where.equals("BOTTOM")) {
					
			try {
				java.net.URL helpURL = getClass().getClassLoader().getResource(filename); //added 2023 - jar
				//java.net.URL helpURL = this.getClass().getResource(filename); //old
			
			if (helpURL != null) {
    				bottomEditorPane.setPage(helpURL);
				
    			
			} else {
    				System.err.println("Couldn't find file: " + filename);
			}
			} catch (Exception e) {
       				 System.err.println("Attempted to read a bad URL: ");
  			}
						 
			bottomScrollPane.setViewportView(bottomEditorPane);
			bottomScrollPane.setVisible(isVisible);
			}
	}


	public void setRatios() {
		
		double rX = mySlides.getCurrentPanelDimensions().getWidth()/2 - mySlides.getCurrentImageDimensions().getWidth()/2;
		double rX1 = this.getWidth()/2 - mySlides.getCurrentImageDimensions().getWidth()/2;
		double rY = mySlides.getCurrentPanelDimensions().getHeight()/2 - mySlides.getCurrentImageDimensions().getHeight()/2;		
		double rY1 = this.getHeight()/2 - mySlides.getCurrentImageDimensions().getHeight()/2;	
		ratioX = rX - rX1;
		ratioY = rY - rY1; 
	}


	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		 g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (screenNum == 0 || screenNum == Wlt.MIDPOINT || screenNum == ENDSCREEN || screenNum == ENDSCREEN -1) {
			return;
 				
		}else {	
			g2d.drawImage(mySlides.getCurrentImage(), this.getWidth()/2 - (int) mySlides.getCurrentImageDimensions().getWidth()/2, this.getHeight()/2 - (int) mySlides.getCurrentImageDimensions().getHeight()/2, null);

				if (screenNum == 1) {

					if (l.getFirst() == 0) {
						g2d.setPaint(Color.red);
						FontMetrics fm = g.getFontMetrics();
						int w = fm.stringWidth("Click on any side of the bottle!");
						g.drawString("Click on any side of the bottle!", (int) (l.getLineAt(2,0).getX1()-25 - w - ratioX), (int) ((((l.getLastLine(2).getY2() - l.getLineAt(2,0).getY1())/2)+ l.getLineAt(2,0).getY1())-ratioY) );

					}else  {
						g2d.setPaint(Color.red);
						FontMetrics fm = g.getFontMetrics();
						int h = fm.getHeight();
						int w = fm.stringWidth("Now Click again to drag the line");
						g.drawString("Now Click again and drag to line", (int) (l.getLineAt(2,0).getX1()-25 - w - ratioX), (int) ((((l.getLastLine(2).getY2() - l.getLineAt(2,0).getY1())/2)+ l.getLineAt(2,0).getY1())-ratioY) );
						w = fm.stringWidth("OR Simply click on an opposite side");		
						g.drawString("OR Simply click on an opposite side", (int) (l.getLineAt(2,0).getX1()-25 - w - ratioX), (int) ((((l.getLastLine(2).getY2() - l.getLineAt(2,0).getY1())/2)+ l.getLineAt(2,0).getY1()) + h -ratioY) );
						g2d.setPaint(Color.black);				
					}

				}

			if (drawBottle) {
			drawBottle(g2d);								
	   		}		
		
			if (drawLine) {
				g2d.draw(new Line2D.Double(l.getUserLine().getX1() - ratioX, l.getUserLine().getY1() - ratioY, l.getUserLine().getX2() - ratioX, l.getUserLine().getY2() - ratioY));
			}	

			if (!hasPainted) {
				Runtime r = Runtime.getRuntime();
				r.gc();
				p.addStartTime(System.currentTimeMillis());
				hasPainted = true;
			}
	
				
		}
	
	}

	private void drawBottle(Graphics2D g2d) {
		int i;
			Line2D line, z;
			line = l.getRightSide().get(0);
			GeneralPath path = new GeneralPath();
			g2d.setColor(Color.ORANGE);
			for (i=0; i < l.getRightSide().size() &&  (line = l.getRightSide().get(i)) != null ; i++) {
					System.out.println("linedata time: " + line.getX1() + ", " +  line.getY2());
					z = new Line2D.Double(line.getX1() - ratioX, line.getY1() - ratioY, line.getX2() - ratioX, line.getY2() - ratioY);
					path.append(z, true);
					g2d.fill(new Ellipse2D.Double(line.getX1()-ratioX, line.getY1()- ratioY, 7.0, 7.0));
			}
			g2d.fill(new Ellipse2D.Double(line.getX2()-ratioX, line.getY2()-ratioY, 7.0, 7.0));
			g2d.draw(path);	

			path = new GeneralPath();
			for (i=0; i < l.getLeftSide().size() &&  (line = l.getLeftSide().get(i)) != null ; i++) {
					System.out.println("linedata time: " + line.getX1() + ", " +  line.getY2());
					z = new Line2D.Double(line.getX1() - ratioX, line.getY1() - ratioY, line.getX2() - ratioX, line.getY2() - ratioY);
					path.append(z, true);
					g2d.fill(new Ellipse2D.Double(line.getX1()-ratioX, line.getY1()- ratioY, 7.0, 7.0));
			}
			g2d.fill(new Ellipse2D.Double(line.getX2()-ratioX, line.getY2()-ratioY, 7.0, 7.0));
			g2d.draw(path);	

			path = new GeneralPath();
			for (i=0; i < l.getTopCap().size() &&  (line = l.getTopCap().get(i)) != null ; i++) {
					System.out.println("linedata time: " + line.getX1() + ", " +  line.getY2());
					z = new Line2D.Double(line.getX1() - ratioX, line.getY1() - ratioY, line.getX2() - ratioX, line.getY2() - ratioY);
					path.append(z, true);
					g2d.fill(new Ellipse2D.Double(line.getX1()-ratioX, line.getY1()- ratioY, 7.0, 7.0));
			}
			g2d.fill(new Ellipse2D.Double(line.getX2()-ratioX, line.getY2()-ratioY, 7.0, 7.0));
			g2d.draw(path);	

			path = new GeneralPath();
			for (i=0; i < l.getBottomCap().size() &&  (line = l.getBottomCap().get(i)) != null ; i++) {
					System.out.println("linedata time: " + line.getX1() + ", " +  line.getY2());
					z = new Line2D.Double(line.getX1() - ratioX, line.getY1() - ratioY, line.getX2() - ratioX, line.getY2() - ratioY);
					path.append(z, true);
					g2d.fill(new Ellipse2D.Double(line.getX1()-ratioX, line.getY1()- ratioY, 7.0, 7.0));
			}
			g2d.fill(new Ellipse2D.Double(line.getX2()-ratioX, line.getY2()-ratioY, 7.0, 7.0));
			g2d.draw(path);	
			g2d.setColor(Color.BLACK);

	}

	public void mousePressed(MouseEvent e) {
		mouseClicked(e);
	}

	public void mouseReleased(MouseEvent e) {
		
		if (l.intersectionCheck(e.getX() + ratioX, e.getY() + ratioY) ) {
			drawLine = true;
			repaint();
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
		if (!hasClicked) {
			hasClicked = true;
			oldX = (int) e.getX();
			oldY = (int) e.getY();
			p.addClickTime(System.currentTimeMillis());
			
		}

		if (screenNum >= 1) {
			if (l.lineHitDetection(e.getX() + ratioX, e.getY() + ratioY) ) {
				drawLine = true;
				repaint();
			}
				
				
				
		}
	}	


	public void mouseDragged(MouseEvent e) {
		if (screenNum >= 1) {
			
				
		if (!(l.getFirst() == 0)) { 
				l.setUserLine(e.getX() + ratioX, e.getY() + ratioY);
				drawLine = true;
				repaint();
			/*						
				if ( (new Point2D.Double(oldX,oldY).distance(e.getX(), e.getY()) ) > 20) {
					System.out.println("True: Calling Repaint!");
					oldX = (int) l.getUserLine().getX2();
					oldY = (int) l.getUserLine().getY2();
					repaint();
				}
			*/	
			
			}	
						
		}	
		
	
	}

	public void mouseMoved(MouseEvent e) {
	} 

	public void componentHidden(ComponentEvent e) {
        	  //Invoked when the component has been made invisible.
	}
 	
	public void componentMoved(ComponentEvent e) {
		setRatios();
		repaint();
	}      

 	public void componentResized(ComponentEvent e) {
		setRatios();
		 repaint();
	}

 	public void componentShown(ComponentEvent e) {
		setRatios();
	}


	public void actionPerformed(ActionEvent event) {

		if (screenNum ==  0) {
			screenNum++;
			centerScrollPane.setVisible(false);
			this.remove(centerPanel);
			topEditorPane = new JEditorPane();
			topScrollPane = new JScrollPane();
			bottomEditorPane = new JEditorPane();
			bottomScrollPane = new JScrollPane();
			topScrollPane.setBorder(new EmptyBorder(0,0,0,0));
			bottomScrollPane.setBorder(new EmptyBorder(0,0,0,0));

			addEditorPane(Wlt.HTML_DIR + "/" + "bottleInstructionsBottom.html", true, "BOTTOM");
			addEditorPane(Wlt.HTML_DIR + "/" + "bottleInstructionsTop.html", true, "TOP");
			topScrollPane.setPreferredSize(new Dimension(135,135));
			bottomScrollPane.setPreferredSize(new Dimension(100,100));
			this.add(topScrollPane, BorderLayout.NORTH);
			this.add(bottomScrollPane, BorderLayout.PAGE_END);
			//testLabel.setText("" + screenNum);		
			this.validate();
			repaint();
		
		
		}else if (screenNum == 1) {
			
			if (!l.isUserLineDrawn()) {
				JFrame frame = new JFrame("Draw Line!");
				JOptionPane.showMessageDialog(null, Wlt.NOT_FINISHED_ERROR);
			}else {
				screenNum++;
				//testLabel.setText("" + screenNum);
				this.remove(topScrollPane);
				this.remove(bottomScrollPane);
				//mySlides = new Slides("../xmldata/XmlSideData.xml");
				mySlides = new Slides(Wlt.XML_SLIDES); //added 2023.
				mySlides.loadFromFile();
				ENDSCREEN = mySlides.getMax() + Wlt.NUM_NON_TRIAL_SCREENS-1;
				l.resetUserLine();
				l.updateSides(mySlides.getCurrentSide("right"), mySlides.getCurrentSide("left"), mySlides.getCurrentSide("top"), mySlides.getCurrentSide("bottom"));
				hasPainted = drawLine = hasClicked = false;
				setRatios();
				repaint();
			}

			

		}else if (screenNum == Wlt.MIDPOINT) {
			screenNum++;
			this.remove(centerPanel);
			hasPainted=false;
			//testLabel.setText("" + screenNum);
			repaint();
			
		
		}else if (screenNum == ENDSCREEN -1) {
				if (movingButton.isSelected()) {
					p.addMotionSelection("moving");
				}else if (restButton.isSelected()) {
					p.addMotionSelection("rest");
				}else { 
					JFrame frame = new JFrame("Make a Selection!");
					JOptionPane.showMessageDialog(null, Wlt.NO_SELECTION_ERROR);
					return;
				}
				screenNum++;
				addEditorPane(Wlt.HTML_DIR + "/" + "endText2.html", true, "CENTER");
				centerScrollPane.setPreferredSize(new Dimension(200,200));
				centerPanel.remove(radioPanel);
				addRadioButtons();
				this.validate();
			
		
				
		}else if (screenNum == ENDSCREEN) {
			if (tiltButton.isSelected()) {
				p.addApperanceSelection("tilt");
			}else if (luckButton.isSelected()) {
				p.addApperanceSelection("luck");
				p.addMotionSel("tilt");
			}else if (shapeButton.isSelected()) {
				p.addApperanceSelection("shape");
			}else if (gravityButton.isSelected()) {
				 p.addApperanceSelection("gravitation");
			}else if (intuitionButton.isSelected()) {
				p.addApperanceSelection("intuition");
			}else {
				JFrame frame = new JFrame("Make a Selection!");
				JOptionPane.showMessageDialog(null, Wlt.NO_SELECTION_ERROR);
				return;
			}
			
			p.writeXmlFile();
			p.writeDataToFile();
			System.exit(0);
		
		
		
		}else {
			
			if (!l.isUserLineDrawn()) {
				JFrame frame = new JFrame("Draw Line!");
				JOptionPane.showMessageDialog(null, Wlt.NOT_FINISHED_ERROR);
			}else {
				
				p.addSlideElement(Integer.toString(mySlides.getCurrentSlideNum()));
				p.addStartTime(mySlides.getCurrentSlideNum());
				p.addAngleElement(Integer.toString(mySlides.getCurrentSlideNum()), java.lang.Double.toString(l.getAngle()));
				p.addSlideNum(mySlides.getActualSlideNum());
				p.addMidTime(mySlides.getCurrentSlideNum());
				p.addEndTime(System.currentTimeMillis());
				p.addEndTime(Integer.toString(mySlides.getCurrentSlideNum()), java.lang.Double.toString(System.currentTimeMillis()) );				
				p.addTotalTime(mySlides.getCurrentSlideNum());
				p.addAngle(l.getAngle());
				p.addMotionSel("Null");
				p.addApperanceSel("Null");	
				
				if (mySlides.advanceSlide()) {
					
					
					if (p.writeDataToFile()) {
						l.resetUserLine();
						l.updateSides(mySlides.getCurrentSide("right"), mySlides.getCurrentSide("left"), mySlides.getCurrentSide("top"), mySlides.getCurrentSide("bottom"));
						drawLine = false;
						hasClicked = false;
						hasPainted = false;
						
						if (screenNum == Wlt.MIDPOINT-1) {
							addEditorPane(Wlt.HTML_DIR + "/"+ "midText.html", true, "CENTER");
							this.add(centerPanel);
							screenNum++;
							//testLabel.setText("" + screenNum);
							this.validate();
						}else {
							
							screenNum++;
							//testLabel.setText("" + screenNum);
							repaint();
						}
					}else {
						p.writeXmlFile();
						System.exit(0);
					}		
			
					
				}else {
					
					hasPainted = false;
					drawLine = false;
					hasClicked = false;
					screenNum++;
					//testLabel.setText("" + screenNum);

					addEditorPane(Wlt.HTML_DIR + "/" + "endText.html", true, "CENTER");
					centerScrollPane.setPreferredSize(new Dimension(225,225));
					addMotionRadioButtons();
					this.add(centerPanel,BorderLayout.NORTH);
					this.validate();
					
					
				}
			}
		}
	}	
			







}


