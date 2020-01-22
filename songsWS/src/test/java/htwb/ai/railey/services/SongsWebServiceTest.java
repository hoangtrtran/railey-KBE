package htwb.ai.railey.services;
import java.net.URI;
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
import htwb.ai.railey.model.User;
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
	
	// ---------------Test for PUT ----------------------------
	
	@Test
	public void putJsonSongReturn204() {
        Response response = target("/songs/12").request().header("Authorization", "hellonewday").put(Entity.json(createNewSongToBePut()));
        Assert.assertEquals(204, response.getStatus());
	}
	
	@Test
	public void putXmlSongReturn204() {
        Response response = target("/songs/12").request().header("Authorization", "hellonewday").put(Entity.xml(createNewSongToBePut()));
        Assert.assertEquals(204, response.getStatus());
	}
	
	@Test
	public void putSongIdNotNumberReturn404() {
        Response response = target("/songs/kkkkkk").request().header("Authorization", "hellonewday").put(Entity.json(createNewSongToBePut()));
        Assert.assertEquals(404, response.getStatus());
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
	public void putSongLabelEmptyReturn400() {
		Song mySong = new Song();
		mySong.setId(12);
        mySong.setTitle("Fall On Me");
        mySong.setLabel ("");
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
	public void putSongUnautorisedTokenCodeDontExistReturn401() {
        Response response = target("/songs/12").request().header("Authorization", "wrongCode").put(Entity.xml(createNewSongToBePut()));
        Assert.assertEquals(401, response.getStatus());
	}
	
	@Test
	public void putSongIdDoesntExistyReturn400() {
        Response response = target("/songs/00").request().header("Authorization", "hellonewday").put(Entity.xml(createNewSongToBePut()));
        Assert.assertEquals(400, response.getStatus());
	}
	
	@Test 
	public void putSongWrongObjectReturn400() {
		Response response = target("/songs/12").request().header("Authorization", "hellonewday").put(Entity.xml(new User("123", "456", "Liam", "Grammy")));
        Assert.assertEquals(400, response.getStatus());
	}
	
	// -------------- Test for POST ----------------
	
	private Song createNewSongToBePost() {
		Song mySong = new Song();
        mySong.setArtist("Liam Grammy");
        mySong.setTitle("Make You Feel My Love");
        mySong.setLabel ("Liam-Record");
        mySong.setReleased(2019);
        return mySong;
	}
	
	@Test
	public void postJsonSongReturn201() {
        Response response = target("/songs").request().header("Authorization", "hellonewday").post(Entity.json(createNewSongToBePost()));
        System.out.println(response.getHeaderString("Location"));
        Assert.assertEquals(201, response.getStatus());
	}
	
	
	@Test
	public void postXMLSongReturn201() {
        Response response = target("/songs").request().header("Authorization", "hellonewday").post(Entity.xml(createNewSongToBePost()));
        System.out.println(response.getHeaderString("Location"));
        Assert.assertEquals(201, response.getStatus());
	}
	
	@Test
	public void postWrongObjectReturn400() {
        Response response = target("/songs").request().header("Authorization", "hellonewday").post(Entity.json(new User("123", "456", "Liam", "Grammy")));
        Assert.assertEquals(400, response.getStatus());
	}
	
	@Test
	public void postXMLSongWithIdReturn400() {
        Response response = target("/songs").request().header("Authorization", "hellonewday").post(Entity.xml(createNewSongToBePut()));
        Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void postSongArtistMissingReturn400() {
		Song mySong = new Song();
        mySong.setTitle("Fall On Me");
        mySong.setLabel ("Liam-Record");
        mySong.setReleased(2019);
        Response response = target("/songs").request().header("Authorization", "hellonewday").post(Entity.xml(mySong));
        Assert.assertEquals(400, response.getStatus());
	}

	@Test
	public void posUnautorisedReturn401() {
        Response response = target("/songs").request().header("Authorization", "gaugaugau").post(Entity.xml(createNewSongToBePut()));
        Assert.assertEquals(401, response.getStatus());
	}
	/*
	// ------------ DELETE -------------- first post a new Song and then delete that same song
	@Test
	public void deleteSongReturn204() {
        Response response = target("/songs").request().header("Authorization", "hellonewday").post(Entity.json(createNewSongToBePost()));
        URI location = response.getLocation();
        response = target(location.getPath()).request().header("Authorization", "hellonewday").delete();
        Assert.assertEquals(204, response.getStatus());
	}
	
	@Test
	public void deleteSongIdDoesntExistReturn400() {
        Response response = target("/songs/000").request().header("Authorization", "hellonewday").delete();
        Assert.assertEquals(400, response.getStatus());
	}
	

	@Test
	public void deleteSongIdNotNumberReturn404() {
        Response response = target("/songs/whatthehell").request().header("Authorization", "hellonewday").delete();
        Assert.assertEquals(404, response.getStatus());
	}
	
	@Test
	public void deleteSongUnautorisedReturn401() {
        Response response = target("/songs/whatthehell").request().header("Authorization", "shutup").delete();
        Assert.assertEquals(401, response.getStatus());
	}*/
	
}
