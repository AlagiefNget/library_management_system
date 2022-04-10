package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import business.Address;
import business.Author;
import business.BookException;
import business.ControllerInterface;
import business.SystemController;

public class AddAuthorsToBookWindow extends JFrame implements LibWindow {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final AddAuthorsToBookWindow INSTANCE = new AddAuthorsToBookWindow();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel topPanel;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel outerMiddle;


	private JTextField firstNameField;
	private JTextField lastNameField;
	private JTextField phoneField;
	
	private JTextField cityField;
	private JTextField stateField;
	private JTextField zipField;
	private JTextField streetField;
	private JTextArea bioField;
	
	private JTable table;
	private JScrollPane scrollPane;
	private CustomTableModel model;
	private final String[] DEFAULT_COLUMN_HEADERS 
	   = {"First Name", "Last Name", "Phone"};
	
    private final float [] COL_WIDTH_PROPORTIONS =
    	{0.35f, 0.35f, 0.3f};
    
	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 480;
	private static final int TABLE_WIDTH = (int) (0.75 * SCREEN_WIDTH);
    private static final int DEFAULT_TABLE_HEIGHT = (int) (0.25 * SCREEN_HEIGHT);
    private List<Author> authors = new ArrayList<Author>();
    List<String[]> data = new ArrayList<>();
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
	private AddAuthorsToBookWindow() {
	
	}

	public void init() {
		mainPanel = new JPanel();
		defineTopPanel();
		defineOuterMiddle();
		defineMiddleHalf();
		defineLowerHalf();
	//	defineLowerRightHalf();
		BorderLayout bl = new BorderLayout();
		bl.setVgap(30);
		mainPanel.setLayout(bl);

		
		mainPanel.add(topPanel, BorderLayout.NORTH);
		mainPanel.add(middleHalf, BorderLayout.CENTER);
		mainPanel.add(lowerHalf, BorderLayout.SOUTH);
	//	mainPanel.add(lowerRightHalf, BorderLayout.SOUTH);
		getContentPane().add(mainPanel);
		isInitialized(true);
		pack();

	}

	private void defineTopPanel() {
		topPanel = new JPanel();
		createTableAndTablePane();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(scrollPane);		
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
		lowerHalf.setLayout(new FlowLayout(FlowLayout.CENTER));

		JButton backButton = new JButton("<= Back to Main");
		addBackButtonListener(backButton);
		
		JButton addBookButton = new JButton("Save Book");
		saveBookButtonListener(addBookButton);
		
		lowerHalf.add(backButton);
		lowerHalf.add(addBookButton);

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


		JLabel authFirstNameLabel = new JLabel("First Name");
		JLabel authLastNameLabel = new JLabel("Last Name");
		JLabel phoneLabel = new JLabel("Phone Number");
		
		JLabel streetLabel = new JLabel("Street");
		JLabel cityLabel = new JLabel("City");
		JLabel stateLabel = new JLabel("State");
		JLabel zipLabel = new JLabel("Zip");
		JLabel bioLabel = new JLabel("Bio");
		
		firstNameField = new JTextField(10);
		lastNameField = new JTextField(10);
		phoneField = new JTextField(10);
		
		cityField = new JTextField(10);
		stateField = new JTextField(10);
		streetField = new JTextField(10);
		zipField = new JTextField(10);
		bioField = new JTextArea(2,5);

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
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(bioLabel);

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
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(bioField);
		
		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		outerMiddle.add(middlePanel, BorderLayout.NORTH);

		// this portion adds buttons to the bottom
		JButton addAuthorButton = new JButton("Add Member");
		addAuthorButtonListener(addAuthorButton);
		JPanel addBookButtonPanel = new JPanel();
		addBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		addBookButtonPanel.add(addAuthorButton);
		outerMiddle.add(addBookButtonPanel, BorderLayout.CENTER);

	}
	

	private void createTableAndTablePane() {
		updateModel(); 
		table = new JTable(model);
		createCustomColumns(table, TABLE_WIDTH,
	            COL_WIDTH_PROPORTIONS, DEFAULT_COLUMN_HEADERS);
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(
				new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
		scrollPane.getViewport().add(table);
	}
	private void createCustomColumns(JTable table, int width, float[] proportions,
			  String[] headers) {
			table.setAutoCreateColumnsFromModel(false);
	        int num = headers.length;
	        for(int i = 0; i < num; ++i) {
	            TableColumn column = new TableColumn(i);
	            column.setHeaderValue(headers[i]);
	            column.setMinWidth(Math.round(proportions[i]*width));
	            table.addColumn(column);
	        }
		}
	

	private void setValues(CustomTableModel model) {
		for(Author author:authors) {
			String[] row = {author.getFirstName(), author.getLastName(), author.getTelephone()};
			data.add(row);
		}
		model.setTableValues(data);
		
	}
	public void updateModel(List<String[]> list){
		if(model == null) {
			model = new CustomTableModel();
		}
		model.setTableValues(list);
	}
	
	private void updateModel() {
		List<String[]> theData = new ArrayList<String[]>();
		updateModel(theData);
	}
	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}
	

	private void addAuthorButtonListener(JButton butn) {
		butn.addActionListener(evt -> {

			String firstName = firstNameField.getText().trim();
			String lastName = lastNameField.getText().trim();
			String phone = phoneField.getText().trim();
			String city = cityField.getText().trim();
			String state = stateField.getText().trim();
			String street = stateField.getText().trim();
			String zip = zipField.getText().trim();
			String bio = bioField.getText().trim();
			
			if(firstName.length() == 0 || lastName.length() == 0 || phone.length() == 0 
					|| city.length() == 0 || state.length() == 0 || street.length() == 0|| zip.length() == 0) {
				JOptionPane.showMessageDialog(this,"All fields must be nonempty");
			}else {
				
				Address address = new Address(street, city, state, zip);
				Author author = new Author(firstName, lastName, phone, address, bio);
				authors.add(author);
				setValues(model);
				table.updateUI();	
			}

		});
	}
	
	private void saveBookButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			if(authors.size() == 0) {
				JOptionPane.showMessageDialog(this,"Atleast one author must be provided");
			}
			else {
				
				try {
					ControllerInterface controller = new SystemController();
					controller.addBook(AddBookWindow.INSTANCE.getbookIsbn(),AddBookWindow.INSTANCE.getbookTitle(),
							AddBookWindow.INSTANCE.getbookmaxCheckout(), authors);
					JOptionPane.showMessageDialog(this,"Book added Successfully");
					LibrarySystem.hideAllWindows();
					LibrarySystem.INSTANCE.init();
					LibrarySystem.INSTANCE.setVisible(true);
					setToInitial();
				} catch (BookException e) {
				//	e.printStackTrace();
					JOptionPane.showMessageDialog(this,"Book cannot be added,Check fields and Try again");
				}
			}

		});
	}
	private void setToInitial() {
		firstNameField.setText("");
		lastNameField.setText("");
		phoneField.setText("");
		cityField.setText("");
		streetField.setText("");
		stateField.setText("");
		zipField.setText("");
		bioField.setText("");
	}
	
	

}

