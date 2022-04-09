package business;

import java.io.Serializable;

final public class LibraryMember extends Person implements Serializable {
	private String memberId;
	private CheckoutRecord checkoutRecord;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;
		this.setCheckoutRecord(new CheckoutRecord());
	}
	
	public String getMemberId() {
		return memberId;
	}
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	public CheckoutRecord getCheckoutRecord() {
		return checkoutRecord;
	}


	public void setCheckoutRecord(CheckoutRecord checkoutRecord) {
		this.checkoutRecord = checkoutRecord;
	}
	
	private static final long serialVersionUID = -2226197306790714013L;
}
