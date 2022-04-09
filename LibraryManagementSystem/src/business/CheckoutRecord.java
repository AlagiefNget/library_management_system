package business;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CheckoutRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2616918611240886733L;
	List<CheckoutEntry> checkoutEntries = new ArrayList<>();

	public void setCheckoutEntries(List<CheckoutEntry> checkoutEntries) {
		this.checkoutEntries = checkoutEntries;
	}

	public List<CheckoutEntry> getCheckoutEntries() {
		return checkoutEntries;
	}
	public void printCheckOutRecordEntry() {
		for(CheckoutEntry entry:checkoutEntries) {
			System.out.println(entry.getBookCopy().getBook() +   " " + entry.getCheckoutDate()+ " " + entry.getDueDate());
		}
		
	}
}
