package librarysystem;


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddLibraryMemberWindows extends JFrame implements LibWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final AddLibraryMemberWindows INSTANCE = new AddLibraryMemberWindows();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel outerMiddle;
	
	private JTextField idField;
	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField phoneField;
	
	private JTextField cityField;
	private JTextField stateField;
	private JTextField zipField;
	private JTextField streetField;
	
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
	private AddLibraryMemberWindows() {
	
	}

	public void init() {
		mainPanel = new JPanel();
		defineOuterMiddle();
		defineMiddleHalf();
		defineLowerHalf();
		BorderLayout bl = new BorderLayout();
		bl.setVgap(30);
		mainPanel.setLayout(bl);

		mainPanel.add(middleHalf, BorderLayout.CENTER);
		mainPanel.add(lowerHalf, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized(true);
		pack();

	}

	private void defineMiddleHalf() {
		middleHalf = new JPanel();
		middleHalf.setLayout(new BorderLayout());
		JSeparator s = new JSeparator();
		s.setOrientation(SwingConstants.HORIZONTAL);
		middleHalf.add(outerMiddle, BorderLayout.NORTH);
		middleHalf.add(s, BorderLayout.SOUTH);

	}

	private void defineLowerHalf() {

		lowerHalf = new JPanel();
		lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton backButton = new JButton("<= Back to Main");
		addBackButtonListener(backButton);
		lowerHalf.add(backButton);

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

		JLabel idLabel = new JLabel("Member ID");
		JLabel authFirstNameLabel = new JLabel("First Name");
		JLabel authLastNameLabel = new JLabel("Last Name");
		JLabel phoneLabel = new JLabel("Pnone Number");
		
		JLabel streetLabel = new JLabel("Street");
		JLabel cityLabel = new JLabel("City");
		JLabel stateLabel = new JLabel("State");
		JLabel zipLabel = new JLabel("Zip");

		firstNameField = new JTextField(10);
		lastNameField = new JTextField(10);
		phoneField = new JTextField(10);
		idField = new JTextField(10);
		
		cityField = new JTextField(10);
		stateField = new JTextField(10);
		streetField = new JTextField(10);
		zipField = new JTextField(10);

		leftPanel.add(idLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(authFirstNameLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(authLastNameLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(phoneLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(streetLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(cityLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(stateLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(zipLabel);

		rightPanel.add(idField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(firstNameField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(lastNameField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(phoneField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(streetField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(cityField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(stateField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(zipField);

		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		outerMiddle.add(middlePanel, BorderLayout.NORTH);

		// this portion adds buttons to the bottom
		JButton addBookButton = new JButton("Add Member");
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
			JOptionPane.showMessageDialog(this, "Successful added Book");

		});
	}

}
