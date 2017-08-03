package algutils;

/**
 * Exception that controls input file errors. 
 * 
 * @author David Gutiérrez-Avilés
 * 
 */
@SuppressWarnings("serial")
public class LegacyWrongInput extends Exception {

	private String mensaje;

	public LegacyWrongInput(String message) {
		this.mensaje = message;
	}

	public String getMenssage() {
		return mensaje;
	}

}
