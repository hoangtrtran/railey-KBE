package htwb.ai.railey.services;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import htwb.ai.railey.model.Song;
import htwb.ai.railey.model.SongList;
import htwb.ai.railey.model.SongListsXmlWrapper;
import htwb.ai.railey.model.User;
import htwb.ai.railey.storage.IAuthenticator;
import htwb.ai.railey.storage.SongListsDAO;
import htwb.ai.railey.storage.SongsDAO;

@Path("/songLists")
public class SongListsWebService {
	@Inject
    private SongsDAO songsDAO;
	
	@Inject
    private SongListsDAO songListsDAO;
	
	@Inject
	private IAuthenticator authenticator;
    
    @Context 
	private UriInfo uriInfo;
    
    /**
     * GET http://localhost:8080/songsWS-railey/rest/songLists?userId=liam
     * @param authToken
     * @param userId
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response getSongListsByUser(@HeaderParam("Authorization") String authToken, @QueryParam("userId") String userId) {
    	try {
    		if (!authenticator.isUserExisted(userId)) {
    			 return Response.status(404).entity("UserId does not exist.").build();
    		}
    		
            boolean isUserTheOwner = authenticator.isUserTheOwner(userId, authToken);
            List<SongList> songLists = songListsDAO.getSongListsByUser(userId, isUserTheOwner);
            if (!songLists.isEmpty()) {
            	SongListsXmlWrapper songListsXmlWrapper = new SongListsXmlWrapper();
            	songListsXmlWrapper.addSongLists(songLists);
                return Response.ok(songListsXmlWrapper).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Song list not found").build();
            }
    	}
    	catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}  
    	
    }
	
    /**
	 * GET http://localhost:8080/songsWS-railey/rest/songLists/1
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSongList(@HeaderParam("Authorization") String authToken, @PathParam("id") Integer id) {
		try {
			SongList getSongList = songListsDAO.getSongListById(id);
			if (getSongList == null) {
				return Response.status(Response.Status.NOT_FOUND).entity("Song List Id not found").build();
			}
			
			User owner = getSongList.getOwner();
			User requestUser = songListsDAO.getUserWithId(authenticator.getUserId(authToken));

			if (!owner.toString().equals(requestUser.toString()) && getSongList.getIsPrivate()) {
				return Response.status(Response.Status.FORBIDDEN).entity("This song list is private!").build();
			}
			return Response.status(Response.Status.OK).entity(getSongList).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}  
	}
    
	/**
	 * POST http://localhost:8080/songsWS-railey/rest/songLists with songList in payload
	 * Status Code 201 und URI fuer den neuen Eintrag im http-header 'Location' zurueckschicken, also:
	 * Location: http://localhost:8080/songsWS-railey/rest/songLists/neueID
	 * @param songList
	 * @return new SongListId
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response createSongList(@HeaderParam("Authorization") String authToken, SongList songList) {
		
		try {
			if (songList.getIsPrivate() == null) {
				return Response.status(400).entity("Song list is private field must be filled").build();
			}
			User owner = songListsDAO.getUserWithId(authenticator.getUserId(authToken));
			songList.setOwner(owner);
			
			for (Song song : songList.getSongs()) {
				if (!songsDAO.isSongExisted(song)) {
					return Response.status(400).entity("Song doesn't exist.").build();
				}
			}
			
			Integer newId = songListsDAO.addSongList(songList);
			if (newId == null) {
				 return Response.status(400).entity("Add song is unfortunately not succeeded.").build();
			}
			
		    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		    uriBuilder.path(Integer.toString(newId));
		    return Response.created(uriBuilder.build()).entity("New song list was added to database. Here is the location: " + uriBuilder).build();
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}   
	}
	
	
}