package htwb.ai.railey.storage;

public interface IAuthenticator {
	boolean login(String userId, String key);
	boolean authenticate(String authToken);
	String generateToken();
}
