package com.example.file_bn_ishlash.Controler;

import com.example.file_bn_ishlash.Entity.File_Info;
import com.example.file_bn_ishlash.Entity.File_byte;
import com.example.file_bn_ishlash.Repostory.File_byte_repostory;
import com.example.file_bn_ishlash.Repostory.File_info_repostory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Optional;

@RestController
public class File_Controller {
    @Autowired
     File_byte_repostory file_byte_repostory;

    @Autowired
    File_info_repostory file_info_repostory;

    @RequestMapping(value = "/file",method = RequestMethod.POST)
    public String Yuklash(MultipartHttpServletRequest request) throws IOException {
        Iterator<String> fayl = request.getFileNames();
        MultipartFile file = request.getFile(fayl.next());
        File_Info file_info = new File_Info();
        file_info.setFileorginalnomi(file.getOriginalFilename());
        file_info.setFileturi(file.getContentType());
        file_info.setFilehajmi(file.getSize());
        File_Info save = file_info_repostory.save(file_info);

        File_byte file_byte = new File_byte();
        file_byte.setBytes(file.getBytes());
        file_byte.setFileinfo(save);
        file_byte_repostory.save(file_byte);
        return "file yuklandi";



    }

    @GetMapping("/downloadFile/{id}")
    public void DownloadFile(@PathVariable Integer id, HttpServletResponse response) throws IOException {
        Optional<File_Info> byId = file_info_repostory.findById(id);
        if(byId.isPresent()){
            File_Info file_info = byId.get();
            Optional<File_byte> byId1 = file_byte_repostory.findById(file_info.getId());
            if(byId1.isPresent()){
                File_byte file_byte = byId1.get();
                response.setContentType(file_info.getFileturi());
                response.setHeader("Content-Disposition","attachment; filename=\""+ file_info.getFileorginalnomi()+"\"");
                FileCopyUtils.copy(file_byte.getBytes(),response.getOutputStream());

            }
        }
    }

}
