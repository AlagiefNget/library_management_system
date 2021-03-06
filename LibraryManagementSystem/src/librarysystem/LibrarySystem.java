package librarysystem;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import business.ControllerInterface;
import business.SystemController;
import dataaccess.Auth;

public class LibrarySystem extends JFrame implements LibWindow {
	private static final long serialVersionUID = -7888058519835867106L;
	
	ControllerInterface ci = new SystemController();
	public final static LibrarySystem INSTANCE = new LibrarySystem();
	JPanel mainPanel;
	JMenuBar menuBar;
	JMenu options;
	JMenuItem login, allBookIds, addBook, addMember, allMemberIds, addBookCopy, printCheckOutRecord, checkoutBook, logOut, checkOverDue;
	String pathToImage;
	private boolean isInitialized = false;

	private static LibWindow[] allWindows = { 
			LibrarySystem.INSTANCE, 
			LoginWindow.INSTANCE, 
			AllMemberIdsWindow.INSTANCE,
			AddBookWindow.INSTANCE,
			AddLibraryMemberWindows.INSTANCE,
			AllBookIdsWindow.INSTANCE, 
			AddABookCopyWindow.INSTANCE,
			CheckoutBookWindow.INSTANCE,
			PrintCheckoutWindow.INSTANCE,
			AddAuthorsToBookWindow.INSTANCE,
			CheckOverdueWindow.INSTANCE
		};

	public static void hideAllWindows() {

		for (LibWindow frame : allWindows) {
			frame.setVisible(false);

		}
	}

	private LibrarySystem() {
	}

	public void init() {
		formatContentPane();
		setPathToImage();
		insertSplashImage();

		createMenus();
		// pack();
		setSize(660, 500);
		isInitialized = true;
	}

	private void formatContentPane() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 1));
		getContentPane().add(mainPanel);
	}

	private void setPathToImage() {
		String currDirectory = System.getProperty("user.dir");
		pathToImage = currDirectory + "\\src\\librarysystem\\library.jpg";
	}

	private void insertSplashImage() {
		ImageIcon image = new ImageIcon(pathToImage);
		mainPanel.add(new JLabel(image));
	}

	private void createMenus() {
		menuBar = new JMenuBar();
		menuBar.setBorder(BorderFactory.createRaisedBevelBorder());
		addMenuItems();
		setJMenuBar(menuBar);
	}

	private void addMenuItems() {
		options = new JMenu("Options");
		menuBar.add(options);
		login = new JMenuItem("Login");
		login.addActionListener(new LoginListener());
		addBookCopy = new JMenuItem("Add Book Copy");
		addBookCopy.addActionListener(new AddBookCopyListener());
		allBookIds = new JMenuItem("All Book Ids");
		allBookIds.addActionListener(new AllBookIdsListener());
		allMemberIds = new JMenuItem("All Member Ids");
		allMemberIds.addActionListener(new AllMemberIdsListener());
		addBook = new JMenuItem("Add Book");
		addBook.addActionListener(new AddBookListener());
		addMember = new JMenuItem("Add Member");
		addMember.addActionListener(new AddMemberListener());
		checkoutBook = new JMenuItem("Checkout");
		checkoutBook.addActionListener(new CheckoutBookListener());
		printCheckOutRecord = new JMenuItem("Print Member Checkouts");
		printCheckOutRecord.addActionListener(new PrintCheckoutBookListener());
		checkOverDue = new JMenuItem("Overdue");
		checkOverDue.addActionListener(new BookCheckOverdueListener());
		
		logOut = new JMenuItem("Logout");
		logOut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SystemController.logout();
			}
		});
		
		JMenuItem[] notLogin = {login};
		JMenuItem[] librarianItems = {checkoutBook, allBookIds, allMemberIds, printCheckOutRecord, checkOverDue, logOut};
		JMenuItem[] adminItems = {allMemberIds, addMember, addBook, addBookCopy, logOut};
		JMenuItem[] bothLevelItems = {allBookIds, addBook, addMember, allMemberIds, addBookCopy,checkoutBook, printCheckOutRecord, checkOverDue, logOut};
		
		Auth auth = getUserAuthorizationLevel();		
		JMenuItem[] items = auth == Auth.LIBRARIAN ? librarianItems : auth == Auth.ADMIN ? adminItems : auth == Auth.BOTH ? bothLevelItems : notLogin;
		showUserOptions(items, options);
	}

	private void showUserOptions(JMenuItem[] items, JMenu options) {
		for(JMenuItem menuItem : items)
			options.add(menuItem);
	}
	
	class LoginListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			LoginWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(LoginWindow.INSTANCE);
			LoginWindow.INSTANCE.setVisible(true);

		}

	}

	class AddBookCopyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddABookCopyWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(AddABookCopyWindow.INSTANCE);
			AddABookCopyWindow.INSTANCE.setVisible(true);
		}

	}

	class AddBookListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddBookWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(AddBookWindow.INSTANCE);
			AddBookWindow.INSTANCE.setVisible(true);
		}

	}
	
	class AddMemberListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AddLibraryMemberWindows.INSTANCE.init();
			Util.centerFrameOnDesktop(AddLibraryMemberWindows.INSTANCE);
			AddLibraryMemberWindows.INSTANCE.setVisible(true);
		}

	}

	class AllBookIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			List<String> ids = ci.allBookIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for (String s : ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllBookIdsWindow.INSTANCE.setData(sb.toString());
			AllBookIdsWindow.INSTANCE.pack();
			// AllBookIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllBookIdsWindow.INSTANCE);
			AllBookIdsWindow.INSTANCE.setVisible(true);
		}

	}

	class AllMemberIdsListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			AllMemberIdsWindow.INSTANCE.init();
			AllMemberIdsWindow.INSTANCE.pack();
			AllMemberIdsWindow.INSTANCE.setVisible(true);

			LibrarySystem.hideAllWindows();
			AllBookIdsWindow.INSTANCE.init();

			List<String> ids = ci.allMemberIds();
			Collections.sort(ids);
			StringBuilder sb = new StringBuilder();
			for (String s : ids) {
				sb.append(s + "\n");
			}
			System.out.println(sb.toString());
			AllMemberIdsWindow.INSTANCE.setData(sb.toString());
			AllMemberIdsWindow.INSTANCE.pack();
			// AllMemberIdsWindow.INSTANCE.setSize(660,500);
			Util.centerFrameOnDesktop(AllMemberIdsWindow.INSTANCE);
			AllMemberIdsWindow.INSTANCE.setVisible(true);
		}

	}

	class CheckoutBookListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			CheckoutBookWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(CheckoutBookWindow.INSTANCE);
			CheckoutBookWindow.INSTANCE.setVisible(true);
		}

	}
	
	class PrintCheckoutBookListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			PrintCheckoutWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(PrintCheckoutWindow.INSTANCE);
			PrintCheckoutWindow.INSTANCE.setVisible(true);
		}

	}
	
	class BookCheckOverdueListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			LibrarySystem.hideAllWindows();
			CheckOverdueWindow.INSTANCE.init();
			Util.centerFrameOnDesktop(CheckOverdueWindow.INSTANCE);
			CheckOverdueWindow.INSTANCE.setVisible(true);
		}

	}
	
	@Override
	public boolean isInitialized() {
		return isInitialized;
	}

	@Override
	public void isInitialized(boolean val) {
		isInitialized = val;

	}
	
	private Auth getUserAuthorizationLevel() {
		return SystemController.currentAuth;
	}
}
