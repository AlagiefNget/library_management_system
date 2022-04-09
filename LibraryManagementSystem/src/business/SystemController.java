package business;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.JOptionPane;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.LibrarySystem;

public class SystemController implements ControllerInterface {
	public static Auth currentAuth = null;
	
	public void login(String id, String password) throws LoginException {
		DataAccess da = new DataAccessFacade();
		HashMap<String, User> map = da.readUserMap();
		if(!map.containsKey(id)) {
			throw new LoginException("ID " + id + " not found");
		}
		String passwordFound = map.get(id).getPassword();
		if(!passwordFound.equals(password)) {
			throw new LoginException("Password incorrect");
		}
		System.out.println(map.get(id));
		currentAuth = map.get(id).getAuthorization();
		
	}
	@Override
	public List<String> allMemberIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readMemberMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookIds() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readBooksMap().keySet());
		return retval;
	}
	
	@Override
	public List<String> allBookAuthors() {
		DataAccess da = new DataAccessFacade();
		List<String> retval = new ArrayList<>();
		retval.addAll(da.readAuthorMap().keySet());
		return retval;
	}
	public static void logout() {
		currentAuth = null;
		LibrarySystem.hideAllWindows();
		LibrarySystem.INSTANCE.init();
		LibrarySystem.INSTANCE.setVisible(true);
	}
	
	@Override
	public void addMember(String id, String firstname, String lastname, String phone, String city, String street,
			String state, String zip) throws AddMemberException {

		DataAccess da = new DataAccessFacade();
		Address address = new Address(street, city, state, zip);
		LibraryMember mem = new LibraryMember(id, firstname, lastname, phone,address);
		da.saveNewMember(mem);
		
	}
	@Override
	public void addBook(String isbn, String title, int maxCheckoutLength, List<Author> list) throws BookException {
		DataAccess da = new DataAccessFacade();
	//	List<Author> authors = new ArrayList<Author>();
		Book book = new Book(isbn, title, maxCheckoutLength, list);
		da.saveNewBook(book);
		
	}
	
	public LibraryMember getLibraryMember(String id) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> members = da.readMemberMap();
		return members.get(id);
	}
	
	public Book getBook(String isbn) {
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> books = da.readBooksMap();
		return books.get(isbn);
	}
	
	public Date calculateDueDate(int dateLong , Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, dateLong);
		return c.getTime();

	}

	public BookCopy checkAvailabilityAndSet(Book book) {	
		BookCopy nextAvailable = book.getNextAvailableCopy();
		if(nextAvailable == null) {
			JOptionPane.showMessageDialog(null, "Book copy does not available!");
		} else {
			nextAvailable.changeAvailability();
		}
		return nextAvailable;
	}
	
	public CheckoutRecord checkoutBook(String memId, String isbn) {
		Book book = getBook(isbn);
		if(book != null) {
			LibraryMember member = getLibraryMember(memId);
			if(member != null) {
				DataAccessFacade da = new DataAccessFacade();
				HashMap<String, LibraryMember> mems = da.readMemberMap();
				
				BookCopy givenBook = checkAvailabilityAndSet(book);
				CheckoutEntry ce = new CheckoutEntry(givenBook, new Date(),calculateDueDate(book.getMaxCheckoutLength(),new Date()));
				
				member.getCheckoutRecord().getCheckoutEntries().add(ce);
				mems.put(member.getMemberId(), member);
				da.saveMembers(mems);				
				
				return member.getCheckoutRecord();
			}
			else {
				JOptionPane.showMessageDialog(null, "Member with ID not found");
				return null;
			}
		}else {
			JOptionPane.showMessageDialog(null, "Book with ISBN not found");
			return null;
		}
	}
	
	public void addCopies(Book book, int numOfCopies) {
		book.addCopies(numOfCopies);
	}
	
	public void printCheckOutRecord(String memId) {
		LibraryMember member = getLibraryMember(memId);
		if(member != null) {
			DataAccessFacade da = new DataAccessFacade();
			HashMap<String, LibraryMember> mems = da.readMemberMap();
			member.printCheckOutRecordEntry();
				
		}
		else {
			JOptionPane.showMessageDialog(null, "Member with ID not found");
		}
	}
}
