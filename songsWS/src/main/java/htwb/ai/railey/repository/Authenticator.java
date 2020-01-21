package htwb.ai.railey.repository;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
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
    private Map<String, String> userTokenMap; 
    
    @Inject
    public Authenticator(EntityManagerFactory emf) {
    	this.emf = emf; // = Persistence.createEntityManagerFactory(persistenceUnit);
    	this.userTokenMap = new HashMap<>();
    	userTokenMap.put("testuser", "hellonewday"); //for test purpose
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
    	
    	for (Map.Entry<String, String> userToken : userTokenMap.entrySet()) {
    	    if (userToken.getValue().equals(authToken)) {
    	    	return true;
    	    };
    	}
    	
    	return false;
    }
    
    @Override
    public String generateToken(String userId) {
		try {
			KeyGenerator keyGen = KeyGenerator.getInstance("AES");
			keyGen.init(256);
	    	SecretKey secretKey = keyGen.generateKey();
	    	String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded()); //Secret Key to String
	    	this.userTokenMap.put(userId, encodedKey);
	    	return encodedKey;
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
    }
    
    @Override
    public String getUserId(String authToken) {
    	for (Map.Entry<String, String> userToken : userTokenMap.entrySet()) {
    	    if (userToken.getValue().equals(authToken)) {
    	    	return userToken.getKey();
    	    };
    	}
    	
    	return null;
    }
    
    @Override
    public boolean isUserTheOwner(String userId, String authToken) {
    	if (isUserExisted(userId) && userTokenMap.get(userId).equals(authToken))
    		return true;
    	else return false;
    	
    }
    
    @Override
    public boolean isUserExisted(String userId) {
    	if (userTokenMap.containsKey(userId))
    		return true;
    	else return false;
    }
    
    /**
     * String to SecretKey:
     * decode the base64 encoded string
     * byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
     * rebuild key using SecretKeySpec
     * SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES")
     */
}
