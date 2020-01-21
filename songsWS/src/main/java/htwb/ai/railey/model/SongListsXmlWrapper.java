package htwb.ai.railey.model;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "songLists")
@XmlAccessorType(XmlAccessType.FIELD)
public class SongListsXmlWrapper {
	
	@XmlElement(name = "songList")
    private List<SongList> songLists;
	
	public SongListsXmlWrapper() {
		this.songLists = new LinkedList<>();
	}
	
	public void addSongLists(List<SongList> songLists) {
        this.songLists.addAll(songLists);
    }
}