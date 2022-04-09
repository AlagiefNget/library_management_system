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

import business.Book;
import business.SystemController;

public class AddABookCopyWindow extends JFrame implements LibWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final AddABookCopyWindow INSTANCE = new AddABookCopyWindow();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel outerMiddle;
	
	private JTextField copyNumber;
	private JTextField searchField;
	
	private Book book;
	private JButton searchButton;
	private JButton addBookButton;
	private JLabel bookFoundLabel;
	
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
	private AddABookCopyWindow() {
	
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

		JPanel searchPanel = new JPanel();
		
		JLabel copyLabel = new JLabel("Number of Copies");
		JLabel searchLabel = new JLabel("Enter Book ISBN");
		bookFoundLabel = new JLabel("");

		copyNumber = new JTextField(10);
		searchField = new JTextField(20); 
		searchButton = new JButton("Search");
		searchButtonListener(searchButton);
		
//		leftPanel.add(searchLabel);
//		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));

		searchPanel.add(searchLabel);
		searchPanel.add(searchField);
		searchPanel.add(searchButton);
		
		rightPanel.add(Box.createRigidArea(new Dimension(0, 4)));
		rightPanel.add(Box.createRigidArea(new Dimension(0, 4)));


		leftPanel.add(copyLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));

		rightPanel.add(copyNumber);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));

		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		
		outerMiddle.add(searchPanel, BorderLayout.NORTH);
		outerMiddle.add(middlePanel, BorderLayout.CENTER);

		// this portion adds buttons to the bottom
		addBookButton = new JButton("Add Copy");
		addBookButton.setEnabled(false);
		addBookCopyButtonListener(addBookButton);
		JPanel addBookButtonPanel = new JPanel();
		addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		addBookButtonPanel.add(addBookButton);
		outerMiddle.add(addBookButtonPanel, BorderLayout.SOUTH);

	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	private void addBookCopyButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			int numOfCopies = Integer.parseInt(copyNumber.getText());
			SystemController controller = new SystemController();
			controller.addCopies(book, numOfCopies);
			JOptionPane.showMessageDialog(this, "Successful added Book copies");
		});
	}
	
	private void searchButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			String isbn = searchField.getText();
			SystemController controller = new SystemController();
			book = controller.getBook(isbn);
			if(book != null) {
				JOptionPane.showMessageDialog(this, "Book found");
				bookFoundLabel.setText(book.getTitle());
				addBookButton.setEnabled(true);
			}
			else {
				JOptionPane.showMessageDialog(this, "Book not found");
			}
		});
	}

}
