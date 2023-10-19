package com.example.board.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_jpa_file")
@NoArgsConstructor
@Data
public class BoardFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idx;

    @Column(nullable = false)
    private String originalFileName;

    @Column(nullable = false)
    private String storedFilePath;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false)
    private String creatorId;

    @Column(nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Column
    private String updaterId;

    @Column
    private LocalDateTime updatedDateTime;

    @Builder
    public BoardFileEntity (String originalFileName, String storedFilePath, Long fileSize, String creatorId) {
        Assert.notNull(originalFileName, "파일 이름값이 없습니다.");
        Assert.notNull(storedFilePath, "파일 저장 경로가 없습니다.");
        Assert.notNull(fileSize, "파일 크기가 없습니다.");
        Assert.notNull(creatorId, "파일 등록자가 없습니다.");


        this.originalFileName = originalFileName;
        this.storedFilePath = storedFilePath;
        this.fileSize = fileSize;
        this.creatorId = creatorId;
    }


}
