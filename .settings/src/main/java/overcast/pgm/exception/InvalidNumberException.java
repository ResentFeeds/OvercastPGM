package overcast.pgm.exception;

public class InvalidNumberException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public InvalidNumberException(int id){
		 super( id + " is a invalid number");
	}

}
