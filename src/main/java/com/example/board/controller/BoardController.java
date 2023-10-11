package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@Slf4j
public class BoardController {

    @Autowired
    private BoardService boardService;

    // @Slf4j 사용하면 로거 생성 필요없음.
//     private Logger log = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/board/openBoardList.do")
    public ModelAndView openBoardList() throws Exception {
        log.debug("BoardController :: openBoardList");

        ModelAndView mv = new ModelAndView("/board/boardList");

        List<BoardDto> list = boardService.selectBoardList();
        mv.addObject("list", list);

        return mv;
    }

    @RequestMapping("/board/openBoardWrite.do")
    public String openBoardWrite() throws Exception{
        log.debug("BoardController :: openBoardWrite");

        return "/board/boardWrite";
    }

    @RequestMapping("/board/insertBoard.do")
    public String insertBoard(BoardDto dto) throws Exception{
        log.debug("BoardController :: insertBoard");

        boardService.insertBoard(dto);
        return "redirect:/board/openBoardList.do";
    }

    @RequestMapping("/board/openBoardDetail.do")
    public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception{
        log.debug("BoardController :: openBoardDetail");

        ModelAndView mv = new ModelAndView("/board/boardDetail");
        BoardDto board = boardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);

        return mv;
    }

    @RequestMapping("/board/updateBoard.do")
    public String updateBoard(BoardDto dto) throws Exception{
        log.debug("BoardController :: updateBoard");

        boardService.updateBoard(dto);
        return "redirect:/board/openBoardList.do";
    }

    @RequestMapping("/board/deleteBoard.do")
    public String deleteBoard(int boardIdx) throws Exception{
        log.debug("BoardController :: deleteBoard");

        boardService.deleteBoard(boardIdx);
        return "redirect:/board/openBoardList.do";
    }
}
