package htwb.ai.railey.storage;
import java.util.List;
import htwb.ai.railey.model.Song;

public interface SongsDAO {
	public Song getSong(Integer id);
	public List<Song> getAllSongs(); 
	public Integer addSong(Song song);
	public boolean updateSong(Integer id, Song song);
	public Song deleteSong(Integer id);
}
