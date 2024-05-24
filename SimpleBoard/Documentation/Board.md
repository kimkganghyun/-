```markdown
# Board 클래스

## 개요
`Board` 클래스는 게시판의 게시물을 표현하는 모델 클래스입니다. 이 클래스는 데이터베이스 테이블 `board`와 매핑되며, 게시물의 아이디, 이름, 제목, 비밀번호, 내용, 생성 날짜 및 업데이트 날짜를 포함합니다.

## 패키지 및 임포트
```java
package org.example.simpleboard.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import java.time.LocalDateTime;
```
- `lombok` 패키지를 통해 클래스의 보일러플레이트 코드를 줄입니다.
- `spring-data` 패키지를 통해 데이터베이스 테이블과 매핑합니다.
- `java.time.LocalDateTime`을 통해 날짜와 시간을 다룹니다.

## 어노테이션
- `@Getter`, `@Setter`: 각 필드에 대한 getter 및 setter 메서드를 자동 생성합니다.
- `@NoArgsConstructor`: 파라미터가 없는 기본 생성자를 자동 생성합니다.
- `@AllArgsConstructor`: 모든 필드를 파라미터로 받는 생성자를 자동 생성합니다.
- `@ToString`: 객체의 문자열 표현을 제공합니다.
- `@EqualsAndHashCode`: 객체의 동등성 및 해시 코드를 자동으로 생성합니다.
- `@Table("board")`: 이 클래스가 데이터베이스의 `board` 테이블과 매핑됨을 나타냅니다.

## 필드
```java
@Id
private Long id; // 아이디
private String name; // 이름
private String title; // 글 제목
private String password; // 비밀 번호
private String content; // 글 내용
private LocalDateTime createdAt; // 생성 날짜
private LocalDateTime updatedAt; // 업데이트 날짜
```
- `id`: 게시물의 고유 아이디입니다.
- `name`: 작성자의 이름입니다.
- `title`: 게시물의 제목입니다.
- `password`: 게시물의 비밀번호입니다.
- `content`: 게시물의 내용입니다.
- `createdAt`: 게시물이 생성된 날짜 및 시간입니다.
- `updatedAt`: 게시물이 업데이트된 날짜 및 시간입니다.

## 메서드
### prePersist
```java
public void prePersist() {
    createdAt = LocalDateTime.now();
    updatedAt = LocalDateTime.now();
}
```
- 새로운 게시물이 생성될 때 호출됩니다.
- `createdAt`과 `updatedAt` 필드를 현재 시간으로 설정합니다.

### preUpdate
```java
public void preUpdate() {
    updatedAt = LocalDateTime.now();
}
```
- 게시물이 업데이트될 때 호출됩니다.
- `updatedAt` 필드를 현재 시간으로 설정합니다.

## 예제
```java
Board board = new Board();
board.setName("작성자 이름");
board.setTitle("게시물 제목");
board.setPassword("비밀번호");
board.setContent("게시물 내용");
board.prePersist(); // 생성 날짜와 업데이트 날짜 설정
System.out.println(board);
```
- 새로운 `Board` 객체를 생성하고 필드를 설정한 후, `prePersist` 메서드를 호출하여 생성 날짜와 업데이트 날짜를 설정합니다.
- `System.out.println(board);`를 통해 게시물 정보를 출력합니다.
```