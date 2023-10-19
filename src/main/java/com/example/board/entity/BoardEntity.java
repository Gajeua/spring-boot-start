package com.example.board.entity;

import com.example.board.dto.JpaBoardDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_jpa_board")
@NoArgsConstructor
@Data
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long boardIdx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private int hitCnt = 0;

    @Column(nullable = false)
    private String creatorId;

    @Column(nullable = false)
    private LocalDateTime createdDateTime = LocalDateTime.now();

    @Column
    private String updaterId;

    @Column
    private LocalDateTime updatedDateTime;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "board_idx")
    private List<BoardFileEntity> fileList;

    @Builder
    public BoardEntity (String title, String contents, String creatorId) {
        Assert.notNull(title, "게시글 제목이 없습니다.");
        Assert.notNull(contents, "게시글 내용이 없습니다.");
        Assert.notNull(creatorId, "작성자가 없습니다.");

        this.title = title;
        this.contents = contents;
        this.creatorId = creatorId;
        this.createdDateTime = LocalDateTime.now();
    }

    public void updateHitCnt() {
        this.hitCnt ++;
    }

    public void updateBoard(JpaBoardDto boardDto) {
        Assert.notNull(boardDto.getTitle(), "게시글 제목이 없습니다.");
        Assert.notNull(boardDto.getContents(), "게시글 내용이 없습니다.");

        this.title = boardDto.getTitle();
        this.contents = boardDto.getContents();
        this.updaterId = "admin";
        this.updatedDateTime = LocalDateTime.now();
    }
}
