package com.example.board.dto;

import com.example.board.entity.BoardEntity;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class JpaBoardDto {

    private Long boardIdx;
    private String title;
    private String contents;
    private Integer hitCnt;
    private String creatorId;
    private LocalDateTime createdDatetime;
    private String updaterId;
    private LocalDateTime updatedDatetime;

    private List<JpaBoardFileDto> boardFileDtoList;

    public static JpaBoardDto toResult(BoardEntity board) {
        return JpaBoardDto.builder()
                .boardIdx(board.getBoardIdx())
                .title(board.getTitle())
                .contents(board.getContents())
                .hitCnt(board.getHitCnt())
                .creatorId(board.getCreatorId())
                .createdDatetime(board.getCreatedDateTime())
                .updaterId(board.getUpdaterId())
                .updatedDatetime(board.getUpdatedDateTime())
                .boardFileDtoList(
                        CollectionUtils.isEmpty(board.getFileList())
                                ? new ArrayList<>()
                                : board.getFileList().stream()
                                .map(x -> JpaBoardFileDto.toResult(board.getBoardIdx(), x))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
