package business;

import java.io.Serializable;

public class MemberException extends Exception implements Serializable {
	
	private static final long serialVersionUID = 3126915348398932420L;
	public MemberException() {
		super();
	}
	public MemberException(String msg) {
		super(msg);
	}
	public MemberException(Throwable t) {
		super(t);
	}
}
