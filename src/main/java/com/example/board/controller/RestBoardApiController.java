package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "게시판 REST API")
@RestController
@Slf4j
@RequiredArgsConstructor
public class RestBoardApiController {

    @Autowired
    private final BoardService boardService;

    @ApiOperation(value = "게시글 목록 조회", notes = "게시글 목록을 조회합니다.")
    @GetMapping("/api/board")
    public List<BoardDto> openBoardList() throws Exception {
        return boardService.selectBoardList();
    }

    @ApiOperation(value = "게시글 작성", notes = "게시글을 작성니다.")
    @PostMapping("/api/board/write")
    public void insertBoard(@RequestBody BoardDto boardDto) throws Exception {
        boardService.insertBoard(boardDto, null);
    }

    @ApiOperation(value = "게시글 상세 조회", notes = "게시글을 상세 조회합니다.")
    @GetMapping("/api/board/{boardIdx}")
    public BoardDto openBoardDetail(@PathVariable @ApiParam(value = "게시글 번호") int boardIdx) throws Exception{
        return boardService.selectBoardDetail(boardIdx);
    }

    @ApiOperation(value = "게시글 수정", notes = "게시글을 수정합니다.")
    @PutMapping("/api/board/{boardIdx}")
    public String updateBoard(@PathVariable int boardIdx, @RequestBody BoardDto dto) throws Exception{
        boardService.updateBoard(dto);
        return "redirect:/board";
    }

    @ApiOperation(value = "게시글 삭제", notes = "게시글을 삭제합니다.")
    @DeleteMapping("/api/board/{boardIdx}")
    public String deleteBoard(@PathVariable @ApiParam(value = "게시글 번호") int boardIdx) throws Exception{
        boardService.deleteBoard(boardIdx);
        return "redirect:/board";
    }
}
