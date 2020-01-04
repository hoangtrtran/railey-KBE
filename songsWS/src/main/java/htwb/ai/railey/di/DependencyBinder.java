package htwb.ai.railey.di;

import javax.inject.Singleton;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

import htwb.ai.railey.repository.Authenticator;
import htwb.ai.railey.repository.SongsDBDAO;
import htwb.ai.railey.storage.IAuthenticator;
import htwb.ai.railey.storage.SongsDAO;

public class DependencyBinder extends AbstractBinder {
	@Override
	protected void configure() {
		bind (Persistence.createEntityManagerFactory("songDB-PU")).to(EntityManagerFactory.class); 
		bind (SongsDBDAO.class).to(SongsDAO.class).in(Singleton.class);
		bind (Authenticator.class).to(IAuthenticator.class).in(Singleton.class);
	}
}