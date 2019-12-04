package htwb.ai.railey.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import javax.servlet.ServletException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletConfig;

import htwb.ai.railey.controller.SongsServletTest;

class SongsServletTest {
	private SongsServlet servlet;
    private MockServletConfig config;
    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private static JSONArray uriToDB;

    @BeforeEach
    public void setUp() throws ServletException {
        servlet = new SongsServlet();
        request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        config = new MockServletConfig();
        config.addInitParameter("songsJSONFilePath", "/testSongs.json");
        servlet.init(config);
        uriToDB = servlet.getUriToDB();
    }

    @Test
    public void initTest() {
    	assertEquals(uriToDB.length(), 4);
    	uriToDB.forEach(song -> {
    		assertEquals(((JSONObject) song).length(), 5);
    		assertTrue(((JSONObject) song).has("id"));
    		assertTrue(((JSONObject) song).has("title"));
    		assertTrue(((JSONObject) song).has("artist"));
    		assertTrue(((JSONObject) song).has("label"));
    		assertTrue(((JSONObject) song).has("released"));	
		});
    }
    
    @Test
    public void initTestObjectNumber4InJSonFile() {
    	uriToDB.forEach(song -> {
    		if (((JSONObject) song).get("id").toString().equals("1")) {
    			assertEquals(((JSONObject) song).get("id").toString(), "1");
        		assertEquals(((JSONObject) song).get("title"), "Can’t Stop the Feeling");
        		assertEquals(((JSONObject) song).get("artist"), "Justin Timberlake");
        		assertEquals(((JSONObject) song).get("label"), "Virgin");
        		assertEquals(((JSONObject) song).get("released").toString(), "2016");	
    		}
		});
    }
    
    @Test
    public void doGetJSONObject() throws ServletException, IOException {
        request.addParameter("songId", "4");
        request.addHeader("accept", "application/json");
        servlet.doGet(request, response);

        assertEquals("application/json", response.getContentType());
        assertTrue(response.getContentAsString().contains("{\"artist\":\"Fall Out Boy, Missy Elliott\",\"id\":4,\"label\":\"Virgin\",\"title\":\"Ghostbusters (I'm not a fraid)\",\"released\":2016}"));
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void doGetXMLObject() throws ServletException, IOException {
        request.addParameter("songId", "3");
        request.addHeader("accept", "application/xml");
        servlet.doGet(request, response);

        assertEquals("application/xml", response.getContentType());
        assertTrue(response.getContentAsString().contains("<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n" + 
        		"<root><artist>Iggy Azalea</artist><id>3</id><label>Virgin</label><title>Team</title><released>2016</released></root>"));
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void doGetJSONObjectWithEmptyHeader() throws ServletException, IOException {
        request.addParameter("songId", "2");
        request.addHeader("accept", "*/*");
        servlet.doGet(request, response);

        assertEquals("application/json", response.getContentType());
        assertTrue(response.getContentAsString().contains("{\"artist\":\"Meghan Trainor, Kelli Trainor\",\"id\":2,\"label\":\"Virgin\",\"title\":\"Mom\",\"released\":2016}"));
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void doGetJSONObjectWithOtherHeader() throws ServletException, IOException {
        request.addParameter("songId", "6");
        request.addHeader("accept", "plain/text");
        servlet.doGet(request, response);

        assertEquals(406, response.getStatus());
    }
    
    @Test
    public void doGetJSONObjectWithAlphabetAlsParameter() throws ServletException, IOException {
        request.addParameter("songId", "s");
        request.addHeader("accept", "*/*");
        servlet.doGet(request, response);

        assertEquals(400, response.getStatus());
    }
    
    @Test
    public void doGetJSONObjectWithWrongParameter() throws ServletException, IOException {
        request.addParameter("hi", "");
        request.addHeader("accept", "*/*");
        servlet.doGet(request, response);

        assertEquals(400, response.getStatus());
    }
    
    @Test
    public void doGetJSONObjectWithoutParameter() throws ServletException, IOException {
        request.addParameter("", "");
        request.addHeader("accept", "*/*");
        servlet.doGet(request, response);

        assertEquals(400, response.getStatus());
    }

    @Test
    public void doGetJSONObjectParameterWithSongId11() throws ServletException, IOException {
        request.addParameter("songId", "11");
        request.addHeader("accept", "*/*");
        servlet.doGet(request, response);

        assertEquals(404, response.getStatus());
    }
    
    @Test
    public void doGetJSONObjectParameterWithSongId0() throws ServletException, IOException {
        request.addParameter("songId", "0");
        request.addHeader("accept", "*/*");
        servlet.doGet(request, response);

        assertEquals(404, response.getStatus());
    }
    
    
}
