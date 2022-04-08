package business;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dataaccess.Auth;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;
import librarysystem.LibrarySystem;
import librarysystem.LoginWindow;

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
	public void addBook(String isbn, String title, int maxCheckoutLength) throws BookException {
		DataAccess da = new DataAccessFacade();
		// Author auth = new Author();
		List<Author> authors = new ArrayList<Author>();
		Book book = new Book(isbn, title, maxCheckoutLength, authors);
		da.saveNewBook(book);
		
	}
}
