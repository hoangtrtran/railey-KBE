package htwb.ai.railey.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="user")
@XmlRootElement(name="user")
public class User {
	@Id
	@Column (name = "userId")
	String userId;
	
	@Column (name = "key")
	String key;
	
	@Column (name = "firstName")
	String firstName;
	
	@Column (name = "lastName")
	String lastName;
	
	public User() {
		super();
	}
	
	public User(String userId, String key, String firstName, String lastName) {
		super();
		this.userId = userId;
		this.key = key;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
