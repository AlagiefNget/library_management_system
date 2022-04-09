package business;

import java.io.Serializable;
import java.util.Date;

public class Payment implements Serializable {
	
	private static final long serialVersionUID = 3384229372955453486L;
	private Date date;
	private Double amount;
	
	public Payment(Date date, Double amount) {
		this.setAmount(amount);
		this.setDate(date);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
}
