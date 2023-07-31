//LINE MANAGER

package Wlt;
import java.awt.geom.*;
import java.util.ArrayList;



public class LineManager {
	final double TOLERANCE = 1.0;
	final boolean DEBUG_ON = false;
	final int LINE_EXT = 2;
	private Line2D userLine;
	private Line2D lineOne;
	private Line2D lineTwo;
	private Line2D capOne; //Bottom
	private Line2D capTwo; //TOP (Break into portion??))
	private ArrayList <Line2D> rightSide;
	private ArrayList <Line2D> leftSide;
	private ArrayList <Line2D> topCap; 
	private ArrayList <Line2D> bottomCap; 
	private int first;
	private boolean isUserLineDrawn;
	
//LineOne should be taken as the left most line if the bottle was standing upright
//LineTwo should be taken as the right most line if the bottle was standing upright
//topCap is essentially the bottom of the bottle if the bottle was standing upright, with start marked from the bottom of LineOne
//bottomCap is essentially the top of the bottle if the bottle was standing upright, witht start marked from the top of LineTWo

	public LineManager() {
		Point2D point;
		point = null;
		userLine = new Line2D.Double(0,0,0,0);
		first = 0;
		isUserLineDrawn = false;
		rightSide = new ArrayList<Line2D>(0);
		leftSide = new ArrayList<Line2D>(0);
		topCap = new ArrayList<Line2D>(0);
		bottomCap = new ArrayList<Line2D>(0);
	}

	public LineManager(ArrayList<Line2D> right, ArrayList<Line2D> left, ArrayList<Line2D> top, ArrayList<Line2D> bottom) {
		Point2D point;
		point = null;
		userLine = new Line2D.Double(0,0,0,0);
		first = 0;
		isUserLineDrawn = false;
		rightSide = right;
		leftSide = left;
		topCap = top;
		bottomCap = bottom;	
	}
	public void updateSides(ArrayList<Line2D> rightSide, ArrayList<Line2D> leftSide, ArrayList<Line2D> topSide, ArrayList<Line2D> bottomSide) {
		this.rightSide = rightSide;
		this.leftSide = leftSide;
		topCap = topSide;
		bottomCap = bottomSide;
	}

	public void addLineRight(Line2D l) {
		rightSide.add(l);
	}

	public void addLineLeft(Line2D l) {
		leftSide.add(l);
	}

	public void addLineTop(Line2D l) {
		topCap.add(l);
	}

	public void addLineBottom(Line2D l) {
		bottomCap.add(l);
	}


	public Line2D getUserLine() {
		return userLine;
	}

	public ArrayList<Line2D> getRightSide() {
		return rightSide;
	}

	public ArrayList<Line2D> getLeftSide() {
		return leftSide;
	}

	public ArrayList<Line2D> getTopCap() {
		return topCap;
	}

	public ArrayList<Line2D> getBottomCap() {
		return bottomCap;
	}

	public boolean removeLastLine(int whichSide) {
		if (whichSide == 1 && !rightSide.isEmpty()) {
			rightSide.remove(rightSide.size()-1);
			return true;
		}else if (whichSide == 2 && !leftSide.isEmpty()) {
			leftSide.remove(leftSide.size()-1);
			return true;
		}else if (whichSide == 3 && !topCap.isEmpty()) {
			topCap.remove(topCap.size()-1);
			return true;
		}else if (whichSide == 4 && !bottomCap.isEmpty()) {
			bottomCap.remove(bottomCap.size()-1);
			return true;
		}else
			return false;

	}


	public Line2D getLineAt(int whichSide, int index) {
		if (whichSide == 1 && !rightSide.isEmpty()) {
			return rightSide.get(index);
		}else if (whichSide == 2 && !leftSide.isEmpty()) {
			return leftSide.get(index);
		}else if (whichSide == 3 && !topCap.isEmpty()) {
			return topCap.get(index);
		}else if (whichSide == 4 && !bottomCap.isEmpty()) {
			return bottomCap.get(index);
		}else
			return null;
	}

	public Line2D getLastLine(int whichSide) {
		if (whichSide == 1 && !rightSide.isEmpty()) {
			return rightSide.get(rightSide.size()-1);
		}else if (whichSide == 2 && !leftSide.isEmpty()) {
			return leftSide.get(leftSide.size()-1);
		}else if (whichSide == 3 && !topCap.isEmpty()) {
			return topCap.get(topCap.size()-1);
		}else if (whichSide == 4 && !bottomCap.isEmpty()) {
			return bottomCap.get(bottomCap.size()-1);
		}else
			return null;
	}

	public void clearSide(int whichSide) {
		if (whichSide == 1 && !rightSide.isEmpty()) {
			rightSide.clear();
		}else if (whichSide == 2 && !leftSide.isEmpty()) {
			leftSide.clear();
		}else if (whichSide == 3 && !topCap.isEmpty()) {
			topCap.clear();
		}else if (whichSide == 4 && !bottomCap.isEmpty()) {
			bottomCap.clear();
		}else
			System.out.println("Unknown Side: " + whichSide);
	}

	public void clearAllSides() {
		rightSide.clear();
		leftSide.clear();
		topCap.clear();
		bottomCap.clear();
	}

	
	//Set new ENDPOINTS:
	public void setUserLine(double x2, double y2) {
		userLine.setLine(userLine.getX1(), userLine.getY1(), x2, y2);
	}

	public void setUserLine(double x1, double y1, double x2, double y2) {
		userLine.setLine(x1,y1,x2,y2);		
	}


	public void resetUserLine() {
		userLine.setLine(0,0,0,0);
		first = 0;
		isUserLineDrawn = false;
	}

	public Line2D getLineOne() {
		return lineOne;
	}

	public Line2D getLineTwo() {
		return lineTwo;
	}

	public double getAngle() {
		double k = 1.0;
		double angle = (Math.atan2(userLine.getY1() - userLine.getY2(), userLine.getX2() - userLine.getX1()) ) * (180/Math.PI);
		if (angle < 0.0) {
			k = -1.0;
		}
		angle = Math.abs(angle);
		if (angle > 90.0) {
			angle = 180 - angle;
		}
		
		if (Wlt.ALLOW_NEG_ANGLE) {	
			return k*angle;
		}else
			return angle;
	}

	public boolean isUserLineDrawn() {
		return isUserLineDrawn;
	}

	public void updateLines(Line2D lineOne, Line2D lineTwo) {
		this.lineOne = lineOne;
		this.lineTwo = lineTwo;
		capOne.setLine(lineOne.getX2(), lineOne.getY2(), lineTwo.getX2(), lineTwo.getY2());
		capTwo.setLine(lineOne.getX1(), lineOne.getY1(), lineTwo.getX1(), lineTwo.getY1());
		
	}

	//Checks to see if x,y intesect any line in this Side.
	//returns the Line which it intersects OR  null if x,y doesn't intersect.
	public Line2D sideHitDetection(double x, double y, int whichLine) {
		double qo;		
		int i = 1;
		int k = 0;
		Line2D currentLine;
		if (whichLine == 1) {
			while (k < rightSide.size()) {
				currentLine = rightSide.get(k++);
				qo = currentLine.ptSegDist(x,y);
				if (qo <= Wlt.LINE_DETECT) {
					return currentLine;
				}
			}
			return null;
		}else if (whichLine == 2) {
			while (k < leftSide.size()) {
				currentLine = leftSide.get(k++);
				qo = currentLine.ptSegDist(x,y);
				if (qo <= Wlt.LINE_DETECT) {
					return currentLine;
				}
			}
			return null;
		}else if (whichLine ==3) {
			while (k < topCap.size()) {
				currentLine = topCap.get(k++);
				qo = currentLine.ptSegDist(x,y);
				if (qo <= Wlt.LINE_DETECT) {
					return currentLine;
				}
			}
			return null;


		}else if (whichLine == 4) {
			while (k < bottomCap.size()) {
				currentLine =  bottomCap.get(k++);
				qo = currentLine.ptSegDist(x,y);
				if (qo <= Wlt.LINE_DETECT) {
					return currentLine;
				}
			}
			return null;
		}else
			return null;
	}

/*
	//returns true if the endpoint was detected. Returns zero if nothing is detected.
	public boolean sideEndPointHitDetection(double x, double y, whichSide) {
		Point2D e1;
		Point2D e2;
		int i = 1;
		int k = 0;
		Line2D currentLine;
		while (k<numLines) {
			currentLine = lineList.get(k++);
			e1 = new Point2D.Double(currentLine.getX1(), currentLine.getY1());
			System.out.println(e1.getX() + ", " + e1.getY());
			System.out.println(e1.distance(x,y));
			e2 = new Point2D.Double(currentLine.getX2(), currentLine.getY2());
			System.out.println(e2.getX() + ", " + e2.getY());
			System.out.println(e2.distance(x,y));
			if (e1.distance(x,y) < ENDPOINT_DIST || e2.distance(x,y) < ENDPOINT_DIST)  {
				return true;
			}
			
		}
		return false;
				
	}

*/
	
	/**
	**This method will set the start point.  It is a helper method used for moving the start point of the userLine to a point on the line.  
	**It is needed so that we can detect "hits" that are within a specfied hit detection range which may not be "on the line".  Thus it checks
	**the nearest path to the line in question, finds the intersection point and sets the userLine start coordinates to the intersection point.
	**/
	private void setStartPoint(double x, double y, double qo, int whichSide) {
		Point2D.Double point;	
		double radian;
		double angle;
		double endX;
		double endY;
		double radius = qo*2;
		for (angle = 0; angle <= 360; angle++) {
			radian = Math.toRadians(angle);
			endX = x+radius*Math.cos(radian);
			endY = y+radius*Math.sin(radian);
			userLine.setLine(x,y,endX,endY);
			point = getIntersectionPoint(whichSide,userLine);
			if (point != null) {
				x = point.getX();
				y = point.getY();
				userLine.setLine(x,y,x,y);
				return;
			}
		}		
	}	
	
	/**
	Check to see if the x,y coordinates fall on a line.  If the user has never clicked on either line and we are within the correct distance, then...we mark that we clicked 
	this line first.  Otherwise, we check to see if we have clicked close enough to the opposite line.  If so we set the userLine to the appropriate coordiantes. 
	-retruns True if we have never clicked on a line and we are close enough to one & updates userLine
	-retruns True if we have clicked on a line and are close enough to the opposite line & updates userLine
	-returns False if we are not near enough to a line
	*
	**/	 
	public boolean lineHitDetection(double x, double y) {
		Line2D line;
		Point2D.Double point;
		double qo;
		int i = 1;
		int min, k;
		min = k = 0;
		
		int q[] = new int[4];
		

		
		
		switch (first) {

			case 0:
				
			while (k < 4) {
				line = sideHitDetection(x,y,k+1);
				if (line == null) {
					q[k] = -1;
				}else {
					q[k] = (int)line.ptSegDist(x,y);
					if (min == 0) {
						min = k+1;
					}else if (q[k] < min) {
						min = k+1;
				}
					
				}k++;
			}
			
			
			if (min == 0) {
				
				System.out.println("NO DETECTION, NOT CLOSE ENOUGH");
				return false;

				
			}else {
				
				if (min == 1 && (line = sideHitDetection(x,y,1)) != null) {
					qo = q[0]; //qo = line.ptSegDist(x,y);			
					if (qo <= Wlt.LINE_DETECT) {
						setStartPoint(x,y,qo,1);
						first = 1;
						return true;
					}
				}else if (min == 2 && (line = sideHitDetection(x,y,2)) != null) {
					qo = q[1];  ////qo = line.ptSegDist(x,y);					
					if (qo <= Wlt.LINE_DETECT) {
						setStartPoint(x,y,qo,2);
						first = 2;
						return true;
					}

				}else if (min == 3 && (line = sideHitDetection(x,y,3)) != null) {
					qo = q[2]; //qo = line.ptSegDist(x,y);
					if (qo <= Wlt.LINE_DETECT) {
						setStartPoint(x,y,qo,3);
						first = 3;
						return true;
					}
				}else if (min == 4 && (line = sideHitDetection(x,y,4)) != null) {
					qo = q[3]; //qo = line.ptSegDist(x,y);
					if (qo <= Wlt.LINE_DETECT) {
						setStartPoint(x,y,qo,4);
						first = 4;
						return true;
					}
				}
			}
			break;
	
			case 1:
			
				if ((line = sideHitDetection(x,y,2)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
					userLine.setLine(userLine.getX1(),userLine.getY1(),x,y); 
					extendLine(); 
					if (!trimExtendedLine(1)) {
						setUserLine(x,y);	
						return false;			
											
					}
					showAngleDebugInfo(); //DEBUGGING
					isUserLineDrawn = true;					
					return true;		
	
				}else if ((line = sideHitDetection(x,y,3)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
						userLine.setLine(userLine.getX1(),userLine.getY1(),x,y); 
						extendLine(); 
						if (!trimExtendedLine(1)) {
							setUserLine(x,y);
							return false;				
											
						}
						showAngleDebugInfo(); 
						isUserLineDrawn = true;					
						return true;	

				}else if ((line = sideHitDetection(x,y,4)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
						userLine.setLine(userLine.getX1(),userLine.getY1(),x,y); 
						extendLine(); 
						if (!trimExtendedLine(1)) {
							setUserLine(x,y);
							return false;				
											
						}
						showAngleDebugInfo(); 
						isUserLineDrawn = true;					
						return true;	
				}		
						

			 	break;	
	
			case 2:
				
				if ((line = sideHitDetection(x,y,1)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
					userLine.setLine(userLine.getX1(),userLine.getY1(), x, y);
					extendLine();
					if (!trimExtendedLine(2)) {
						setUserLine(x,y);
						return false;				
											
					}
					showAngleDebugInfo(); 			
					isUserLineDrawn = true;					
					return true;

				}else if ((line = sideHitDetection(x,y,3)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
						userLine.setLine(userLine.getX1(),userLine.getY1(),x,y); 
						extendLine(); 
						if (!trimExtendedLine(2)) {
							setUserLine(x,y);
							return false;												
						}
						showAngleDebugInfo(); 
						isUserLineDrawn = true;					
						return true;	

				}else if ((line = sideHitDetection(x,y,4)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
						userLine.setLine(userLine.getX1(),userLine.getY1(),x,y); 
						extendLine(); 
						if (!trimExtendedLine(2)) {
							setUserLine(x,y);
							return false;				
											
						}
						showAngleDebugInfo(); 
						isUserLineDrawn = true;					
						return true;	
				}
				
				break;

			case 3:
				if ((line = sideHitDetection(x,y,1)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
					userLine.setLine(userLine.getX1(),userLine.getY1(), x, y);
					extendLine();
					if (!trimExtendedLine(3)) {
						setUserLine(x,y);
						return false;				
											
					}
					showAngleDebugInfo(); 		
					isUserLineDrawn = true;					
					return true;

				}else if ((line = sideHitDetection(x,y,2)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
						userLine.setLine(userLine.getX1(),userLine.getY1(),x,y); 
						extendLine(); 
						if (!trimExtendedLine(3)) {
							setUserLine(x,y);
							return false;									
						}
						showAngleDebugInfo(); 
						isUserLineDrawn = true;					
						return true;	

				}else if ((line = sideHitDetection(x,y,4)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
						userLine.setLine(userLine.getX1(),userLine.getY1(),x,y); 
						extendLine(); 
						if (!trimExtendedLine(3)) {
							setUserLine(x,y);
							return false;								
						}
						showAngleDebugInfo(); 
						isUserLineDrawn = true;					
						return true;	
				}		
				break;
		
			case 4:
				if ((line = sideHitDetection(x,y,1)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
					userLine.setLine(userLine.getX1(),userLine.getY1(), x, y);
					extendLine();
					if (!trimExtendedLine(4)) {
						setUserLine(x,y);
						return false;				
											
					}
					showAngleDebugInfo(); 			
					isUserLineDrawn = true;					
					return true;

				}else if ((line = sideHitDetection(x,y,2)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
						userLine.setLine(userLine.getX1(),userLine.getY1(),x,y); 
						extendLine(); 
						if (!trimExtendedLine(4)) {
							setUserLine(x,y);
							return false;				
											
						}
						showAngleDebugInfo(); 
						isUserLineDrawn = true;					
						return true;	

				}else if ((line = sideHitDetection(x,y,3)) != null && line.ptSegDist(x,y) <= Wlt.LINE_DETECT) {
						userLine.setLine(userLine.getX1(),userLine.getY1(),x,y); 
						extendLine(); 
						if (!trimExtendedLine(4)) {
							setUserLine(x,y);
							return false;				
											
						}
						showAngleDebugInfo(); 
						isUserLineDrawn = true;					
						return true;	
				}	
				break;

			default:
				return false;

		}//end switch
		return false;
	}

	private boolean trimExtendedLine(int startLine) {
		Point2D point;		
		switch (startLine) {
			case 1:
				if ( (point = getIntersectionPoint(2, userLine))!= null) {
						setUserLine(point.getX(), point.getY());
						return true;						
					}else if ( (point = getIntersectionPoint(3, userLine) ) != null) {
						setUserLine(point.getX(), point.getY());
						return true;
					}else if ( (point = getIntersectionPoint(4, userLine) ) != null) {
						setUserLine(point.getX(), point.getY());
						return true;
					}else 
						return false;
			case 2:
				if ( (point = getIntersectionPoint(1, userLine))!= null) {
						setUserLine(point.getX(), point.getY());
						return true;						
					}else if ( (point = getIntersectionPoint(3, userLine) ) != null) {
						setUserLine(point.getX(), point.getY());
						return true;
					}else if ( (point = getIntersectionPoint(4, userLine) ) != null) {
						setUserLine(point.getX(), point.getY());
						return true;
					}else 
						return false;

			case 3:
				if ( (point = getIntersectionPoint(1, userLine))!= null) {
						setUserLine(point.getX(), point.getY());
						return true;						
					}else if ( (point = getIntersectionPoint(2, userLine) ) != null) {
						setUserLine(point.getX(), point.getY());
						return true;
					}else if ( (point = getIntersectionPoint(4, userLine) ) != null) {
						setUserLine(point.getX(), point.getY());
						return true;
					}else 
						return false;

			case 4:
				if ( (point = getIntersectionPoint(1, userLine))!= null) {
						setUserLine(point.getX(), point.getY());
						return true;						
					}else if ( (point = getIntersectionPoint(2, userLine) ) != null) {
						setUserLine(point.getX(), point.getY());
						return true;
					}else if ( (point = getIntersectionPoint(3, userLine) ) != null) {
						setUserLine(point.getX(), point.getY());
						return true;
					}else 
						return false;

			default:
				return false;
		}
	
	}

	public boolean intersectionCheck(double x, double y) {
		Point2D.Double point;
		Line2D l;
		int i;
		switch (first) {

			case 0: 
				return false;
				
			
			case 1:
				userLine.setLine(userLine.getX1(), userLine.getY1(),x, y);	
					//for each line in arrayList if userLine.intersectsLine(side.get(i))		
				for (i = 0; i < leftSide.size(); i ++) {
					l = leftSide.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
						isUserLineDrawn = true;	
						return true;
					}
				}
					
				
				for (i = 0; i < topCap.size(); i ++) {
					l = topCap.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}
				
				for (i = 0; i < bottomCap.size(); i ++) {
					l = bottomCap.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}

				if (lineHitDetection(x,y)) {
					return true;
				} 

				isUserLineDrawn = false;
				return false;	
				

			case 2:
				userLine.setLine(userLine.getX1(), userLine.getY1(),x, y);	
						
				for (i = 0; i < rightSide.size(); i ++) {
					l = rightSide.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}
				
				for (i = 0; i < topCap.size(); i ++) {
					l = topCap.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}
				
				for (i = 0; i < bottomCap.size(); i ++) {
					l = bottomCap.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}
			
				if (lineHitDetection(x,y)) {
					return true;
				} 

				isUserLineDrawn = false;
				return false;	
				
				
			case 3:

				userLine.setLine(userLine.getX1(), userLine.getY1(),x, y);	
						
				for (i = 0; i < rightSide.size(); i ++) {
					l = rightSide.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}
				
				for (i = 0; i < leftSide.size(); i ++) {
					l = leftSide.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}
				
				for (i = 0; i < bottomCap.size(); i ++) {
					l = bottomCap.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}

				if (lineHitDetection(x,y)) {
					return true;
				} 
				
				isUserLineDrawn = false;
				return false;	
				
				

			case 4:
				
				userLine.setLine(userLine.getX1(), userLine.getY1(),x, y);	
						
				for (i = 0; i < leftSide.size(); i ++) {
					l = leftSide.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}
				
				for (i = 0; i < topCap.size(); i ++) {
					l = topCap.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}
				
				for (i = 0; i < rightSide.size(); i ++) {
					l = rightSide.get(i);
					if (userLine.intersectsLine(l)) {
						point = getIntersectionPoint(l, userLine);
						if (point != null) {
							userLine.setLine(userLine.getX1(), userLine.getY1(), point.getX(), point.getY());
							
						}
					isUserLineDrawn = true;	
					return true;
					}
				}

				if (lineHitDetection(x,y)) {
					return true;
				} 

				isUserLineDrawn = false;
				return false;	
				
				
			default:
				isUserLineDrawn = false;
				return false;
			
		}
		
			
	}

	public int getFirst() {
		return first;
	}


	private void extendLine() {
		double x, y, len, factor;
		Point2D p;
		x = userLine.getX2() - userLine.getX1();
		y = userLine.getY2() - userLine.getY1();
		len = Math.sqrt(x*x + y*y);
		factor = len*LINE_EXT;
		len = factor / len;
		x = userLine.getX1() + (userLine.getX2() - userLine.getX1()) * len;
		y = userLine.getY1() + (userLine.getY2() - userLine.getY1()) * len;
		setUserLine(x,y);
		//setUserLine( (Math.sin(angle) * (length*2)) + userLine.getX1(), (Math.cos(angle) * (length*2)) + userLine.getY1() );

	}
	
	
	private Point2D.Double getIntersectionPoint(Line2D line1, Line2D line2) {
   		 if (! line1.intersectsLine(line2) ) return null;
     			
			double px = line1.getX1(),
            		py = line1.getY1(),
            		rx = line1.getX2()-px,
            		ry = line1.getY2()-py;

      			double qx = line2.getX1(),
            		qy = line2.getY1(),
            		sx = line2.getX2()-qx,
            		sy = line2.getY2()-qy;
 
     			double det = sx*ry - sy*rx;
      			if (det == 0) {
        			return null;
      			} else {
        			double z = (sx*(qy-py)+sy*(px-qx))/det;
        			if (z==0 ||  z==1) 
					return null;  // intersection at end point!
      				return new Point2D.Double((px+z*rx),(py+z*ry));
     			 }

 	} 

	public double getUserLineLength() {
		if (isUserLineDrawn) {
			double x = Math.abs(userLine.getX2() - userLine.getX1());
			double y = Math.abs(userLine.getY2() - userLine.getY1());
			return Math.sqrt(x*x + y*y); 
		}else
			return 0.0;
	}


	private Point2D.Double getIntersectionPoint(int whichSide, Line2D line2) {
   		int i;
		Line2D line1;
		switch (whichSide) {
		//Can probably use SideHitDetection?
			case 1:
				
				for (i = 0; i < rightSide.size(); i++) {
					line1 = rightSide.get(i);				
					if (line1.intersectsLine(line2) ) {
						//TODO: could also say: getIntersectionPoint(line1, line2);     						
						double px = line1.getX1(),
            					py = line1.getY1(),
            					rx = line1.getX2()-px,
            					ry = line1.getY2()-py;

      						double qx = line2.getX1(),
            					qy = line2.getY1(),
            					sx = line2.getX2()-qx,
            					sy = line2.getY2()-qy;
 
     						double det = sx*ry - sy*rx;
      						if (det == 0) {
        						return null;
      						} else {
        						double z = (sx*(qy-py)+sy*(px-qx))/det;
        						if (z==0 ||  z==1) 
								return null;  // intersection at end point!
      							return new Point2D.Double((px+z*rx),(py+z*ry));
     						 }
					}

 				} 
				return null; // none of the lines intersected.
				
			
			case 2:

				for (i = 0; i < leftSide.size(); i++) {	
					line1 = leftSide.get(i);		
					if (line1.intersectsLine(line2) ) {
     						double px = line1.getX1(),
            					py = line1.getY1(),
            					rx = line1.getX2()-px,
            					ry = line1.getY2()-py;

      						double qx = line2.getX1(),
            					qy = line2.getY1(),
            					sx = line2.getX2()-qx,
            					sy = line2.getY2()-qy;
 
     						double det = sx*ry - sy*rx;
      						if (det == 0) {
        						return null;
      						} else {
        						double z = (sx*(qy-py)+sy*(px-qx))/det;
        						if (z==0 ||  z==1) 
								return null;  // intersection at end point!
      							return new Point2D.Double((px+z*rx),(py+z*ry));
     						 }
					}

 				} 
				return null; // none of the lines intersected.
				

			case 3:
		
				for (i = 0; i < topCap.size(); i++) {	
					line1 = topCap.get(i);		
					if (line1.intersectsLine(line2) ) {
     						double px = line1.getX1(),
            					py = line1.getY1(),
            					rx = line1.getX2()-px,
            					ry = line1.getY2()-py;

      						double qx = line2.getX1(),
            					qy = line2.getY1(),
            					sx = line2.getX2()-qx,
            					sy = line2.getY2()-qy;
 
     						double det = sx*ry - sy*rx;
      						if (det == 0) {
        						return null;
      						} else {
        						double z = (sx*(qy-py)+sy*(px-qx))/det;
        						if (z==0 ||  z==1) 
								return null;  // intersection at end point!
      							return new Point2D.Double((px+z*rx),(py+z*ry));
     						 }
					}

 				} 
				return null; // none of the lines intersected.
				

			case 4:

				for (i = 0; i < bottomCap.size(); i++) {	
					line1 = bottomCap.get(i);		
					if (line1.intersectsLine(line2) ) {
     						double px = line1.getX1(),
            					py = line1.getY1(),
            					rx = line1.getX2()-px,
            					ry = line1.getY2()-py;

      						double qx = line2.getX1(),
            					qy = line2.getY1(),
            					sx = line2.getX2()-qx,
            					sy = line2.getY2()-qy;
 
     						double det = sx*ry - sy*rx;
      						if (det == 0) {
        						return null;
      						} else {
        						double z = (sx*(qy-py)+sy*(px-qx))/det;
        						if (z==0 ||  z==1) 
								return null;  // intersection at end point!
      							return new Point2D.Double((px+z*rx),(py+z*ry));
     						 }
					}

 				} 
				return null; // none of the lines intersected.
				

			default:
				return null;
				
			}
	}
				

	private void showAngleDebugInfo() {
		if (DEBUG_ON) {
			System.out.println((Math.atan2(userLine.getY1() - userLine.getY2(), userLine.getX2() - userLine.getX1()))*(180/Math.PI));
			System.out.println(getAngle());
		}
	}

/*
	public static void main(String[] args) {
		LineManager l = new LineManager(new Line2D.Double(181, 213.0, 351.0, 503.0), new Line2D.Double(437.0, 70.0, 602.0, 359.0));
		System.out.println(l.getUserLine().getX1() + ", " + l.getUserLine().getY1());
		System.out.println(l.getLineTwo().getX1() + ", " + l.getLineTwo().getY1());
		double x, y;
		x = 181;
		y = 213.0;
		System.out.println(l.lineHitDetection(x,y));
		x = 500.0;
		y = 381.0;
		System.out.println("First: " + l.getFirst());
		System.out.println(l.getUserLine().getX1() + ", " + l.getUserLine().getY1());
		System.out.println(l.intersectionCheck(x,y));
		System.out.println(l.getUserLine().getX1() + ", " + l.getUserLine().getY1() + ", " + l.getUserLine().getX2() + ", " + l.getUserLine().getY2());

	
	}
*/
}

