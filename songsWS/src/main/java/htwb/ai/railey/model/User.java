package htwb.ai.railey.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	
	@OneToMany(cascade = CascadeType.PERSIST, mappedBy = "owner")
	private Set<SongList> songLists;
	
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
	
	@Override
    public String toString() {
        return "User [userId = " + userId + ", key = " + key + ", first name = " + firstName 
        		+ ", last name =" + lastName + "]";
    }

}
