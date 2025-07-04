package ever.support.exception;

public class JwtCustomException extends Exception {
	private static final long serialVersionUID = 2541106846666574747L;

	public JwtCustomException() {
	}

	public JwtCustomException(String message) {
		super(message);
	}

	public JwtCustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public JwtCustomException(Throwable cause) {
		super(cause);
	}

	protected JwtCustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
