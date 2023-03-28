package com.example.filestorage.repository;

import com.example.filestorage.entity.TargetFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetFileRepository extends JpaRepository<TargetFile, Long> {
}
