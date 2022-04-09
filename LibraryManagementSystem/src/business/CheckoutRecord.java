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
		System.out.println("-----------------------------------------------------------------------------");
        System.out.printf("%10s  %10s %20s %20s ", "ISBN","MAXLENGTH", "CHECKOUTDATE", "DUEDATE");
        System.out.println();
        System.out.println("-----------------------------------------------------------------------------");
    	for(CheckoutEntry entry:checkoutEntries){
        	System.out.format("%10s %10d %20s %20s",entry.getBookCopy().getBook().getIsbn(),entry.getBookCopy().getBook().getMaxCheckoutLength(),
        			entry.getCheckoutDate(), entry.getDueDate());
        	
            System.out.println();
        }
        System.out.println("-----------------------------------------------------------------------------");

//		for(CheckoutEntry entry:checkoutEntries) {
//			System.out.format("%10s %20s %5s",entry.getBookCopy().getBook() +   " " + entry.getCheckoutDate()+ " " + entry.getDueDate());
//		}
		
	}
}
