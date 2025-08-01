package com.example.datajpa.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name= "medias")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true,nullable = false,length = 100)
    private String name;

    @Column(nullable = false, length = 20)
    private String extension;

    @Column(nullable = false, length = 20)
    private String mimeTypeFile;

    @Column(nullable = false)
    private Boolean isDeleted;

}
