package mobile.project.exceptions;

public class InvalidNumberException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public String errorField = null;

	public InvalidNumberException(String errorField) {
		this.errorField = errorField;
	}
}
