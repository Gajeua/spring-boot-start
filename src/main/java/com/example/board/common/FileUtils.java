package com.example.board.common;

import com.example.board.dto.BoardFileDto;
import com.example.board.entity.BoardFileEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class FileUtils {

    public List<BoardFileDto> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        if (ObjectUtils.isEmpty(multipartHttpServletRequest)) return null;

        List<BoardFileDto> fileList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now(); // 자바 1.8 부터 사용 가능
        String path ="images/" + current.format(formatter);
        File file = new File(path);

        // 해당 폴더가 없으면 생성.
        if (!file.exists()) file.mkdirs();

        String newFileName, originalFileExtension, contentType;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        while (iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());

            // 파일의 contentType 확인 후 확장자 넣어줌.
            for (MultipartFile multipartFile : list) {
                if (!multipartFile.isEmpty()) {
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) break;
                    else {
                        if (contentType.contains("image/jpeg")) {
                            originalFileExtension = ".jpg";
                        } else if (contentType.contains("image/png")) {
                            originalFileExtension = ".png";
                        } else if (contentType.contains("image/gif")) {
                            originalFileExtension = ".gif";
                        } else break;
                    }

                    // 나노타임을 이용해서 이름 중복나지 않게.. (밀리초로 할 경우 중복될 수 있음)
                    newFileName = System.nanoTime() + originalFileExtension;
                    BoardFileDto boardFileDto = new BoardFileDto();
                    boardFileDto.setBoardIdx(boardIdx);
                    boardFileDto.setFileSize(multipartFile.getSize());
                    boardFileDto.setOriginalFileName(multipartFile.getOriginalFilename());
                    boardFileDto.setStoredFilePath(path +"/" + newFileName);
                    fileList.add(boardFileDto);

                    // 파일 저장
                    file = new File(boardFileDto.getStoredFilePath());
                    multipartFile.transferTo(file);
                }
            }
        }
        return fileList;
    }

    public List<BoardFileEntity> parseFileInfo(MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        if (ObjectUtils.isEmpty(multipartHttpServletRequest)) return null;

        List<BoardFileEntity> fileList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        ZonedDateTime current = ZonedDateTime.now(); // 자바 1.8 부터 사용 가능
        String path ="images/" + current.format(formatter);
        File file = new File(path);

        // 해당 폴더가 없으면 생성.
        if (!file.exists()) file.mkdirs();

        String newFileName, originalFileExtension, contentType;
        Iterator<String> iterator = multipartHttpServletRequest.getFileNames();
        while (iterator.hasNext()) {
            List<MultipartFile> list = multipartHttpServletRequest.getFiles(iterator.next());

            // 파일의 contentType 확인 후 확장자 넣어줌.
            for (MultipartFile multipartFile : list) {
                if (!multipartFile.isEmpty()) {
                    contentType = multipartFile.getContentType();
                    if (ObjectUtils.isEmpty(contentType)) break;
                    else {
                        if (contentType.contains("image/jpeg")) {
                            originalFileExtension = ".jpg";
                        } else if (contentType.contains("image/png")) {
                            originalFileExtension = ".png";
                        } else if (contentType.contains("image/gif")) {
                            originalFileExtension = ".gif";
                        } else break;
                    }

                    // 나노타임을 이용해서 이름 중복나지 않게.. (밀리초로 할 경우 중복될 수 있음)
                    newFileName = System.nanoTime() + originalFileExtension;
                    BoardFileEntity boardFile = BoardFileEntity.builder()
                            .originalFileName(multipartFile.getOriginalFilename())
                            .storedFilePath(path +"/" + newFileName)
                            .fileSize(multipartFile.getSize())
                            .creatorId("admin")
                            .build();
                    boardFile.setFileSize(multipartFile.getSize());
                    boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
                    boardFile.setStoredFilePath(path +"/" + newFileName);
                    fileList.add(boardFile);

                    // 파일 저장
                    file = new File(boardFile.getStoredFilePath());
                    multipartFile.transferTo(file);
                }
            }
        }
        return fileList;
    }
}
