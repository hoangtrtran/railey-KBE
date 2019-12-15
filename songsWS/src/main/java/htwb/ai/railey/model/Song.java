package htwb.ai.railey.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="song")
@XmlRootElement(name="song")
public class Song {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private Integer id;
	
	@Column (name = "title")
	private String title;
	
	@Column (name = "artist")
	private String artist;
	
	@Column (name = "label")
	private String label;

	@Column (name = "released")
	private Integer released;
	
	public Song() {
		super();
	}

	public Song(String title, String artist, String label, Integer released) {
		super();
		this.title = title;
		this.artist = artist;
		this.label = label;
		this.released = released;
	}

	public Song(Integer id, String title, String artist, String label, Integer released) {
		super();
		this.id = id;
		this.title = title;
		this.artist = artist;
		this.label = label;
		this.released = released;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getArtist() {
		return artist;
	}
	
	public void setArtist(String artist) {
		this.artist = artist;
	}
	
	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	
	public Integer getReleased() {
		return released;
	}
	
	public void setReleased(Integer released) {
		this.released = released;
	}
	
	public static class Builder {
		private Integer id;
		private String title;
		private String artist;
		private String label;
		private Integer released;

		public Builder(Integer id, String title) {
			this.id = id;
			this.title = title;
		}

		public Builder artist(String artist) {
			this.artist = artist;
			return this;
		}
		
		public Builder label(String label) {
			this.label = label;
			return this;
		}

		public Builder released(Integer released) {
			this.released = released;
			return this;
		}
		
		public Song build() {
			return new Song(this);
		}
	}

	private Song(Builder builder) {
		this.id = builder.id;
		this.title = builder.title;
		this.artist = builder.artist;
		this.label = builder.label;
		this.released = builder.released;
	}
	
	@Override
    public String toString() {
        return "Song [id = " + id + ", title = " + title + ", artist = " + artist + ", label = " + label 
        		+ ", released =" + released + "]";
    }
}
