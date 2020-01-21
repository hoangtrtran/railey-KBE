package htwb.ai.railey.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlAccessType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="songlist")
@XmlRootElement(name="songlist")
@XmlAccessorType(XmlAccessType.FIELD)
public class SongList {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	Integer id;
	
	@ManyToOne
    @JoinColumn(name = "ownerId")
    @JsonIgnore
    @XmlTransient
	User owner;

	@Column (name = "name")
	String name;
	
	@Column (name = "isPrivate")
	Boolean isPrivate;
	
	@ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "songlist_song",
    		joinColumns = @JoinColumn(name = "songlist_id", referencedColumnName ="id"),
    		inverseJoinColumns = @JoinColumn(name = "song_id", referencedColumnName ="id"))
    @XmlElementWrapper(name = "songs")
    @XmlElement(name = "song")
	Set<Song> songs;
	
	public SongList(Integer id, User owner, String name, Boolean isPrivate, Set<Song> songs) {
		super();
		this.id = id;
		this.owner = owner;
		this.name = name;
		this.isPrivate = isPrivate;
		this.songs = songs;
	}

	public SongList() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Boolean getIsPrivate() {
		return isPrivate;
	}

	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	public Set<Song> getSongs() {
		return songs;
	}

	public void setSongs(Set<Song> songs) {
		this.songs = songs;
	}
	
	
	/**
	 * CREATE TABLE IF NOT EXISTS songlist_song (
    	songlist_id INT(11) NOT NULL, 
  		song_id INT(11) NOT NULL, 
    	CONSTRAINT fk_songlist FOREIGN KEY (songlist_id) REFERENCES songlist(id) ON DELETE CASCADE,
    	CONSTRAINT fk_song FOREIGN KEY (song_id) REFERENCES song(id) ON DELETE CASCADE)  
	ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 */
	
	/**
	 CREATE TABLE IF NOT EXISTS `songlist` ( 
    `id` INT NOT NULL AUTO_INCREMENT,
    `ownerId` VARCHAR(50) NOT NULL , 
    `name` VARCHAR(50), 
    `isPrivate` BIT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`ownerId`) REFERENCES user(userId)) ENGINE=InnoDB DEFAULT CHARSET=utf8;
	 */
}
