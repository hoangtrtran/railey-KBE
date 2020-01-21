package htwb.ai.railey.repository;

import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import htwb.ai.railey.model.SongList;
import htwb.ai.railey.model.User;
import htwb.ai.railey.storage.SongListsDAO;

public class SongListsDBDAO implements SongListsDAO {
	
	private EntityManagerFactory emf; //wird nur einmal beim Start der Applikation erstellt
        
    @Inject
    public SongListsDBDAO(EntityManagerFactory emf) {
    	this.emf = emf; // = Persistence.createEntityManagerFactory(persistenceUnit);
    }
    
    
    @Override
	public List<SongList> getSongListsByUser(String userId, boolean userIsTheOwner) {
		EntityManager em = null;
		List<SongList> songList = null;
		try {
			em = emf.createEntityManager();
			
			if(userIsTheOwner) {
				songList = (List<SongList>) em.createQuery("SELECT sl FROM SongList sl WHERE sl.owner.userId = :userId", SongList.class)
							 .setParameter("userId", userId)
							 .getResultList();
			}
			else {
				songList = (List<SongList>) em.createQuery("SELECT sl FROM SongList sl WHERE sl.owner.userId = :userId AND isPrivate = true", SongList.class)
                        	 .setParameter("userId", userId)
                        	 .getResultList();
			}
		}
		catch (Exception e){
	        	songList = null;
	    } 
	    finally {
	    	if (em != null) {
	    		em.close();
	        }
	    }
	    return songList;
	}
    
    @Override
    public SongList getSongListById(Integer id) {
		EntityManager em = null;
		SongList songList;
		
        try {
            em = emf.createEntityManager();
            songList = em.find(SongList.class, id);
        } 
        catch (Exception e){
        	e.printStackTrace();
        	songList = null;
        } 
        finally {
            if (em != null) {
                em.close();
            }
        }
        return songList;
	}
    
    @Override
	public User getUserWithId(String userId) {
		EntityManager em = null;
		User user;
		
        try {
            em = emf.createEntityManager();
            Query q = em.createQuery("SELECT u FROM User u where id = '" + userId + "'");
            user = (User) q.getSingleResult();
        } 
        catch (Exception e){
        	e.printStackTrace();
        	user = null;
        } 
        finally {
            if (em != null) {
                em.close();
            }
        }
        return user;
	}
    
	@Override
	public Integer addSongList(SongList songList) throws PersistenceException {
        // EntityManager bietet Zugriff auf Datenbank
        // EM wird bei jedem Zugriff auf die DB erstellt
        EntityManager em = null;
        EntityTransaction transaction = null;
        Integer songId;
        try {
            em = emf.createEntityManager();
            transaction = em.getTransaction();
            transaction.begin();
            em.persist(songList);
            transaction.commit();
            songId = songList.getId();
        } catch (Exception  ex) {
        	ex.printStackTrace();
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
	public SongList deleteSongList(Integer id) throws PersistenceException {
        EntityManager em = emf.createEntityManager();
        SongList songList;
        EntityTransaction transaction = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            // find SongList with id 
            songList = em.find(SongList.class, id);
            if (songList != null) {
                em.remove(songList);
                transaction.commit();
            }
        } catch (Exception e) {
            songList = null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return songList;
    }
}
