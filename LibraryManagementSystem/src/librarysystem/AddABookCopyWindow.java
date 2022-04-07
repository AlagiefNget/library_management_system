package librarysystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddABookCopyWindow extends JFrame implements LibWindow {
	  public static final AddABookCopyWindow INSTANCE = new AddABookCopyWindow();
		
		private boolean isInitialized = false;
		
		private JPanel mainPanel;
		private JPanel upperHalf;
		private JPanel middleHalf;
		private JPanel lowerHalf;
		private JPanel container;
		
		private JPanel topPanel;
		private JPanel middlePanel;
		private JPanel lowerPanel;
		private JPanel outerMiddle;
		private JPanel rightTextPanel;
		
		private JTextField firstNameField;
		private JTextField lastNameField;
		private JTextField titleField;
		private JLabel label;
		private JButton addBookCopyButton;
		
		
		
		
		public boolean isInitialized() {
			return isInitialized;
		}
		public void isInitialized(boolean val) {
			isInitialized = val;
		}
		private JTextField messageBar = new JTextField();
		public void clear() {
			messageBar.setText("");
		}
		
		/* This class is a singleton */
	    private AddABookCopyWindow () {}
	    
	    public void init() {     		
	    		mainPanel = new JPanel();
	    		defineUpperHalf();
	    		defineMiddleHalf();
	    		defineOuterMiddle();
	    		defineLowerHalf();
	    		BorderLayout bl = new BorderLayout();
	    		bl.setVgap(30);
	    		mainPanel.setLayout(bl);
	    					
	    		mainPanel.add(upperHalf, BorderLayout.NORTH);
	    		mainPanel.add(outerMiddle);
	    		mainPanel.add(middleHalf, BorderLayout.CENTER);
	    		mainPanel.add(lowerHalf, BorderLayout.SOUTH);
	    		getContentPane().add(mainPanel);
	    		isInitialized(true);
	    		pack();
	    		//setSize(660, 500);

	    	
	    }
	    private void defineUpperHalf() {
	    		
	    		upperHalf = new JPanel();
	    		upperHalf.setLayout(new BorderLayout());
	    		defineTopPanel();
	    		defineMiddlePanel();
	    		defineLowerPanel();
	    		upperHalf.add(topPanel, BorderLayout.NORTH);
	    		upperHalf.add(middlePanel, BorderLayout.CENTER);
	    		upperHalf.add(lowerPanel, BorderLayout.SOUTH);
	    		
	    	}
	    	private void defineMiddleHalf() {
	    		middleHalf = new JPanel();
	    		middleHalf.setLayout(new BorderLayout());
	    		JSeparator s = new JSeparator();
	    		s.setOrientation(SwingConstants.HORIZONTAL);
	    		//middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
	    		middleHalf.add(s, BorderLayout.SOUTH);
	    		
	    	}
	    	private void defineLowerHalf() {

	    		lowerHalf = new JPanel();
	    		lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));
	    		
	    		JButton backButton = new JButton("<= Back to Main");
	    		addBackButtonListener(backButton);
	    		lowerHalf.add(backButton);
	    		
	    	}
	    	private void defineTopPanel() {
	    		topPanel = new JPanel();
	    		JPanel intPanel = new JPanel(new BorderLayout());
	    		intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
	    		JLabel loginLabel = new JLabel("Login");
	    		Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
	    		intPanel.add(loginLabel, BorderLayout.CENTER);
	    		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	    		topPanel.add(intPanel);
	    		
	    	}
	    	
	    	
	    	
	    	private void defineMiddlePanel() {
	    		middlePanel=new JPanel();
	    		middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
	    	//	middlePanel.add(outerMiddle);
	    	}
	    	private void defineLowerPanel() {
	    		lowerPanel = new JPanel();
	    		addBookCopyButton = new JButton("Add Book Copy");
	    		addBookCopyButtonListener(addBookCopyButton);
	    		lowerPanel.add(addBookCopyButton);
	    	}
	    	public void defineOuterMiddle() {
	    		outerMiddle = new JPanel();
	    		outerMiddle.setLayout(new BorderLayout());

	    		// this portion sets up the left and right panels
	    		JPanel middlePanel = new JPanel();
	    		FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
	    		middlePanel.setLayout(fl);
	    		JPanel leftPanel = new JPanel();
	    		JPanel rightPanel = new JPanel();
	    		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
	    		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

	    		JLabel authFirstNameLabel = new JLabel("Author First Name");
	    		JLabel authLastNameLabel = new JLabel("Author Last Name");
	    		JLabel titleLabel = new JLabel("Book Title");

	    		firstNameField = new JTextField(10);
	    		lastNameField = new JTextField(10);
	    		titleField = new JTextField(10);

	    		leftPanel.add(authFirstNameLabel);
	    		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
	    		leftPanel.add(authLastNameLabel);
	    		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
	    		leftPanel.add(titleLabel);

	    		rightPanel.add(firstNameField);
	    		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
	    		rightPanel.add(lastNameField);
	    		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
	    		rightPanel.add(titleField);

	    		middlePanel.add(leftPanel);
	    		middlePanel.add(rightPanel);
	    		outerMiddle.add(middlePanel, BorderLayout.NORTH);

	    		// this portion adds buttons to the bottom
	    		JButton addBookButton = new JButton("Add Book");
	    		addBookCopyButtonListener(addBookButton);
	    		JPanel addBookButtonPanel = new JPanel();
	    		addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
	    		addBookButtonPanel.add(addBookButton);
	    		outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);

	    	}
	    	private void addBackButtonListener(JButton butn) {
	    		butn.addActionListener(evt -> {
	    			LibrarySystem.hideAllWindows();
	    			LibrarySystem.INSTANCE.setVisible(true);
	    		});
	    	}
	    	
	    	private void addBookCopyButtonListener(JButton butn) {
	    		butn.addActionListener(evt -> {
	    			JOptionPane.showMessageDialog(this,"Successful added Book");
	    				
	    		});
	    	}
		
	        
	    

}
