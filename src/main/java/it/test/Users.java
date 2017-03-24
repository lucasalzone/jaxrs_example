package it.test;

import java.util.ArrayList;
import java.util.List;

import it.test.dto.User;

public class Users {
	private static Users users;
	private ArrayList<User> userList;
	private Users(){}
	
	public static Users getSingleton(){
		if(users==null){
			users=new Users();
			users.init();
		}
		return users;
	}
	
	private void init() {
		userList = new ArrayList<User>();
		userList.add(new User("luca","salzone"));
		userList.add(new User("mario","rossi"));
		userList.add(new User("giuseppe","bianchi"));
		userList.add(new User("francesco","verdi"));
	}
	
	public Users add(final User user){
		userList.add(user);
		return this;
	}
	
	public User getByName(final String name){
		for (User user : userList) {
			if(user.getName().equals(name)){
				return user;
			}
		}
		return null;
	}
	
	public List<User> get(){
		return userList;
	}

	public void remove(User user) {
		userList.remove(user);
	}

	public Users removeByName(String name) {
		for (User user : userList) {
			if(user.getName().equals(name)){
				userList.remove(user);
				return this;
			}
		}
		return this;
	}
}
