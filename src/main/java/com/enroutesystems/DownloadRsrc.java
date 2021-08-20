package com.enroutesystems;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DownloadRsrc
 */
@WebServlet("/enroutelogo")
public class DownloadRsrc extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public DownloadRsrc() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		// reads input file from an absolute path
        String filePath = "/Users/cristianramirez/Downloads/enroute-logo.png";
        File downloadFile = new File(filePath);
        FileInputStream inStream = new FileInputStream(downloadFile);
         
        // if you want to use a relative path to context root:
        String relativePath = getServletContext().getRealPath("");
        System.out.println("relativePath = " + relativePath);
         
        // obtains ServletContext
        ServletContext context = getServletContext();
         
        // gets MIME type of the file
        String mimeType = context.getMimeType(filePath);
        if (mimeType == null) {        
            // set to binary type if MIME mapping not found
            mimeType = "application/octet-stream";
        }
        System.out.println("MIME type: " + mimeType);
         
        // modifies response
        response.setContentType(mimeType);
        response.setContentLength((int) downloadFile.length());
         
        // forces download
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
        response.setHeader(headerKey, headerValue);
         
        // obtains response's output stream
        OutputStream outStream = response.getOutputStream();
         
        byte[] buffer = new byte[4096];
        int bytesRead = -1;
         
        while ((bytesRead = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, bytesRead);
        }
         
        inStream.close();
        outStream.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter output = response.getWriter();
		
		String data = "El método HTTP POST envía datos al servidor. El tipo del cuerpo de la solicitud es indicada por la cabecera  Content-Type."
				+ "La diferencia entre PUT y POST es que PUT es idempotente: llamarlo una o varias veces sucesivamente tiene el mismo efecto (no tiene efecto secundario colateral), mientras que varios POST idénticos pueden tener efectos adicionales, como pasar una orden muchas veces.";
		
		output.println("<html><body>");
		output.println("<h1>Downloads Servlet</h1>");
		output.println("Método ejecutado: POST\n" + data);
		output.println("Valor recibido: " + request.getParameter("value"));
		output.println("</body></html>");
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		boolean deleted = false;
		
		String filename = req.getParameter("filename");
		
		File myfile = new File(filename); 
	    if (myfile.delete()) { 
	      System.out.println("Deleted the file: " + myfile.getName());
	      deleted = true;
	    } else {
	      System.out.println("Failed to delete the file.");
	    }
	    
	    String data = "Este método es utilizado para solicitar al servidor que elimine un archivo en una ubicación específica dada por la URL. En otras palabras más simples, este método elimina un recurso determinado.";
	    
	    
	    PrintWriter output = resp.getWriter();
	    output.println("<html><body>");
		output.println("<h1>Downloads Servlet</h1>");
		output.println("Método ejecutado: DELETE\n" + data);
		output.println("Valor recibido: " + req.getParameter("filename"));
		output.println("El valor" + ((deleted) ? "" : " no") + " se eliminó");
		output.println("</body></html>");
	}

	@Override
	protected void doHead(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String data = "El método HEAD pide una respuesta idéntica a la de una petición GET, pero sin el cuerpo de la respuesta.";
		resp.addHeader("CUSTOM_HEADER", data);
	}

	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String data = "El método OPTIONS representa una solicitud de información acerca de las opciones de comunicación disponibles en el canal de solicitud/respuesta identificada por el Request-URI. En otras palabras, éste método es el que utilizamos para describir las opciones de comunicación existentes de un recurso destino.";
		resp.addHeader("CUSTOM_HEADER", data);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String data = "La petición HTTP PUT crea un nuevo elemento o reemplaza una representación del elemento de destino con los datos de la petición.\n"
				+ "La diferencia entre el método PUT y el método POST es que PUT es un método idempotente: llamarlo una o más veces de forma sucesiva tiene el mismo efecto (sin efectos secundarios), mientras que una sucesión de peticiones POST idénticas pueden tener efectos adicionales, como envíar una orden varias veces.";
		PrintWriter output = resp.getWriter();
	    output.println("<html><body>");
		output.println("<h1>Downloads Servlet</h1>");
		output.println("Método ejecutado: PUT\n" + data);
		output.println("Valor recibido: " + req.getParameter("value"));
		output.println("</body></html>");
	}

	@Override
	protected void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String data = "El método HTTP TRACE  efectua una prueba de bucle de mensaje por el camino al recurso objetivo proporcionando un útil mecanismo de debugging."
				+ "\n"
				+ "El destino final de la petición debería devolver el mensaje recibido, excluyendo algunos de los campos descritos abajo, de vuelta al cliente como el mensaje body e una respuesta 200 (OK) con un Content-Type de  message/http. El destinatario final es o el servidor de origen o el priver servidor en recivir un Max-Forwards de valor 0 en la petición.";
		PrintWriter output = resp.getWriter();
	    output.println("<html><body>");
		output.println("<h1>Downloads Servlet</h1>");
		output.println("Método ejecutado: TRACE\n" + data);
		output.println("</body></html>");
	}

}
