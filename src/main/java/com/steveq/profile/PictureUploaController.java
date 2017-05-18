package com.steveq.profile;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;

/**
 * Created by Adam on 2017-05-18.
 */
@Controller
public class PictureUploaController {
    public static final Resource PICTURES_DIR = new FileSystemResource("./pictures");

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String uploadImage(){
        return "profile/uploadPage";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String onUpload(MultipartFile file, RedirectAttributes redirectAttributes) throws IOException{
        if(file.isEmpty() || !isImage(file)){
            redirectAttributes.addFlashAttribute("error", "Niewlasciwy plik. Zaladuj plik z obrazem.");
            return "redirect:/upload";
        } else {
            copyFileToPictures(file);
            return "profile/uploadPage";
        }
    }

    private Resource copyFileToPictures(MultipartFile file) throws IOException{
        String fileExtension = getFileExtension(file.getOriginalFilename());
        File tempFile = File.createTempFile("pic", fileExtension, PICTURES_DIR.getFile());
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
