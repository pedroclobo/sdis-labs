package pt.tecnico.addressbook.server.domain;

import pt.tecnico.addressbook.grpc.AddressBookList;
import pt.tecnico.addressbook.grpc.PersonInfo.PhoneType;
import pt.tecnico.addressbook.server.domain.exception.DuplicatePersonInfoException;
import pt.tecnico.addressbook.server.domain.exception.PersonNotFoundException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AddressBook {

	private ConcurrentHashMap<String, Person> people = new ConcurrentHashMap<>();

	public AddressBook() {
	}

	public void addPerson(String name, String email, int phoneNumber, PhoneType type) throws DuplicatePersonInfoException {
		if(people.putIfAbsent(email, new Person(name, email, phoneNumber, type)) != null) {
			throw new DuplicatePersonInfoException(email);
		}
	}

	public Person searchPerson(String email) {
		if (!people.containsKey(email)) {
			throw new PersonNotFoundException(email);
		}

		return people.get(email);
	}

	public AddressBookList proto() {
		return AddressBookList.newBuilder()
				.addAllPeople(people.values().stream().map(Person::proto).collect(Collectors.toList()))
				.build();
	}
}
