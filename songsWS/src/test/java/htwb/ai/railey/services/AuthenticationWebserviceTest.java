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
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import htwb.ai.railey.repository.Authenticator;
import htwb.ai.railey.repository.SongsDBDAO;
import htwb.ai.railey.services.filter.AuthorizationFilter;
import htwb.ai.railey.storage.IAuthenticator;
import htwb.ai.railey.storage.SongsDAO;

public class AuthenticationWebserviceTest extends JerseyTest {
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

    @Test
    public void unknownUser() {
        Response response = target("/auth").queryParam("userId", "what").queryParam("key", "hello").request().get();
        Assert.assertEquals(404, response.getStatus());
    }

    @Test
    public void validUserTest() {
        Response response = target("/auth").queryParam("userId", "eschuler").queryParam("key", "ENCRYPTEDKEY2").request().get();
        Assert.assertEquals(404, response.getStatus());
    }
}
