package htwb.ai.railey.services;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import htwb.ai.railey.repository.Authenticator;
import htwb.ai.railey.repository.SongListsDBDAO;
import htwb.ai.railey.repository.SongsDBDAO;
import htwb.ai.railey.services.SongsWebService;
import htwb.ai.railey.services.filter.AuthorizationFilter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import htwb.ai.railey.storage.IAuthenticator;
import htwb.ai.railey.storage.SongListsDAO;
import htwb.ai.railey.storage.SongsDAO;

public class SongListsWebServiceTest extends JerseyTest{
	/*
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
            	bind (Persistence.createEntityManagerFactory("Songlists-TEST-PU")).to(EntityManagerFactory.class); 
        		bind (SongsDBDAO.class).to(SongsDAO.class).in(Singleton.class);
        		bind (Authenticator.class).to(IAuthenticator.class).in(Singleton.class);
        		bind (SongListsDBDAO.class).to(SongListsDAO.class).in(Singleton.class);
            }
          });
        
        return resourceConfig;
    }
	
	@Test
    public void getSongListByIdShouldBeXml() {
		Response response = target("/songLists/1").request().header("Authorization", "hellonewday").header("Accept", "application/xml").get();
		Assert.assertEquals(Response.ok(), response.getStatus());
		assertTrue(response.toString().startsWith("<?xml"));
    }
	
	@Test
    public void getSongListByIdNotFound() {
		Response response = target("/songLists/10").request().header("Authorization", "hellonewday").header("Accept", "application/xml").get();
		Assert.assertEquals(Response.Status.NOT_FOUND, response.getStatus());
		assertTrue(response.toString().startsWith("<?xml"));
    }
	
	@Test
    public void getSongListByUserIdNotExist() {
		Response response = target("/songLists?userId=was").request().header("Authorization", "hellonewday").header("Accept", "application/xml").get();
		Assert.assertEquals(Response.Status.NOT_FOUND, response.getStatus());
    }*/
}
