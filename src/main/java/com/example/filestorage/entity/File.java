package com.example.filestorage.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "files")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String userId;

    private String filename;
    private String uuid;
    private String extension;

    private String sourceLang;
    private String targetLang;

    private int pageAmount;

    @Enumerated(EnumType.STRING)
    private Status status;

    private long filesize;

    private boolean deleted = false;

    @OneToMany(mappedBy = "file", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TargetFile> targetFiles = new HashSet<>();

    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
