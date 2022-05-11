package dispatcher;


import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.disk.*;
import org.apache.commons.fileupload.servlet.*;
import services.logservice.LogService;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.lang.reflect.Method;
import java.rmi.ServerException;
import java.util.List;
import java.util.logging.*;

@WebServlet(name = "Dispatcher", urlPatterns = {"/Dispatcher"})
//@MultipartConfig
public class Dispatcher extends HttpServlet {

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    Logger logger = LogService.getApplicationLogger();

    try {

      String controllerAction = null;

      boolean isMultipart = ServletFileUpload.isMultipartContent(request);
      if (!isMultipart) {
        controllerAction = request.getParameter("controllerAction");
        logger.log(Level.INFO, "ControllAction: " + controllerAction);
      } else {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);
        request.setAttribute("items",items);
        for (FileItem item:items) {
          if (item.isFormField() && item.getFieldName().equals("controllerAction")) {
            LogService.getApplicationLogger().log(Level.INFO, "ControllAction: " + controllerAction);
            controllerAction=item.getString();
          }
        }
      }

      //String controllerAction = request.getParameter("controllerAction");

      if (controllerAction == null) controllerAction = "HomeManagement.view";

      String[] splittedAction = controllerAction.split("\\.");
      Class<?> controllerClass = Class.forName("controller." + splittedAction[0]);
      Method controllerMethod = controllerClass.getMethod(splittedAction[1], HttpServletRequest.class, HttpServletResponse.class);
      LogService.getApplicationLogger().log(Level.INFO,splittedAction[0]+" "+splittedAction[1]);
      controllerMethod.invoke(null, request, response);

      String viewUrl = (String) request.getAttribute("viewUrl");
      RequestDispatcher view = request.getRequestDispatcher("jsp/" + viewUrl + ".jsp");
      view.forward(request, response);


    } catch (Exception e) {
      logger.log(Level.SEVERE, "Dispatcher Error", e);
      e.printStackTrace(out);
      throw new ServerException("Dispatcher Servlet Error", e);
    } finally {
      out.close();
    }
  }

  // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

  /**
   * Handles the HTTP
   * <code>GET</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Handles the HTTP
   * <code>POST</code> method.
   *
   * @param request  servlet request
   * @param response servlet response
   * @throws ServletException if a servlet-specific error occurs
   * @throws IOException      if an I/O error occurs
   */
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    processRequest(request, response);
  }

  /**
   * Returns a short description of the servlet.
   *
   * @return a String containing servlet description
   */
  @Override
  public String getServletInfo() {
    return "Short description";
  }// </editor-fold>
}
