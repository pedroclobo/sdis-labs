package pt.tecnico.addressbook.server.domain.exception;

public class PersonNotFoundException extends RuntimeException {
	public PersonNotFoundException(String email) {
		super("There is no person with email " + email + " in the address book");
	}
}
