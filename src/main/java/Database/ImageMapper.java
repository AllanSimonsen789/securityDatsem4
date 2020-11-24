package Database;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Map;
import java.util.Properties;

import Exception.ImageException;

/**
 * The purpose of the ImageMapper is to save the images in cloudinary and in the
 * database and to delete the stored images when necessary
 *
 * @author allan
 */
public class ImageMapper {

    private static final String UPLOAD_DIR = "securityImg";
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static String working_dir = null;
    private static Cloudinary cloudinary;


    public ImageMapper() {
        if (OS.contains("win") || OS.contains("mac")) {
            working_dir = System.getProperty("user.dir");
        } else if (OS.contains("nix") || OS.contains("nux") || OS.contains("aix")) {
            working_dir = System.getProperty("catalina.base");
        } else {
            working_dir = "";
        }
        try (InputStream prob = ImageMapper.class.getResourceAsStream("/cloudinary.properties");) {
            Properties pros = new Properties();
            pros.load(prob);

            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", pros.getProperty("cloud_name"),
                    "api_key", pros.getProperty("api_key"),
                    "api_secret", pros.getProperty("api_secret")));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String uploadProfilePic(Part part) throws ImageException, IOException {
        String returnString = "";
        if (part.getContentType() != null && part.getSize() > 0) {
            String contentType = part.getContentType();
            // allows JPEG & PNG files to be uploaded
            // Check mime type. <- getContentType is the same headder as Mime type.
            // Check image type, and create temporary file.
            String fileSuffix = "";
            switch (contentType.toLowerCase()) {
                case "image/jpeg":
                    fileSuffix = ".jpeg";
                    break;
                case "image/png":
                    fileSuffix = ".png";
                    break;
                default:
                    throw new ImageException("Unsupported image type! Must be JPG, JPEG or PNG.");
            }
            // Create a temporary file.
            // Copy the uploaded image to the temp file.
            // Upload the image to cloudinary.
            // Delete the local file.
            File directory = new File(working_dir + File.separator + UPLOAD_DIR);
            File file = File.createTempFile("temp", fileSuffix, directory);
            Files.copy(part.getInputStream(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
            Map uploadResult = null;
            try{
            uploadResult = cloudinary.uploader().upload(file, Cloudinary.asMap("upload_preset", "ml_default"));
            } catch (RuntimeException e){
                file.delete();
                throw new ImageException("Unsupported image type! Must be JPG, JPEG or PNG.");
            }
            returnString = (String) uploadResult.get("url");
            file.delete();
        }
        return returnString;
    }
}
