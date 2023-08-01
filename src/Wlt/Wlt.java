package Wlt;
import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.StringTokenizer;
import java.awt.Toolkit;
import java.net.URL;
import java.lang.StringBuilder;

public class Wlt extends JFrame  {
	
	final String SETTINGS_FILENAME = "../settings/settings.txt";
	private boolean RESIZEABLE = true;
	
	public static int NUM_SLIDES;
	public static int NUM_NON_TRIAL_SCREENS = 5;
	public static String NEWLINE = System.getProperty("line.separator");
	public static String BUTTON_MSG = "Click WHEN FINISHED";
	public static double LINE_DETECT = 20;
	public static String NO_SELECTION_ERROR = "Please make a selection.";
	public static String NOT_FINISHED_ERROR = "Please finish extending the line accross the container";
	public static boolean ALLOW_NEG_ANGLE = true;
	public static String IMG_DIR = "../img";
	public static String HTML_DIR = "../html";
	public static int MIDPOINT = 10;

	WltPanel panel;
	JPanel mainPanel, subPanel;
	JButton b1;
	JLabel label1;
	JLabel label2;
	JTextField tempC;
	
	public Wlt() {
		  
		String s;
	       	setTitle("WLT");
		setAlwaysOnTop(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		while ( (s = (String)JOptionPane.showInputDialog(this,"Enter the subject number: ")) != null && !(s.matches("\\d+")) ) {
			JOptionPane.showMessageDialog(this, "Please enter digits");
		}
	
		if (s == null) {
			System.exit(0);
		}

		try {
			loadSettings(this.getClass().getResourceAsStream(SETTINGS_FILENAME));
		}catch (NullPointerException e) {

				JOptionPane.showMessageDialog(this, "Error Loading Settings from File: "  +  SETTINGS_FILENAME + " - using defaults");
		}
				
		
		mainPanel = new JPanel(new BorderLayout());
		subPanel = new JPanel();//new FlowLayout(FlowLayout.LEADING));
		panel = new WltPanel(new Participant(s));
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		panel.addMouseListener(panel);
		panel.addMouseMotionListener(panel);
		panel.addComponentListener(panel);
		mainPanel.add(panel, BorderLayout.CENTER);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(0,0,0,0));
		mainPanel.setBackground(Color.white);
		b1 = new JButton(Wlt.BUTTON_MSG);
		b1.setPreferredSize(new Dimension(275,50));
		b1.addActionListener(panel);
		subPanel.add(b1);	
		mainPanel.add(subPanel, BorderLayout.PAGE_END);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
		this.pack();
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setVisible(true);
		this.setResizable(RESIZEABLE);
		
	}

	 private void loadSettings(InputStream stream) {
		BufferedReader in;
		FileReader file;
		StringTokenizer tokens;
		
		int i;
		String line, currToken, sNum;
	
			try {
				
				in = new BufferedReader(new InputStreamReader(stream));
				line = null;
						
				while ((line = in.readLine()) != null) {
					tokens = new StringTokenizer(line, "=");
					currToken = tokens.nextToken();
					if (currToken.equals("BUTTON_MSG")) {
						String result = "";
						while (tokens.hasMoreTokens()) {
							currToken = tokens.nextToken();
							result = result + currToken;
						}			
						this.BUTTON_MSG = result;					
					}else if (currToken.equals("MIDPOINT")) {
						currToken = tokens.nextToken();
						int mid = Integer.parseInt(currToken);
						this.MIDPOINT = mid>2 ? mid:this.MIDPOINT;
					}else if (currToken.equals("IMG_DIRECTORY")) {
						currToken = tokens.nextToken();
						this.IMG_DIR = currToken;
					}else if (currToken.equals("LINE_DETECT")) {
						currToken = tokens.nextToken();
						this.LINE_DETECT = Double.parseDouble(currToken);
					}else if (currToken.equals("NOT_FINISHED_ERROR")) {
						String result = "";
						while (tokens.hasMoreTokens()) {
							currToken = tokens.nextToken();
							result = result + currToken;
						}			
						this.NOT_FINISHED_ERROR = result;
					}else if (currToken.equals("NUM_NON_TRIAL_SCREENS")) {
							currToken = tokens.nextToken();
							this.NUM_NON_TRIAL_SCREENS = Integer.parseInt(currToken);
					}else if (currToken.equals("RESIZEABLE")) {
							currToken = tokens.nextToken();
							this.RESIZEABLE = currToken.equals("true") ? true:false;
					}else if (currToken.equals("HTML_DIR")) {
							currToken = tokens.nextToken();
							this.HTML_DIR = currToken;
					}else if (currToken.equals("NO_SELECTION_ERROR") ) {
							String result = "";
						while (tokens.hasMoreTokens()) {
							currToken = tokens.nextToken();
							result = result + currToken;
						}			
						this.NO_SELECTION_ERROR = result;
					}else if (currToken.equals("ALLOW_NEG_ANGLE")) {
							currToken = tokens.nextToken();
							this.ALLOW_NEG_ANGLE = currToken.equals("true") ? true:false;	
					}					
					line = null;
				}//end while
			
				in.close();
				
			}catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Error Loading Settings - using defaults");
				
		}
		 
			}	

	public static void main(String[] args) {
		 SwingUtilities.invokeLater(new Runnable() {
     			 public void run() {
				Wlt wlt = new Wlt();
	     			
    			  }
   		 });
  	}

		

}

