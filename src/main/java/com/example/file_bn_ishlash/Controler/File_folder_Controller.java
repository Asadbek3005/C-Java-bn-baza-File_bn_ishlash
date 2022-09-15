package com.example.file_bn_ishlash.Controler;

import com.example.file_bn_ishlash.Entity.File_folder;
import com.example.file_bn_ishlash.Repostory.File_folder_repostory;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/info")
public class File_folder_Controller {

    @Autowired
    File_folder_repostory file_folder_repostory;
    String manzil="src\\main\\resources\\Baza\\";
    @PostMapping("/a")
    public  String Papkaga(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fileNames = request.getFileNames();
        MultipartFile file = request.getFile(fileNames.next());
        if (file!=null){
            File_folder file_folder=new File_folder();
            file_folder.setFileorginalnomi(file.getOriginalFilename());
            file_folder.setFileturi(file.getContentType());
            file_folder.setFilehajmi(file.getSize());
            System.out.println("Shart bajarildi");
            String[] newname=file.getOriginalFilename().split("\\.");
            String Readom= UUID.randomUUID().toString()+"."+newname[newname.length-1];
            file_folder.setYangi_file(Readom);
            file_folder_repostory.save(file_folder);
            Path path= Paths.get(manzil+Readom);
            Files.copy(file.getInputStream(),path);
            System.out.println(Arrays.toString(newname));
           return "Joylandi";
        }
        return "Joylamadi";
    }
    @GetMapping("/b/{id}")
       public   void Yuklash(@PathVariable Integer id, HttpServletResponse respons) throws IOException {
        Optional<File_folder> byId = file_folder_repostory.findById(id);
        if (byId.isPresent()){
            File_folder file_folder = byId.get();
            respons.setContentType(file_folder.getYangi_file());
            respons.setHeader("Content-Disposition","attachment; filename=\""+ file_folder.getYangi_file() +"\"");
            FileInputStream inputStream=new FileInputStream(manzil+file_folder.getYangi_file());
            FileCopyUtils.copy(inputStream,respons.getOutputStream());
        }
    }
}
