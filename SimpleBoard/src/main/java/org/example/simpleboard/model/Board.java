package org.example.simpleboard.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Table("board") // 테이블 매핑
public class Board {
    @Id
    private Long id; // 아이디
    private String name; // 이름
    private String title; // 글 제목
    private String password; // 비밀 번호
    private String content; // 글 내용
    private LocalDateTime createdAt; // 생성 날짜
    private LocalDateTime updatedAt; // 업데이트 날짜

    // 생성 시 날짜 설정
    public void prePersist() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    // 업데이트 시 날짜 설정
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }


}
