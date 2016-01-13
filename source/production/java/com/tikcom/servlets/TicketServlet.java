package com.tikcom.servlets;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.tikcom.dictionaries.HttpContentTypes;
import com.tikcom.domain.Attachment;
import com.tikcom.domain.Ticket;
import com.tikcom.utils.url.ServletUrlPaths;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

@WebServlet(name = "ticketServlet", 
urlPatterns = { "/tickets" }, 
loadOnStartup = 1)

@MultipartConfig(fileSizeThreshold = 5_242_880, // 5MB after this it gets garbage collected from memory and stored to disk, better work with filesys
maxFileSize = 20_971_520L, // 20MB
maxRequestSize = 41_943_040L // 40MB
)
public class TicketServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	//for now, without persistence, ticket sequence static shared value
	private volatile int TICKET_ID_SEQUENCE = 1;

	private Map<Integer, Ticket> ticketDatabase = new LinkedHashMap<>();

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			this.showTicketForm(request,response);
			break;
		case "view":
			this.viewTicket(request, response);
			break;
		case "download":
			this.downloadAttachment(request, response);
			break;
		default:
			this.listTickets(request, response);
			break;
		}
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if (action == null)
			action = "list";
		switch (action) {
		case "create":
			this.createTicket(request, response);
			break;
		case "list":
		default:
			response.sendRedirect("tickets");
			break;
		}
	}

	/**
	 * This method builds hard coded html form to display the response, page html not response 
	 * headers are set in another private method
	 * */
	private void showTicketForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		//Dispatcher redirects to jsp view (servlet still with request/response processing capabilities)
		request.getRequestDispatcher("/WEB-INF/jsp/view/ticketForm.jsp").forward(request, response);
		
	}

	private void viewTicket(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String idString = request.getParameter("ticketId");
		Ticket ticket = this.getTicket(idString, response);
		
		if (ticket == null)
			return;
		
		request.setAttribute("ticketId", idString);
		request.setAttribute("ticket", ticket);
		
		request.getRequestDispatcher("/WEB-INF/jsp/view/viewTicket.jsp").forward(request, response);
	}

	private void downloadAttachment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//get ticket id of attachement from req param
		String ticketId = request.getParameter("ticketId");
		
		//Get the related ticket
		Ticket ticket = this.getTicket(ticketId, response); 
		
		//Ticket does not exist return from method, no exception thrown for now
		if(ticket == null)
			return;
		
		//Get the attachement name for the ticket
		String name = request.getParameter("attachment");
		
		//Case is null redirect to view ticket details page
		if(name == null) {
			response.sendRedirect("tickets?action=view&ticketId=" + ticketId);
			return;
		}
		
		//Brings the class for the attached file, Attachement Object
		Attachment attachment = ticket.getAttachment(name);
		
		if(attachment == null) {
			response.sendRedirect("tickets?action=view&ticketId=" + ticketId);
			return;
		}
		
		//Set the headers for the browser
		//instruct browser to ask client to save file in dialog
		response.setHeader("Content-Disposition", "attachment; filename="+attachment.getName());
		//set the content type
		response.setContentType(HttpContentTypes.FILE.getTypeValue());
		
		//get an SERVLET OUTPUT STREAM from response to build the byte response
		ServletOutputStream stream = response.getOutputStream();
		//get the file and write to servlet output stream, that is the array of bytes from the attachment pojo
		stream.write(attachment.getContents());
	}

	private void listTickets(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setAttribute("ticketDatabase", ticketDatabase);
		
		request.getRequestDispatcher("/WEB-INF/jsp/view/listTickets.jsp").forward(request, response);
		
	}

	private void createTicket(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Ticket ticket = new Ticket();
		
		//Get values from body and such since it is a POST
		ticket.setCustomerName((String) request.getSession().getAttribute("username"));
		ticket.setSubject(request.getParameter("subject"));
		ticket.setBody(request.getParameter("body"));

		//Get the part or byte handling class for uploads
		Part filePart = request.getPart("file1");
		if (filePart != null && filePart.getSize() > 0) {
			//process attachment see method below
			Attachment attachment = this.processAttachment(filePart);
			if (attachment != null)
				ticket.addAttachment(attachment);
		}

		int id;
		synchronized (this) {
			id = this.TICKET_ID_SEQUENCE++;
			this.ticketDatabase.put(id, ticket);
		}

		response.sendRedirect("tickets?action=view&ticketId=" + id);
	}

	private Attachment processAttachment(Part filePart) throws IOException {
		//get input stream from filePart uploaded in browser
		InputStream inputStream = filePart.getInputStream();
		//instantiate bytearray output stream to temporary hold bytes
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		int read;
		final byte[] bytes = new byte[1024];
		//Reads some number of bytes and stores them in the array returning amount read
		while ((read = inputStream.read(bytes)) != -1) {
			outputStream.write(bytes, 0, read);
		}

		Attachment attachment = new Attachment();
		attachment.setName(filePart.getSubmittedFileName());
		attachment.setContents(outputStream.toByteArray());

		return attachment;
	}

	//CRUD: Retrieve operation for tickets
	private Ticket getTicket(String idString, HttpServletResponse response)
			throws ServletException, IOException {
		if (idString == null || idString.length() == 0) {
			response.sendRedirect("tickets");
			return null;
		}

		try {
			Ticket ticket = this.ticketDatabase.get(Integer.parseInt(idString));
			if (ticket == null) {
				response.sendRedirect("tickets");
				return null;
			}
			return ticket;
		} catch (Exception e) {
			response.sendRedirect("tickets");
			return null;
		}
	}
}
