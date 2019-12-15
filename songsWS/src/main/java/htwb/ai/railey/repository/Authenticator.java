package htwb.ai.railey.repository;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import htwb.ai.railey.model.User;
import htwb.ai.railey.storage.IAuthenticator;

public class Authenticator implements IAuthenticator {
	
	private EntityManagerFactory emf;
    private List<String> tokenList; 
    
    @Inject
    public Authenticator(EntityManagerFactory emf) {
    	this.emf = emf; // = Persistence.createEntityManagerFactory(persistenceUnit);
    	this.tokenList = new LinkedList<>();
    	tokenList.add("hellonewday"); //for test purpose
    }

    @Override
	public boolean login(String userId, String key) {
		EntityManager em = null;
		User user;
		boolean authenticated;
		
        try {
            em = emf.createEntityManager();
            Query q = em.createQuery("SELECT u FROM User u where id = '" + userId + "'");
            user = (User) q.getSingleResult();
            if (user.getUserId().equals(userId) && user.getKey().equals(key)) {
            	authenticated = true;
            }
            else authenticated = false;
        } 
        catch (Exception e){
        	authenticated = false;
        } 
        finally {
            if (em != null) {
                em.close();
            }
        }
        return authenticated;
	}
    
    /**
     * Check if authToken exist.
     * @param authToken
     * @return
     */
    @Override
    public boolean authenticate(String authToken) {
    	for (String token : tokenList) {
    		if (token.equals(authToken)) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public String generateToken() {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256);
	    	SecretKey secretKey = keyGen.generateKey();
	    	String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded()); //Secret Key to String
	    	tokenList.add(encodedKey);
	    	return encodedKey;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
    }
    
    /**
     * String to SecretKey:
     * decode the base64 encoded string
     * byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
     * rebuild key using SecretKeySpec
     * SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES")
     */
}
