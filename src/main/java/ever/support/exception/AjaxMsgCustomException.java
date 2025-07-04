package ever.support.exception;

public class AjaxMsgCustomException extends Exception {
	private static final long serialVersionUID = 5093463382166175624L;

	private static String messageCode = "ERROR";

	public AjaxMsgCustomException() {
	}

	public AjaxMsgCustomException(String message) {
		super(message);
	}

	public AjaxMsgCustomException(String messageCode, String message) {
		super(message);
		AjaxMsgCustomException.messageCode = messageCode;
	}

	public AjaxMsgCustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public AjaxMsgCustomException(Throwable cause) {
		super(cause);
	}

	protected AjaxMsgCustomException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public static String getMessageCode() {
		return messageCode;
	}

	public static void setMessageCode(String messageCode) {
		AjaxMsgCustomException.messageCode = messageCode;
	}
}
