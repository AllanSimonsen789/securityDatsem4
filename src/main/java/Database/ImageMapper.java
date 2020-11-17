package Database;

import com.cloudinary.*;
import com.cloudinary.utils.ObjectUtils;

import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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

    private static final String UPLOAD_DIR = "img";
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static String working_dir = null;
    private static Cloudinary cloudinary;


    public ImageMapper() {
        System.out.println("Hello Not working");
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
        System.out.println("Not working");
    }

    public String uploadProfilePic(List<Part> parts) throws ImageException {
        String returnString = "";
        try {
            //Creates img folder if none exist(temporary storage for image before uploaded to cloudinary)
            File uploadFolder = new File(working_dir + File.separator + UPLOAD_DIR);
            if (!uploadFolder.exists()) {
                uploadFolder.mkdirs();
            }

            for (Part part : parts) {
                if (part.getContentType() != null && part.getSize() > 0) {
                    String fileName = "tmpRandomGeneratedFileNameNeeded.PNG";
                    String contentType = part.getContentType();

                    // allows JPEG & PNG files to be uploaded
                    if (contentType != null && (contentType.equalsIgnoreCase("image/jpeg")
                            || contentType.equalsIgnoreCase("image/png"))) {
                        part.write(working_dir + File.separator + UPLOAD_DIR
                                + File.separator + fileName);
                        File file = new File(working_dir + File.separator
                                + UPLOAD_DIR + File.separator + fileName);

                        Map uploadResult = null;
                        String URL = null;
                        Boolean bool = false;

                        uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

                        returnString = (String) uploadResult.get(new String("url"));

                        file.delete();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new ImageException("Could not upload the chosen pictures. "
                    + "Please make sure they are JPEG or PNG and try again.");
        }
        return returnString;
    }
}
