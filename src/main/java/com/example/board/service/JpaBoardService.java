package com.example.board.service;

import com.example.board.common.FileUtils;
import com.example.board.dto.JpaBoardDto;
import com.example.board.dto.JpaBoardFileDto;
import com.example.board.entity.BoardEntity;
import com.example.board.entity.BoardFileEntity;
import com.example.board.repo.JpaBoardRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class JpaBoardService {

    private final JpaBoardRepository jpaBoardRepository;
    private final FileUtils fileUtils;

    @Transactional(readOnly = true)
    public List<JpaBoardDto> selectBoardList() throws Exception {
        return jpaBoardRepository.findAllByOrderByBoardIdxDesc().stream()
                .map(JpaBoardDto::toResult)
                .collect(Collectors.toList());
    }

    @Transactional
    public void savedBoard(JpaBoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        BoardEntity board = BoardEntity.builder()
                .title(boardDto.getTitle())
                .contents(boardDto.getContents())
                .creatorId("admin")
                .build();

        List<BoardFileEntity> list = fileUtils.parseFileInfo(multipartHttpServletRequest);
        if (!CollectionUtils.isEmpty(list)) {
            board.setFileList(list);
        }
        jpaBoardRepository.save(board);
    }

    @Transactional
    public JpaBoardDto selectBoardDetail(Long boardIdx) throws Exception {
        BoardEntity board = jpaBoardRepository.findById(boardIdx).orElseThrow(NullPointerException::new);
        board.updateHitCnt();

        return JpaBoardDto.toResult(board);
    }

    @Transactional
    public void deleteBoard(Long boardIdx) {
        jpaBoardRepository.deleteById(boardIdx);
    }

    @Transactional
    public JpaBoardFileDto selectBoardFileInformation(Long idx, Long boardIdx) throws Exception {
        BoardFileEntity boardFile = jpaBoardRepository.findBoardFile(idx);
        return JpaBoardFileDto.toResult(boardIdx, boardFile);
    }

    @Transactional
    public void updateBoard(JpaBoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) {
        BoardEntity board = jpaBoardRepository.findById(boardDto.getBoardIdx()).orElseThrow(NullPointerException::new);

        board.updateBoard(boardDto);

    }
}
