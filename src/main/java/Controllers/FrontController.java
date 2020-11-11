//package Controllers;
//
//import java.io.IOException;
//import javax.servlet.annotation.MultipartConfig;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.codehaus.plexus.util.StringUtils;
//
//@WebServlet(name = "FrontController", urlPatterns = {"/FrontController"})
//@MultipartConfig(fileSizeThreshold = 500000, // 0.5 MB
//        maxFileSize = 1048576L, // 1 MB
//        maxRequestSize = 5242880 // 5 MB
//)
//
//public class FrontController {
///*
//
//    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        try {
//            Command cmd = Command.from(request);
//            String view = cmd.execute(request, response);
//            switch (view) {
//                case "index":
//                    request.getRequestDispatcher(view + ".jsp").forward(request, response);
//                    break;
//                /*case "/download":
//                    request.getRequestDispatcher("download").forward(request, response);
//                    break;*/
//                default:
//                    request.getRequestDispatcher("/WEB-INF/" + view + ".jsp").forward(request, response);
//                    break;
//            }
//        } catch (CommandException ex) {
//            request.setAttribute("error", ex.getMessage());
//            //TODO: Forward to a view or   perhaps an error page instead of index
//            String s = (String) request.getAttribute("returnPage");
//            if (s != null && StringUtils.isNotBlank(s)) {
//                request.getRequestDispatcher("/WEB-INF/" + s + ".jsp").forward(request, response);
//            } else {
//                request.getRequestDispatcher("index.jsp").forward(request, response);
//            }
//        }catch (UnsupportedOperationException e) {
//            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
//
//    }
//
//    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
//    /**
//     * Handles the HTTP <code>GET</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        processRequest(request, response);
//    }
//
//    /**
//     * Returns a short description of the servlet.
//     *
//     * @return a String containing servlet description
//     */
//    @Override
//    public String getServletInfo() {
//        return "Short description";
//    }// </editor-fold>
//
//}
//
//}
