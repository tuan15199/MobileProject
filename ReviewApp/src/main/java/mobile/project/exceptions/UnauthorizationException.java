package mobile.project.exceptions;

public class UnauthorizationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public String errorField = null;

	public UnauthorizationException(String errorField) {
	    this.errorField = errorField;
	  }
}
