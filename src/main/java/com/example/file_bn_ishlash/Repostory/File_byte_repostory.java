package com.example.file_bn_ishlash.Repostory;

import com.example.file_bn_ishlash.Entity.File_Info;
import com.example.file_bn_ishlash.Entity.File_byte;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface File_byte_repostory extends JpaRepository<File_byte,Integer> {

    Optional<File_Info> findByFileinfoId(Integer fileinfo_id);
}
