package com.steveq.profile;

import com.steveq.config.PictureUploadProperties;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created by Adam on 2017-05-18.
 */
@Controller
@SessionAttributes(value = "picturePath")
public class PictureUploadController {
    private final Resource picturesDir;
    private final Resource anonymousPicture;

    @ModelAttribute("picturePath")
    public Resource picturePath(){
        return anonymousPicture;
    }

    public PictureUploadController(@Autowired PictureUploadProperties uploadProperties){
        picturesDir = uploadProperties.getUploadPath();
        anonymousPicture = uploadProperties.getAnonymousPicture();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadImage(){
        return "profile/uploadPage";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttributes, Model model) throws IOException{
        if(file.isEmpty() || !isImage(file)){
            redirectAttributes.addFlashAttribute("error", "Niewlasciwy plik. Zaladuj plik z obrazem.");
            return "redirect:/upload";
        } else {
            Resource picturePath = copyFileToPictures(file);
            model.addAttribute("picturePath", picturePath);
            System.out.println("MODEL ATTRIBUTE : " + picturePath);
            return "profile/uploadPage";
        }
    }

    @RequestMapping(value = "/uploadedPicture")
    public void getUploadedPicture(HttpServletResponse response, @ModelAttribute("picturePath") Path picturePath) throws IOException{
        response.setHeader("Content-Type", URLConnection.guessContentTypeFromName(picturePath.toString()));
        System.out.println("UPLOADED PICTURE : " + picturePath);
        Files.copy(picturePath, response.getOutputStream());
    }

    private Resource copyFileToPictures(MultipartFile file) throws IOException{
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic", fileExtension, picturesDir.getFile());
        try(InputStream is = file.getInputStream();
            OutputStream os = new FileOutputStream(tempFile)){
            IOUtils.copy(is, os);
        }
        return new FileSystemResource(tempFile);
    }

    private boolean isImage(MultipartFile file){
        return file.getContentType().startsWith("image");
    }

    private static String getFileExtension(String name){
        return name.substring(name.lastIndexOf("."));
    }
}
