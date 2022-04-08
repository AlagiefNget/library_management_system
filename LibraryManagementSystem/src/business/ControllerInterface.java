package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login(String id, String password) throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	public void addMember(String id, String firstname, String lastname,String phone, String city,String street,
			String state, String zip) throws AddMemberException;
	public void addBook(String isbn, String title , int maxCheckoutLength) throws BookException;
	
}
