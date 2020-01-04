package htwb.ai.railey.repository;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import htwb.ai.railey.model.Song;
import htwb.ai.railey.storage.SongsDAO;

public class SongsDBDAO implements SongsDAO {
	// Datei persistence.xml wird automatisch eingelesen
	
    private EntityManagerFactory emf; //wird nur einmal beim Start der Applikation erstellt
    
    
    @Inject
    public SongsDBDAO(EntityManagerFactory emf) {
    	this.emf = emf; // = Persistence.createEntityManagerFactory(persistenceUnit);
    }

	@Override
	public Song getSong(Integer id) {
		EntityManager em = null;
		Song song;
		
        try {
            em = emf.createEntityManager();
            Query q = em.createQuery("SELECT s FROM Song s where id = " + id);
            song = (Song) q.getSingleResult();
        } 
        catch (Exception e){
        	song = null;
        } 
        finally {
            if (em != null) {
                em.close();
            }
        }
        return song;
	}
	
	public boolean isTitleExisted (String title) {
		EntityManager em = null;
		boolean exist;
        try {
            em = emf.createEntityManager();
            Query q = em.createQuery("SELECT s FROM Song s where title = '" + title + "'");
            q.getSingleResult();
            exist = true;
        } 
        catch (Exception e){
        	exist = false;
        } 
        finally {
            if (em != null) {
                em.close();
            }
        }
        return exist;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Song> getAllSongs() {
		EntityManager em = null;
		List<Song> songList;
        try {
            em = emf.createEntityManager();
            Query q = em.createQuery("SELECT s FROM Song s");
            songList = q.getResultList();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return songList;
	}

	@Override
	public Integer addSong(Song song) throws PersistenceException {
        // EntityManager bietet Zugriff auf Datenbank
        // EM wird bei jedem Zugriff auf die DB erstellt
        EntityManager em = null;
        EntityTransaction transaction = null;
        Integer songId;
        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(song);
            transaction.commit();
            songId = song.getId();
        } catch (IllegalStateException | 
                EntityExistsException | RollbackException  ex) {
            if (em != null) {
                em.getTransaction().rollback();
            }
            songId = null;
        } finally {
            // EntityManager nach Datenbankaktionen wieder freigeben
            if (em != null) {
                em.close();
            }
        }
        return songId;
    }

	@Override
	public boolean updateSong(Integer id, Song song) {
		EntityManager em = emf.createEntityManager();
        Song findedSong = null;
        EntityTransaction transaction = null;
        boolean updated;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            // find Song with id 
            findedSong = em.find(Song.class, id);
            if (findedSong != null) {
                findedSong.setTitle(song.getTitle());
                findedSong.setArtist(song.getArtist());
                findedSong.setLabel(song.getLabel());
                findedSong.setReleased(song.getReleased());
                transaction.commit();
             
            }
            updated = true;
        } catch (Exception e) {
           updated = false;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return updated;
	}

	@Override
	public Song deleteSong(Integer id) throws PersistenceException {
        EntityManager em = emf.createEntityManager();
        Song song;
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            // find Song with id 
            song = em.find(Song.class, id);
            if (song != null) {
                em.remove(song);
                transaction.commit();
            }
        } catch (Exception e) {
            song = null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return song;
    }
	
    // Freigabe der EMF am Ende der Applikation
    public void done() {
        emf.close();
    }

}
