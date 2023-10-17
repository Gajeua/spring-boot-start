package com.example.board.controller;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardFileDto;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class RestBoardController {

    @Autowired
    private final BoardService boardService;

    @RequestMapping(method = RequestMethod.GET, value = "/board")
    public ModelAndView openBoardList() throws Exception {
        ModelAndView mv = new ModelAndView("/board/restBoardList");
        List<BoardDto> list = boardService.selectBoardList();
        mv.addObject("list", list);
        return mv;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/board/write")
    public String openBoardWrite() throws Exception {
        return "/board/restBoardWrite";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/board/write")
    public String insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        boardService.insertBoard(boardDto, multipartHttpServletRequest);
        return "redirect:/board";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/board/{boardIdx}")
    public ModelAndView openBoardDetail(@PathVariable int boardIdx) throws Exception {
        ModelAndView mv = new ModelAndView("/board/restBoardDetail");
        BoardDto board = boardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);

        return mv;
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/board/{boardIdx}")
    public String updateBoard(BoardDto dto) throws Exception{
        boardService.updateBoard(dto);
        return "redirect:/board";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/board/{boardIdx}")
    public String deleteBoard(@PathVariable int boardIdx) throws Exception{
        boardService.deleteBoard(boardIdx);
        return "redirect:/board";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/board/file")
    public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
        BoardFileDto fileDto = boardService.selectBoardFileInformation(idx, boardIdx);

        if (!ObjectUtils.isEmpty(fileDto)) {
            String fileName = fileDto.getOriginalFileName();

            // storedFilePath 로 파일을 가져온 후 byte[] 로 변환 (org.appache.commons.io 라이브러리)
            byte[] files = FileUtils.readFileToByteArray(new File(fileDto.getStoredFilePath()));

            // response 설정.
            response.setContentType("application/octet-stream");
            response.setContentLength(files.length);
            // 파일 이름을 UTF-8 로 인코딩 해주지 않으면 이상한 문자로 다운됨.
            response.setHeader("Content-Disposition", "attachment; fileName=\"" +
                    URLEncoder.encode(fileName,"UTF-8") + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");

            // 파일 정보의 바이트 배열 데이터를 response 에 작성
            response.getOutputStream().write(files);

            // response 버퍼를 정리하고 닫음.
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }
}
