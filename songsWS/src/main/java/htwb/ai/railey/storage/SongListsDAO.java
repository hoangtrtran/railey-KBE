package htwb.ai.railey.storage;

import java.util.List;
import htwb.ai.railey.model.SongList;
import htwb.ai.railey.model.User;

public interface SongListsDAO {

	List<SongList> getSongListsByUser(String userId, boolean userIsTheOwner);
	SongList getSongListById(Integer id);
	Integer addSongList(SongList songList);
	User getUserWithId(String userId);
	
}
