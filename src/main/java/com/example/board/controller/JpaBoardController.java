package com.example.board.controller;

import com.example.board.dto.JpaBoardDto;
import com.example.board.dto.JpaBoardFileDto;
import com.example.board.service.JpaBoardService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

@Controller
@AllArgsConstructor
public class JpaBoardController {

    private final JpaBoardService jpaBoardService;

    @GetMapping("/jpa/board")
    public ModelAndView openBoardList() throws Exception {
        ModelAndView mv = new ModelAndView("/board/jpaBoardList");
        List<JpaBoardDto> list = jpaBoardService.selectBoardList();
        mv.addObject("list", list);
        return mv;
    }

    @GetMapping("/jpa/board/write")
    public String openBoardWrite() throws Exception {
        return "/board/jpaBoardWrite";
    }

    @PostMapping("/jpa/board/write")
    public String insertBoard(JpaBoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception {
        jpaBoardService.savedBoard(boardDto, multipartHttpServletRequest);
        return "redirect:/jpa/board";
    }

    @GetMapping("/jpa/board/{boardIdx}")
    public ModelAndView openBoardDetail(@PathVariable Long boardIdx) throws Exception {
        ModelAndView mv = new ModelAndView("/board/jpaBoardDetail");
        JpaBoardDto board = jpaBoardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);

        return mv;
    }

    @PutMapping("/jpa/board/{boardIdx}")
    public String updateBoard(JpaBoardDto dto) throws Exception{
        jpaBoardService.updateBoard(dto, null);
        return "redirect:/jpa/board";
    }

    @DeleteMapping("/jpa/board/{boardIdx}")
    public String deleteBoard(@PathVariable Long boardIdx) throws Exception{
        jpaBoardService.deleteBoard(boardIdx);
        return "redirect:/jpa/board";
    }

    @GetMapping("/jpa/board/file")
    public void downloadBoardFile(@RequestParam Long idx, @RequestParam Long boardIdx, HttpServletResponse response) throws Exception{
        JpaBoardFileDto fileDto = jpaBoardService.selectBoardFileInformation(idx, boardIdx);

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
