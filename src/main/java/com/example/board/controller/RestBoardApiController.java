package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class RestBoardApiController {

    @Autowired
    private final BoardService boardService;

    @GetMapping("/api/board")
    public List<BoardDto> openBoardList() throws Exception {
        return boardService.selectBoardList();
    }

    @PostMapping("/api/board/write")
    public void insertBoard(@RequestBody BoardDto boardDto) throws Exception {
        boardService.insertBoard(boardDto, null);
    }

    @GetMapping("/api/board/{boardIdx}")
    public BoardDto openBoardDetail(@PathVariable int boardIdx) throws Exception{
        return boardService.selectBoardDetail(boardIdx);
    }

    @PutMapping("/api/board/{boardIdx}")
    public String updateBoard(@PathVariable int boardIdx, @RequestBody BoardDto dto) throws Exception{
        boardService.updateBoard(dto);
        return "redirect:/board";
    }

    @DeleteMapping("/api/board/{boardIdx}")
    public String deleteBoard(@PathVariable int boardIdx) throws Exception{
        boardService.deleteBoard(boardIdx);
        return "redirect:/board";
    }
}
