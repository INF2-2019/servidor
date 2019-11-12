package biblioteca.admin.repositories;

public class AdminNotFoundException extends Exception {
	public AdminNotFoundException() {
	}

	public AdminNotFoundException(String message) {
		super(message);
	}

	public AdminNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AdminNotFoundException(Throwable cause) {
		super(cause);
	}

	public AdminNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
