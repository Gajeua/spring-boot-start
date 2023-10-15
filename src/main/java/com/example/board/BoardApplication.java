package com.example.board;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

// 스프링부트에서 자동으로 구성된 첨부파일 관련 MultipartAutoConfiguration 클래스 제외
@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
public class BoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardApplication.class, args);
    }

}
