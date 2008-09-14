package eu.spoonman.smasher.serverinfo;

public class RequiredPropertyNotFoundException extends Exception {

	/**
     * 
     */
    private static final long serialVersionUID = 1L;

    public RequiredPropertyNotFoundException() {
	}

	public RequiredPropertyNotFoundException(String arg0) {
		super(arg0);
	}

	public RequiredPropertyNotFoundException(Throwable arg0) {
		super(arg0);
	}

	public RequiredPropertyNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
