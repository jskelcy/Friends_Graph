package friends;

import java.util.*;

public class Person{
	public String name;
	public String school;
	//public ArrayList<Person> friendList;
	
	public Person(String name, String school){	//ArrayList<Person> friendList
		this.name = name;
		this.school = school;
		//this.friendList = friendList;
	}
}


class friendList{
	Person person;
	friendList next;
	
	public friendList(String name, String school, friendList next){
		person = new Person(name, school);
		this.next = next;
	}
}
