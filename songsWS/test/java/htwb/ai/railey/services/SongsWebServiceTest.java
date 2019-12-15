package htwb.ai.railey.services;
import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import javax.ws.rs.client.Entity;
import htwb.ai.railey.model.Song;
import htwb.ai.railey.repository.Authenticator;
import htwb.ai.railey.repository.SongsDBDAO;
import htwb.ai.railey.services.SongsWebService;
import htwb.ai.railey.services.filter.AuthorizationFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import htwb.ai.railey.storage.IAuthenticator;
import htwb.ai.railey.storage.SongsDAO;

public class SongsWebServiceTest extends JerseyTest{
    @BeforeEach
    @Override
    public void setUp() throws Exception {
        super.setUp();
    }

    @AfterEach
    @Override
    public void tearDown() throws Exception {
        super.tearDown();
    }
    
	@Override
    protected Application configure() {
		ResourceConfig resourceConfig = new ResourceConfig(SongsWebService.class, AuthorizationFilter.class);
        
        resourceConfig.register(new AbstractBinder() {
            protected void configure() {
            	bind (Persistence.createEntityManagerFactory("songDB-PU")).to(EntityManagerFactory.class); 
        		bind (SongsDBDAO.class).to(SongsDAO.class).in(Singleton.class);
        		bind (Authenticator.class).to(IAuthenticator.class).in(Singleton.class);
            }
          });
        
        return resourceConfig;
    }
	
	private Song createNewSongToBePut() {
		Song mySong = new Song();
		mySong.setId(12);
        mySong.setArtist("Christina Aguilera");
        mySong.setTitle("Fall On Me");
        mySong.setLabel ("Liam-Record");
        mySong.setReleased(2019);
        return mySong;
	}
	
	
	@Test
	public void putSongReturn204() {
        Response response = target("/songs/12").request().header("Authorization", "hellonewday").put(Entity.xml(createNewSongToBePut()));
        Assert.assertEquals(204, response.getStatus());
	}
	
	@Test
	public void putSongNotMatchingIdReturn400() {
		Song mySong = new Song();
		mySong.setId(11);
        mySong.setArtist("Christina Aguilera");
        mySong.setTitle("Fall On Me");
        mySong.setLabel ("Liam-Record");
        mySong.setReleased(2019);
        Response response = target("/songs/12").request().header("Authorization", "hellonewday").put(Entity.xml(mySong));
        Assert.assertEquals(400, response.getStatus());
	}
	
	@Test
	public void putSongArtistMissingReturn400() {
		Song mySong = new Song();
		mySong.setId(12);
        mySong.setTitle("Fall On Me");
        mySong.setLabel ("Liam-Record");
        mySong.setReleased(2019);
        Response response = target("/songs/12").request().header("Authorization", "hellonewday").put(Entity.xml(mySong));
        Assert.assertEquals(400, response.getStatus());
	}
	
	@Test
	public void putSongArtistAndLabelMissingReturn400() {
		Song mySong = new Song();
		mySong.setId(12);
        mySong.setTitle("Fall On Me");
        mySong.setReleased(2019);
        Response response = target("/songs/12").request().header("Authorization", "hellonewday").put(Entity.xml(mySong));
        Assert.assertEquals(400, response.getStatus());
	}
	
	@Test
	public void putSongUnautorisedEmtyHeaderReturn400() {
        Response response = target("/songs/12").request().header("Authorization", "").put(Entity.xml(createNewSongToBePut()));
        Assert.assertEquals(401, response.getStatus());
	}
	
	@Test
	public void putSongUnautorisedHeaderNullReturn400() {
        Response response = target("/songs/12").request().header("Authorization", null).put(Entity.xml(createNewSongToBePut()));
        Assert.assertEquals(401, response.getStatus());
	}
	
	@Test
	public void putSongUnautorisedTokenCodeDontExistReturn400() {
        Response response = target("/songs/12").request().header("Authorization", "wrongCode").put(Entity.xml(createNewSongToBePut()));
        Assert.assertEquals(401, response.getStatus());
	}
	
	@Test
	public void putSongIdDoesntExistyReturn400() {
        Response response = target("/songs/00").request().header("Authorization", "hellonewday").put(Entity.xml(createNewSongToBePut()));
        Assert.assertEquals(400, response.getStatus());
	}
	
	/*
	@Test
	public void put() {
		Song song = new Song();
		WebTarget target = target("songs");
		Response response = target.path("10").request()
                .put(Entity.entity(song, MediaType.APPLICATION_JSON));
      System.out.println(response.getHeaderString("Location"));
      System.out.println(response.getStatus());
		
	}*/
}
