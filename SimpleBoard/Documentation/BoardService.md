```markdown
# BoardService 클래스

## 개요
`BoardService` 클래스는 게시판 애플리케이션에서 `Board` 엔티티에 대한 비즈니스 로직을 처리하는 서비스 클래스입니다. 데이터 접근은 `BoardRepository`를 통해 이루어지며, 트랜잭션 관리와 비즈니스 로직을 캡슐화합니다.

## 패키지 및 임포트
```java
package org.example.simpleboard.service;

import lombok.RequiredArgsConstructor;
import org.example.simpleboard.model.Board;
import org.example.simpleboard.repository.BoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
```
- `lombok.RequiredArgsConstructor`: `final` 필드에 대한 생성자를 자동으로 생성하여 의존성을 주입합니다.
- `org.springframework.data.domain.Page`, `PageRequest`, `Pageable`, `Sort`: 페이징 및 정렬을 위한 클래스들입니다.
- `org.springframework.stereotype.Service`: 해당 클래스가 서비스 클래스임을 나타냅니다.
- `org.springframework.transaction.annotation.Transactional`: 트랜잭션 관리를 위한 어노테이션입니다.

## 어노테이션
- `@Service`: 해당 클래스가 서비스 레이어의 빈(Bean)임을 나타냅니다.
- `@RequiredArgsConstructor`: `final` 필드에 대한 생성자를 자동으로 생성하여 의존성을 주입합니다.
- `@Transactional`: 트랜잭션 범위를 지정합니다.

## 필드
```java
private final BoardRepository boardRepository;
```
- `boardRepository`: 게시글에 대한 데이터 접근을 담당하는 리포지토리 인터페이스입니다.

## 메서드
### 모든 게시글 조회
```java
@Transactional(readOnly = true)
public Iterable<Board> findAllBoards() {
    return boardRepository.findAll();
}
```
- 모든 게시글을 조회합니다. (읽기 전용 트랜잭션)

### 새로운 게시글 저장
```java
@Transactional
public Board saveBoard(Board board) {
    board.prePersist(); // 생성하면 생성 날짜 설정
    return boardRepository.save(board);
}
```
- 새로운 게시글을 저장합니다. (쓰기 트랜잭션)

### 게시글 ID로 조회
```java
@Transactional(readOnly = true)
public Board findBoardById(Long id) {
    return boardRepository.findById(id).orElse(null);
}
```
- ID를 사용하여 게시글을 조회합니다. (읽기 전용 트랜잭션)

### 게시글 ID로 삭제
```java
@Transactional
public void deleteBoardById(Long id) {
    boardRepository.deleteById(id);
}
```
- ID를 사용하여 게시글을 삭제합니다. (쓰기 트랜잭션)

### 게시글 목록 조회 (페이징 및 정렬)
```java
@Transactional(readOnly = true)
public Page<Board> findAllBoards(Pageable pageable) {
    Pageable sortedByDescId = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
            Sort.by(Sort.Direction.DESC, "id"));
    return boardRepository.findAll(sortedByDescId);
}
```
- 페이징 및 정렬된 게시글 목록을 조회합니다. (읽기 전용 트랜잭션)

## 예제
```java
// 모든 게시글 조회
Iterable<Board> allBoards = boardService.findAllBoards();

// 새로운 게시글 저장
Board newBoard = new Board();
newBoard.setName("작성자 이름");
newBoard.setTitle("게시물 제목");
newBoard.setPassword("비밀번호");
newBoard.setContent("게시물 내용");
Board savedBoard = boardService.saveBoard(newBoard);

// 게시글 ID로 조회
Board board = boardService.findBoardById(1L);

// 게시글 ID로 삭제
boardService.deleteBoardById(1L);

// 페이징 및 정렬된 게시글 목록 조회
Pageable pageable = PageRequest.of(0, 10);
Page<Board> pagedBoards = boardService.findAllBoards(pageable);
```
- 위 예제는 `BoardService` 클래스의 주요 메서드를 사용하는 방법을 보여줍니다.
