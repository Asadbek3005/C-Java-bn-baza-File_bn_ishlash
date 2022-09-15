package com.example.file_bn_ishlash.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class File_folder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String fileorginalnomi;

    @Column(nullable = false)
    private long filehajmi;

    @Column(nullable = false)
    private String fileturi;
    @Column(nullable = false)
    private String yangi_file;
}
