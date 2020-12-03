package Controllers;

import Database.ImageMapper;
import Database.UserMapper;
import ExtraClasses.SecureRandomString;
import ExtraClasses.SessionHelper;
import model.User;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

import Exception.ImageException;
import Exception.IllegalArgumentException;

@WebServlet(name = "ProfileController")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 1, // 1 MB
        maxRequestSize = 1024 * 1024 * 100 // 100 MB
)
public class ProfileController extends HttpServlet {
    private final ImageMapper im = new ImageMapper();
    private static final String UPLOAD_DIR = "securityImg";
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static String working_dir = null;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!SecureRandomString.validateSecureString(request.getParameter("web_token"))) {
            HttpSession session = request.getSession(false);
            session.invalidate();
            request.setAttribute("errorMessage", "Web tokens are NOT equal");
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else {
            //Create new web_csrf_token.
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
        }
        String returnString = "";
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("username");
        if (sessionUser != null) {
            File img_file = null;
            try {
                // File content
                Part filePart = request.getPart("fileContent");
                if ((filePart == null) || (filePart.getInputStream().available() == 0)) {//.available, checks how much data is accsesible in the filePart.
                    throw new IllegalArgumentException("Unknown file content specified!");
                    //Send to error page.
                }
                //Find the file suffix
                String fileSuffix = fileSuffixFinder(filePart);

                //Instanciate where we are check.
                if (working_dir == null) workingDirectoryCheck();

                // Create a temporary file.
                // Copy the uploaded image to the temp file.
                File directory = new File(working_dir + File.separator + UPLOAD_DIR);
                img_file = File.createTempFile("temp", fileSuffix, directory);
                Files.copy(filePart.getInputStream(), img_file.toPath(), StandardCopyOption.REPLACE_EXISTING);

                //Image decode check.
                ImageInputStream iis = ImageIO.createImageInputStream(img_file);
                Iterator<ImageReader> iir = ImageIO.getImageReaders(iis);
                //Check the mimetype. By checking what image reader can decode our object. If not png or jpg/jpeg, throw error.
                String mimeType = "";
                while (iir.hasNext()){
                    ImageReader reader = (ImageReader) iir.next();
                    mimeType = reader.getFormatName();
                }
                //Cleanup
                iis.close();

                if (mimeType.toLowerCase().equals("png") || mimeType.toLowerCase().equals("jpeg") || mimeType.toLowerCase().equals("jpg")){
                    UserMapper um = UserMapper.getInstance();
                    returnString = im.uploadProfilePic(img_file);
                    um.setProfilePic(sessionUser.getUserID(), returnString);
                    sessionUser.setImageURL(returnString);
                } else {
                    throw new ImageException("Unsupported image type! Must be JPG, JPEG or PNG.");
                }
            } catch (IllegalArgumentException e) {
                request.setAttribute("errorMessage", e.getMessage());
            } catch (ImageException e) {
                request.setAttribute("errorMessage", e.getMessage());
            } catch (IOException e){
                request.setAttribute("errorMessage", "Something went wrong, try again later.");
            } finally {
                //Cleanup
                if (img_file != null){
                    img_file.delete();
                }
            }
            request.setAttribute("user", sessionUser);
            request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
        } else {
            //Not logged in.
            session = request.getSession(false);
            session.invalidate();
            request.setAttribute("errorMessage", "Login to see profile page");
            //Create new web_csrf_token.
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
            response.sendRedirect("/login");
        }
    }

    private String fileSuffixFinder(Part part) throws ImageException {
        String contentType = part.getContentType();
        //Check the file suffix, and return it.
        switch (contentType.toLowerCase()) {
            case "image/jpeg":
                return ".jpeg";
            case "image/png":
                return ".png";
            default:
                throw new ImageException("Unsupported image type! Must be JPG, JPEG or PNG.");
        }
    }

    private void workingDirectoryCheck() {
        if (OS.contains("win") || OS.contains("mac")) {
            working_dir = System.getProperty("user.dir");
        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            working_dir = System.getProperty("catalina.base");
        } else {
            working_dir = "";
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Rotate session ID, with the same user.
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute("username");

        if (sessionUser != null && SecureRandomString.validateSecureString(request.getParameter("web_token"))) {
            SessionHelper.rotateSessionIDWithUser(session, request, sessionUser);
            request.setAttribute("user", sessionUser);
            //Create new web_csrf_token.
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
            request.getRequestDispatcher("/WEB-INF/profilePage.jsp").forward(request, response);
        } else {
            //Not logged in.
            session = request.getSession(false);
            session.invalidate();
            request.setAttribute("errorMessage", "Login to see profile page");
            //Create new web_csrf_token.
            request.setAttribute("web_csrf_token", SecureRandomString.genSecureRandomString());
            response.sendRedirect("/login");
        }
    }
}
