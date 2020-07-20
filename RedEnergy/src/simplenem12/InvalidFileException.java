package simplenem12;
/**
 * CustomException to be thrown when the file is missing header/footer
 * 
 * header -100 ,footer -900
 */
public class InvalidFileException extends Exception{
	private String message;


	public InvalidFileException(String message) {
		super();
		this.message = message;
	}

	public InvalidFileException(String message, Throwable cause) {
		super(message,cause);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	

}
