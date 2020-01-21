package htwb.ai.railey.storage;
import java.util.List;
import htwb.ai.railey.model.Song;

public interface SongsDAO {
	Song getSong(Integer id);
	List<Song> getAllSongs(); 
	Integer addSong(Song song);
	boolean updateSong(Integer id, Song song);
	Song deleteSong(Integer id);
	boolean isSongExisted(Song song);
}
