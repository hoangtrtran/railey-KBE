package htwb.ai.railey.services;

import java.io.IOException;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import htwb.ai.railey.storage.IAuthenticator;

@Path("/auth")
public class AuthenticationWebService {
	
	 @Inject
	 private IAuthenticator authenticator;
	    
	 @Context 
	 private UriInfo uriInfo;
	    
	/**
     * get the token of the saved user
     * @param userId
     * @return
     * @throws IOException
     */
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAuth(@QueryParam("userId") String userId, @QueryParam("key") String key) throws IOException {
    	if (!authenticator.login(userId, key)) {
        	return Response.status(401).entity("Either user Id or key was not corrected or existed").build();
        }
        else {
        	String newToken = this.authenticator.generateToken();
        	if (newToken != null) {
        		return Response.ok(newToken).build();
        	}
        	else return Response.status(401).entity("Cannot generate token.").build();
        	
        }
    }
}
