package htwb.ai.railey.services;
import java.util.Collection;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import htwb.ai.railey.model.Song;
import htwb.ai.railey.storage.SongsDAO;

/**
 * URL fuer diesen Service ist: 
 * http://localhost:8080/songsWS-railey/rest/songs
 */
@Path("/songs")
public class SongsWebService {

	@Inject
    private SongsDAO songsDAO;
    
    @Context 
	private UriInfo uriInfo;

	/**
	 * GET http://localhost:8080/songsWS-railey/rest/songs
	 * @param accept
	 * @return
	 */
    @GET 
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Collection<Song> getAllSongsInXML(@HeaderParam("Accept") String accept) {
		return songsDAO.getAllSongs();
	}

	/**
	 * GET http://localhost:8080/songsWS-railey/rest/songs/1
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public Response getSong(@PathParam("id") Integer id) {
		Song song = songsDAO.getSong(id);
		if (song != null) {
			return Response.status(Response.Status.OK).entity(song).build();
		} else {
		    return Response.status(Response.Status.NOT_FOUND).entity("ID not found").build();
		}
	}
	  
	/**
	 * POST http://localhost:8080/songsWS-railey/rest/songs with song in payload
	 * Status Code 201 und URI fuer den neuen Eintrag im http-header 'Location' zurueckschicken, also:
	 * Location: http://localhost:8080/songsWS-railey/rest/songs/neueID
	 * @param song
	 * @return
	 */
	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response createSong(Song song) {
		try {
			if (song.getTitle() == null || song.getTitle().isEmpty() 
					|| song.getArtist() == null || song.getArtist().isEmpty()
					|| song.getLabel() == null || song.getLabel().isEmpty()
					|| song.getReleased() == null) {
				return Response.status(400).build();
			}
			Integer newId = songsDAO.addSong(song);
			if (newId == null) {
				 return Response.status(400).build();
			}
		    UriBuilder uriBuilder = uriInfo.getAbsolutePathBuilder();
		    uriBuilder.path(Integer.toString(newId));
		    return Response.created(uriBuilder.build()).entity("New song was added to database. Here is the location: " + uriBuilder).build();
		}
		catch (Exception e) {
			return Response.status(400).build();
		}   
	}
	
	/**
	 * If Song id in payload and URL must be the same, then Status Code 400
	 * When update succeeded, return Status Code 204
	 * @param id
	 * @param song
	 * @return
	 */
	@PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Path("/{id}")
    public Response updateSong(@PathParam("id") Integer id, Song song) {
		try {
			if (!song.getId().toString().equals(id.toString()))
				return Response.status(400).entity("Song id in payload and URL must be the same.").build();
			
			if (song.getTitle() == null || song.getTitle().isEmpty() 
					|| song.getArtist() == null || song.getArtist().isEmpty()
					|| song.getLabel() == null || song.getLabel().isEmpty()
					|| song.getReleased() == null) {
				return Response.status(400).build();
			}
			if (songsDAO.updateSong(id, song))
				return Response.status(204).entity("Update song succeeded").build();
			else return Response.status(400).build();
		}
		catch (Exception e) {
			return Response.status(400).build();
		}
    }
	
	@DELETE
	@Path("/{id}")
	public Response deleteSong(@PathParam("id") Integer id) {
		if (songsDAO.deleteSong(id) == null) {
			return Response.status(400).entity("Song cannot be deleted").build();
		}
		else return Response.status(204).entity("Delete song succeeded").build();
	}
}