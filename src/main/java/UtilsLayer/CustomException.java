package UtilsLayer;

public class CustomException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CustomException(String massage) {

		super(massage);
	}

	public CustomException(String massage, Throwable cause) {

		super(massage, cause);
	}

	public CustomException(Throwable cause) {

		super(cause);

	}
}