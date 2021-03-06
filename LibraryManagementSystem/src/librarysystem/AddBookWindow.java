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
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AddBookWindow extends JFrame implements LibWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final AddBookWindow INSTANCE = new AddBookWindow();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel outerMiddle;
	 
	private JTextField isbnField;
	private JTextField titleField;
	
	private JRadioButton sevenDays;
    private JRadioButton twentyOneDays;
    
    private static int checkOutLength = 0;
    
	private static String bookIsbn, bookTitle;
	private static int bookmaxCheckout;
	
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
	private AddBookWindow() {
	
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

		JLabel isbnLabel = new JLabel("ISBN");
		JLabel titleLabel = new JLabel("Book Title");
		JLabel maxCheckOutLabel = new JLabel("Max Checkout Length");

		isbnField = new JTextField(10);
		titleField = new JTextField(10);
		sevenDays = new JRadioButton();
		
		twentyOneDays = new JRadioButton();
		
		sevenDays.setText("Seven");
		twentyOneDays.setText("Twenty-One");
		sevenDays.addActionListener(e->{
			checkOutLength=7;
			twentyOneDays.setSelected(false);
			});
		twentyOneDays.addActionListener(e->{
			checkOutLength =21;
			sevenDays.setSelected(false);
		});

		
		leftPanel.add(isbnLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(titleLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(maxCheckOutLabel);

		rightPanel.add(isbnField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(titleField);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(sevenDays);
		rightPanel.add(twentyOneDays);

		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		outerMiddle.add(middlePanel, BorderLayout.NORTH);

		// this portion adds buttons to the bottom
		JButton addBookButton = new JButton("Next Step");
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
			String isbn = isbnField.getText().trim();
			String title = titleField.getText().trim();
			System.out.println(checkOutLength);

			if(isbn.length() == 0  || title.length() == 0 || checkOutLength ==0) {
				JOptionPane.showMessageDialog(this,"All fields must be nonempty");
			}else {
				bookIsbn = isbn;
				bookTitle = title;
				bookmaxCheckout = checkOutLength;
				isbnField.setText("");
				titleField.setText("");
				checkOutLength = 0;
				sevenDays.setSelected(false);
				twentyOneDays.setSelected(false);
				LibrarySystem.hideAllWindows();
				AddAuthorsToBookWindow.INSTANCE.init();
				Util.centerFrameOnDesktop(AddAuthorsToBookWindow.INSTANCE);
				AddAuthorsToBookWindow.INSTANCE.setVisible(true);
				
			}

		});
	}
	
	public static void setToInitial() {
		bookIsbn = null;
		bookTitle = null;
		bookmaxCheckout = 7;
		
		
	}

	public static String getbookIsbn() {
		return bookIsbn;
	}
	
	public static String getbookTitle() {
		return bookTitle;
	}
	public static int getbookmaxCheckout() {
		return bookmaxCheckout;
	}
}
