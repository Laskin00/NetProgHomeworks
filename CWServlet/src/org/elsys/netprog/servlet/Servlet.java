package org.elsys.netprog.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sun.misc.IOUtils;


/**
 * Servlet implementation class Servlet
 */
@WebServlet("/Servlet/*")
public class Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static List<String> keys = new ArrayList<String>();
	private static List<String> values = new ArrayList<String>();

    /**
     * Default constructor. 
     */
    public Servlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		if(keys.contains(request.getPathInfo().toString())) {
			response.getOutputStream().println(keys.get(keys.indexOf(request.getPathInfo().split("/")[1])));
			response.getOutputStream().println(values.get(keys.indexOf(request.getPathInfo().split("/")[1])));
		}else {
			throw new  IOException("No such key");
		}
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		
		String parsedd = parseJson(request,response);
		
		String[] parsed = parsedd.split(",");
		
		if(keys.contains(parsed[0])) {
			throw new IOException("Key already added");
		}
		keys.add(parsed[0]);
		values.add(parsed[1]);
		
		response.getOutputStream().println(parsed[0]);
		response.getOutputStream().println(parsed[1]);
		//not working

	}
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		if(keys.contains(request.getPathInfo().split("/")[1])) {
			keys.remove(request.getPathInfo().split("/")[1]);
		}else {
			throw new IOException("No such key");
		}
	}
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		if(keys.contains(request.getPathInfo().split("/")[1])) {
			values.set(keys.indexOf(request.getPathInfo().split("/")[1]), request.getParameter("value"));
		}else {
			throw new IOException("No key to change its value");
		}
	}
	
	protected String parseJson(HttpServletRequest req, HttpServletResponse rep) throws IOException {
		BufferedReader reader = req.getReader();
		String parsing = " ";
		String anotherone = " ";
		while((anotherone = reader.readLine()) != null) {
			parsing  = parsing + anotherone;
		}
		
		reader.close();
		
		String parsingg = parsing.replace(":", ",").replace('"', ',').replace("{",",").replace("}", ",");
		
		
		return parsingg;
	}

}
