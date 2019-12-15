package htwb.ai.railey;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import htwb.ai.railey.di.DependencyBinder;
import htwb.ai.railey.model.Song;
import htwb.ai.railey.repository.SongsDBDAO;

@ApplicationPath("/rest")
public class MyApplication extends ResourceConfig {
			
    public MyApplication() {
        register(new DependencyBinder());
        packages("htwb.ai.railey.services");
    }
    
    public static void main(String[] args) {
    	List<Song> songList = readSongsFromResource();
    	
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("songDB-PU");
        SongsDBDAO songsDBDAO = new SongsDBDAO(emf);
       
        for (Song s : songList) {
        	if (!songsDBDAO.isTitleExisted(s.getTitle()))
        		songsDBDAO.addSong(new Song(s.getTitle(), s.getArtist(), s.getLabel(), s.getReleased()));
        }
       
        songsDBDAO.done();
    }
    
    private static List<Song> readSongsFromResource() {
    	List<Song> songs = new LinkedList<>();
        try {

            String fileName = MyApplication.class.getClassLoader().getResource("songs.json").getFile();
            ObjectMapper objectMapper = new ObjectMapper();
            try (InputStream is = new BufferedInputStream(new FileInputStream(fileName))) {
                songs = objectMapper.readValue(is, new TypeReference<List<Song>>() {
                });
            }

        } catch (IOException e) {
        	System.out.println(e.getMessage());
        }
        Collections.sort(songs,new Comparator<Song>(){
			@Override
			public int compare(Song s1, Song s2) {
				return s1.getId() - s2.getId();
			}
        });
        Collections.reverse(songs); 
        
        return songs;
    }
}

