
package Wlt;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
//import javax.swing.event.*;
//import javax.imageio.*;
import java.awt.image.*;

public class UserLine extends JComponent{

Line2D line;

public UserLine(Line2D line) {
	this.setOpaque(true);
	this.line = line;
}

public UserLine(double x, double y, double x1, double y1) {
	line = new Line2D.Double(x,y,x1,y1);
}

public void updateLineEnd(double x, double y) {
	line.setLine(line.getX1(),line.getY1(),x,y);	
}

public void updateLineStart(double x, double y) {
	line.setLine(x,y,line.getX2(), line.getY2());
}

public void updateLine(Line2D line) {
	this.line = line;
}

public void drawUserLine(Graphics g) {
	Graphics2D g2 = (Graphics2D) g;
	g2.draw(line);
	g2.dispose();
}

 protected void paintComponent(Graphics g) {

        final Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        //final int width = getWidth();
       //final int height = getHeight();
       //g2.setPaint(new GradientPaint(0, 0, Color.WHITE, width, height, Color.YELLOW));
        //f//inal = new Line2D.Double(line.getX1(), line.getY1(), line.getX2(), line.getY2());
        g2.setColor(Color.BLACK);
        g2.draw(line);

    }

}
