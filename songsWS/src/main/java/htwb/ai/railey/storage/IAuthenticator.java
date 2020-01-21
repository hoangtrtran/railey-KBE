package htwb.ai.railey.storage;

public interface IAuthenticator {
	boolean login(String userId, String key);
	boolean authenticate(String authToken);
	String generateToken(String userId);
	String getUserId(String authToken);
	boolean isUserTheOwner(String userId, String authToken);
	boolean isUserExisted(String userId);
}
