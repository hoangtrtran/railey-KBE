package htwb.ai.railey.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.XML;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class SongsServlet
 */
//@WebServlet("/songs")
public class SongsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private JSONArray uriToDB = null;
	String songsJSONFilePath = "";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SongsServlet() {
        super();
    }

    @Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		songsJSONFilePath = servletConfig.getInitParameter("songsJSONFilePath"); //songsJSONFilePath stays in web.xml
        try (InputStream is = getClass().getResourceAsStream(songsJSONFilePath))
        {
        	BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String readline = null;
            String jsonString = new String();
            while((readline = reader.readLine()) != null){
            	jsonString += readline;
            } 
            this.uriToDB =  new JSONArray(jsonString);
        }
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--get--");
		String songId = request.getParameter("songId");
		int checkParameterStatus = responseStatusParameterHandling(songId, response);
		String acceptHeader = ((HttpServletRequest)request).getHeader("accept");
		
		if (checkParameterStatus == 200) {
			if (acceptHeader.equals("application/xml"))
				doGetXMLObjectResponse(songId, response);
			else if (acceptHeader.equals("application/json") || (acceptHeader.equals("*/*")))
				doGetJSONObjectResponse(songId, response);
			else response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE); //406 
		}
		System.out.print("Status : " + response.getStatus());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("--put--");
		String songIdInUrl = request.getParameter("songId");
		int checkParameterStatus = responseStatusParameterHandling(songIdInUrl, response);
		//String contentType = ((HttpServletRequest)request).getContentType();
		//Notice: Content Type doesn't need to be checked because it has not been asked in the assignment.
		
		if (checkParameterStatus == 200) {
			InputStream inputStream = request.getInputStream();
			String payload = "";
			int checkPayloadStatus = 0;
	        if (inputStream != null) {
	            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
	            payload = reader.readLine();
	            checkPayloadStatus = responseStatusPayloadHandling(payload, response);
	        }
	        
	        if (checkPayloadStatus == 200) {
				JSONObject putSong = new JSONObject(payload);
				if (songIdInUrl.equals(putSong.get("id").toString()) && hasSameStructureAsJSONObjectInDatabase(putSong)) {
					modifyValueOfJSONObject(putSong);
					System.out.println(putSong);
					response.setStatus(HttpServletResponse.SC_NO_CONTENT); //204
				}
	        }
		}
		System.out.println("Status : " + response.getStatus());
	}
	

	
	/**
	 * Methode to modify the value of JSONObject in JSONArray uritoDB
	 * @param putSong
	 */
	private void modifyValueOfJSONObject(JSONObject putSong) {
		this.uriToDB.forEach(song -> {
			if ((((JSONObject) song).get("id")).equals(putSong.get("id"))) {
				((JSONObject)song).put("title", putSong.get("title"));
				((JSONObject)song).put("artist", putSong.get("artist"));
				((JSONObject)song).put("label", putSong.get("label"));
				((JSONObject)song).put("released", putSong.get("released"));
			}});
	}
	
	/**
	 * Methode to check if songId is empty or null. If not, response with status code 400.
	 * Check if songId is an integer value. If not, response with status code 404.
	 * If songId is a number, check if 1 <= songId <= 10. If not, response with status code 400.
	 * Otherwise response with succeed status 200.
	 * @param songId
	 * @param response
	 * @return
	 */
	private int responseStatusParameterHandling(String songId, HttpServletResponse response) {
		if (songId == null || songId == "") {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400
			return response.getStatus();
		}	
		try {
		    int id = Integer.parseInt(songId);
		    if (id < 1 || id > 10) {
		    	response.setStatus(HttpServletResponse.SC_NOT_FOUND); //404
		    	return response.getStatus();
		    }
		} 
		catch (NumberFormatException e) {	  
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400
			return response.getStatus();
		}
		
		return HttpServletResponse.SC_OK; //200
	}

	/**
	 * Methode to check if payload is empty or null. If not, response with status code 400.
	 * Check if payload string is a JSONObject and can be parse. If not, response with status code 400.
	 * Otherwise response with succeed status 200.
	 * @param payload
	 * @param response
	 * @return
	 */
	private int responseStatusPayloadHandling(String payload, HttpServletResponse response) {
		if (payload == null || payload == "") {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400
			return response.getStatus();
		}	
		if (!isJSONString(payload)) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); //400
			return response.getStatus();
		}
		else return HttpServletResponse.SC_OK; //200
	}
	
	/**
	 * Response JSON song Object to the user
	 * @param songId
	 * @param response
	 */
	private void doGetJSONObjectResponse(String songId, HttpServletResponse response) {
		System.out.println(uriToDB);
		this.uriToDB.forEach(song -> {
		if ((((JSONObject) song).get("id")).toString().equals(songId)) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				System.out.println(song);
				out.println(song);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}});
	}
	
	/**
	 * Response XML song Object to the user
	 * @param songId
	 * @param response
	 */
	private void doGetXMLObjectResponse(String songId, HttpServletResponse response) {
		this.uriToDB.forEach(song -> {
		if ((((JSONObject) song).get("id")).toString().equals(songId)) {
			response.setContentType("application/xml");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out;
			try {
				out = response.getWriter();
				String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>\n<root>" +XML.toString(song)+ "</root>";
				System.out.println(xml);
				out.println(xml);
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}});
	}
	
	/**
	 * Methode to check if the giving string hat JSONObject Format
	 * @param str
	 * @return
	 */
	private boolean isJSONString(String str) {
    	try {
            new JSONObject(str);
        } catch (JSONException ex) {
            try {
                new JSONArray(str);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
	
	/**
	 * Methode to check if a random Json object (for example payload) has the same structure or format at the JSonObject in songs.json 
	 * @param object
	 * @return
	 */
	private boolean hasSameStructureAsJSONObjectInDatabase(JSONObject o) {
		if (o.has("id") && o.has("title") && o.has("artist") && o.has("label") && o.has("released") && o.length() == 5)
			return true;
		else return true;
	}
	
	protected JSONArray getUriToDB () {
		return this.uriToDB;
	}
	
	// For testing purpose
	private static void printFile(File file) throws IOException {

        if (file == null) return;

        try (FileReader reader = new FileReader(file);
             BufferedReader br = new BufferedReader(reader)) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
}
