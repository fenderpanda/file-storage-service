package com.example.filestorage.repository;

import com.example.filestorage.entity.File;
import com.example.filestorage.entity.Status;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FileRepository extends JpaRepository<File, Long> {
    Page<File> findAllByUserIdAndDeletedFalse(String userId, Pageable pageable);
    Optional<File> findByIdAndUserId(long fileId, String userId);

    @Query("select f.pageAmount from File f where f.id = :id and f.userId = :userId")
    int getFileCost(@Param(value = "id") long fileId,
                    @Param(value = "userId") String userId);

    @Query("select f.status from File f where f.id = :id and f.userId = :userId")
    String getStatus(@Param(value = "id") long fileId,
                     @Param(value = "userId") String userId);

    @Modifying
    @Transactional
    @Query("update File f set f.status = :status where f.id = :id and f.userId = :userId")
    void updateStatus(@Param(value = "id") long fileId,
                      @Param(value = "userId") String userId,
                      @Param(value = "status")Status status);

    @Modifying
    @Transactional
    @Query("update File f set f.deleted = true where f.id =:id and f.userId = :userId")
    void markDeleted(@Param(value = "id") long id,
                     @Param(value = "userId") String userId);
}
