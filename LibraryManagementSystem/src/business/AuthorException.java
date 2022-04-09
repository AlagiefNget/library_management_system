package business;

import java.io.Serializable;

public class AuthorException extends Exception implements Serializable {

	public AuthorException() {
		super();
	}
	public AuthorException(String msg) {
		super(msg);
	}
	public AuthorException(Throwable t) {
		super(t);
	}
	private static final long serialVersionUID = 8977723266036027364L;
	
}
