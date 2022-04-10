package librarysystem;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import business.CheckoutEntry;
import business.CheckoutRecord;
import business.SystemController;

public class CheckoutBookWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;

	public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel outerMiddle;
	private JPanel topPanel;
	
	private JTextField memberId;
	private JTextField bookIsbn;

	// JTable
	private JTable table;
	private JScrollPane scrollPane;
	private CustomTableModel model;
	private final String[] DEFAULT_COLUMN_HEADERS = { "Book Title", "Checkout Date", "Due Date" };
	List<String[]> data = new ArrayList<>();
	List<CheckoutEntry> checkoutEntry;
	
	private final float[] COL_WIDTH_PROPORTIONS = { 0.35f, 0.35f, 0.3f };

	private static final int SCREEN_WIDTH = 640;
	private static final int SCREEN_HEIGHT = 480;
	private static final int TABLE_WIDTH = (int) (0.75 * SCREEN_WIDTH);
	private static final int DEFAULT_TABLE_HEIGHT = (int) (0.25 * SCREEN_HEIGHT);

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
	private CheckoutBookWindow() {

	}

	public void init() {
		mainPanel = new JPanel();
		defineOuterMiddle();
		defineMiddleHalf();
		defineLowerHalf();
		defineTablePanel();
		BorderLayout bl = new BorderLayout();
		bl.setVgap(30);
		mainPanel.setLayout(bl);

//		mainPanel.add(middleHalf, BorderLayout.CENTER);
		mainPanel.add(middleHalf, BorderLayout.NORTH);
		mainPanel.add(topPanel, BorderLayout.CENTER);
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
	
	private void defineTablePanel() {
		topPanel = new JPanel();
		createTableAndTablePane();
		topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPanel.add(scrollPane);		
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

		JLabel userIdLabel = new JLabel("Member ID");
		JLabel bookIsbnLabel = new JLabel("Book ISBN");

		memberId = new JTextField(10);
		bookIsbn = new JTextField(10);

		leftPanel.add(userIdLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));
		leftPanel.add(bookIsbnLabel);
		leftPanel.add(Box.createRigidArea(new Dimension(0, 12)));

		rightPanel.add(memberId);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		rightPanel.add(bookIsbn);
		rightPanel.add(Box.createRigidArea(new Dimension(0, 8)));

		middlePanel.add(leftPanel);
		middlePanel.add(rightPanel);
		outerMiddle.add(middlePanel, BorderLayout.NORTH);

		JButton checkoutBookButton = new JButton("Checkout");
		checkoutButtonMemberListener(checkoutBookButton);

		// this portion adds buttons to the bottom

		JPanel chekoutBookButtonPanel = new JPanel();
		chekoutBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		chekoutBookButtonPanel.add(checkoutBookButton);
		outerMiddle.add(chekoutBookButtonPanel, BorderLayout.CENTER);

	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	private void checkoutButtonMemberListener(JButton butn) {
		butn.addActionListener(evt -> {
			String memId = memberId.getText();
			String isbn = bookIsbn.getText();
			if (memId.isEmpty() || isbn.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fields need to be filled!");
				return;
			}

			SystemController controller = new SystemController();
			CheckoutRecord checkoutRecords = controller.checkoutBook(memId, isbn);
			if (checkoutRecords != null) {
				memberId.setText("");
				bookIsbn.setText("");
				checkoutEntry = checkoutRecords.getCheckoutEntries();
				setValues(model);
				table.updateUI();
				JOptionPane.showMessageDialog(null, "Book checkout successful!");
			} else {
				JOptionPane.showMessageDialog(null, "Book checkout failed!");
			}
		});
	}

	private void createTableAndTablePane() {
		updateModel();
		table = new JTable(model);
		createCustomColumns(table, TABLE_WIDTH, COL_WIDTH_PROPORTIONS, DEFAULT_COLUMN_HEADERS);
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(TABLE_WIDTH, DEFAULT_TABLE_HEIGHT));
		scrollPane.getViewport().add(table);
	}

	public void updateModel(List<String[]> list) {
		if (model == null) {
			model = new CustomTableModel();
		}
		model.setTableValues(list);
	}

	private void updateModel() {
		List<String[]> theData = new ArrayList<String[]>();
		updateModel(theData);
	}

	private void createCustomColumns(JTable table, int width, float[] proportions, String[] headers) {
		table.setAutoCreateColumnsFromModel(false);
		int num = headers.length;
		for (int i = 0; i < num; ++i) {
			TableColumn column = new TableColumn(i);
			column.setHeaderValue(headers[i]);
			column.setMinWidth(Math.round(proportions[i] * width));
			table.addColumn(column);
		}
	}
	
	private void setValues(CustomTableModel model) {
		for(CheckoutEntry entry : checkoutEntry) {
			String[] row = {entry.getBookCopy().getBook().getTitle(), formatDate(entry.getCheckoutDate()), formatDate(entry.getDueDate())};
			data.add(row);
		}
		model.setTableValues(data);
	}
	
	private String formatDate(Date date) {
		String pattern = "MM-dd-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		return simpleDateFormat.format(date);
	}

}
