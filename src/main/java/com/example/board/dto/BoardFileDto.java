package com.example.board.dto;

import lombok.Data;

@Data
public class BoardFileDto {
    private String idx;
    private int boardIdx;
    private String originalFileName;
    private String storedFilePath;
    private long fileSize;
}
