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

    private static Cloudinary cloudinary;


    public ImageMapper() {
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

    public String uploadProfilePic(File file) throws ImageException, IOException {
        Map uploadResult = null;
        try {
            uploadResult = cloudinary.uploader().upload(file, ObjectUtils.asMap("upload_preset", "ml_default"));
        } catch (RuntimeException e) {
            throw new ImageException("Unsupported image type! Must be JPG, JPEG or PNG.");
        }
        return (String) uploadResult.get("url");
    }
}
