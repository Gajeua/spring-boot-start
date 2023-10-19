package com.example.board.dto;

import com.example.board.entity.BoardFileEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JpaBoardFileDto {
    private Long idx;
    private Long boardIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;

    public static JpaBoardFileDto toResult(Long boardIdx, BoardFileEntity file) {
        return JpaBoardFileDto.builder()
                .idx(file.getIdx())
                .boardIdx(boardIdx)
                .originalFileName(file.getOriginalFileName())
                .storedFilePath(file.getStoredFilePath())
                .fileSize(Math.round((double) file.getFileSize() / 1000))
                .build();
    }
}
