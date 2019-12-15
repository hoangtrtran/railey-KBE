package htwb.ai.railey.services.filter;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;
import htwb.ai.railey.storage.IAuthenticator;

@Provider
public class AuthorizationFilter implements ContainerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    
    @Inject
    private IAuthenticator authenticator;

    /**
     * authToken = null or empty
     * bedeutet, dass HTTP-Header "Authorization" 
     * nicht mitgeschickt wurde oder keinen Wert hatte
     * Das kann zwei Gruende haben:
     * 1) Client hat noch keinen Token und will zu /auth?userId=...&key=... oder zu /version
     * 2) Client hat keinen Token mitgeschickt und will zu /songs
     * Wenn 1), dann nix machen = durchlassen
     * Wenn 2), dann nicht durchlassen = WebApplicationException werfen
     */
    @Override
    public void filter(ContainerRequestContext containerRequest) throws WebApplicationException {
        String authToken = containerRequest.getHeaderString(AUTHORIZATION_HEADER);

        if (authToken == null || authToken.trim().isEmpty()) {
            if (!(containerRequest.getUriInfo().getQueryParameters().containsKey("userId") 
            		&& containerRequest.getUriInfo().getQueryParameters().containsKey("key"))) {
            	if (!containerRequest.getUriInfo().getPath().equals("version")) {
            		throw new WebApplicationException(Status.UNAUTHORIZED);
            	}           
            }
        } else {
            if (!authenticator.authenticate(authToken)) { // Token existiert nicht
                throw new WebApplicationException(Status.UNAUTHORIZED); //401
            }
        }
    }
}