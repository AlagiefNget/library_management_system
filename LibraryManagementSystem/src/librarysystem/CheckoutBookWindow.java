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

import business.SystemController;

public class CheckoutBookWindow extends JFrame implements LibWindow {
	private static final long serialVersionUID = 1L;

	public static final CheckoutBookWindow INSTANCE = new CheckoutBookWindow();

	private boolean isInitialized = false;

	private JPanel mainPanel;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel outerMiddle;

	private JTextField memberId;
	private JTextField bookIsbn;

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

		JButton searchBookAndMemberButton = new JButton("Checkout");
		searchButtonMemberListener(searchBookAndMemberButton);

		// this portion adds buttons to the bottom

		JPanel chekoutBookButtonPanel = new JPanel();
		chekoutBookButtonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		chekoutBookButtonPanel.add(searchBookAndMemberButton);
		outerMiddle.add(chekoutBookButtonPanel, BorderLayout.CENTER);

	}

	private void addBackButtonListener(JButton butn) {
		butn.addActionListener(evt -> {
			LibrarySystem.hideAllWindows();
			LibrarySystem.INSTANCE.setVisible(true);
		});
	}

	private void searchButtonMemberListener(JButton butn) {
		butn.addActionListener(evt -> {
			String memId = memberId.getText();
			String isbn = bookIsbn.getText();
			if (memId.isEmpty() || isbn.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Fields need to be filled!");
			}

			SystemController controller = new SystemController();
			boolean isDone = controller.checkoutBook(memId, isbn);

			if (isDone) {
				JOptionPane.showMessageDialog(null, "Book check successful!");
			} else {
				JOptionPane.showMessageDialog(null, "Book checkout failed!");
			}
		});
	}

}
