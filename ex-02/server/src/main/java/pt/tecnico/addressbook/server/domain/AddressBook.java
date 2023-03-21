package pt.tecnico.addressbook.server.domain;

import pt.tecnico.addressbook.grpc.AddressBookList;
import pt.tecnico.addressbook.grpc.PersonInfo.PhoneType;
import pt.tecnico.addressbook.server.domain.exception.DuplicatePersonInfoException;
import pt.tecnico.addressbook.server.domain.exception.PersonNotFoundException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import java.util.List;
import java.util.ArrayList;

public class AddressBook {

	private ConcurrentHashMap<String, Person> people = new ConcurrentHashMap<>();

	public AddressBook() {
	}

	public void addPerson(String name, String email, int phoneNumber, PhoneType type, String website) throws DuplicatePersonInfoException {
		if(people.putIfAbsent(email, new Person(name, email, phoneNumber, type, website)) != null) {
			throw new DuplicatePersonInfoException(email);
		}
	}

	public Person searchPerson(String email) {
		if (!people.containsKey(email)) {
			throw new PersonNotFoundException(email);
		}

		return people.get(email);
	}

	public List<Person> listAll(String website) {
		List<Person> pp = new ArrayList<>();

		for (Person person : people.values()) {
			if (person.getWebsite().equals(website)) {
				pp.add(person);
			}
		}

		return pp;
	}

	public AddressBookList proto() {
		return AddressBookList.newBuilder()
				.addAllPeople(people.values().stream().map(Person::proto).collect(Collectors.toList()))
				.build();
	}
}
