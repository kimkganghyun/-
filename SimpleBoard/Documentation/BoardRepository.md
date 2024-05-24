```markdown
# BoardRepository 인터페이스

## 개요
`BoardRepository` 인터페이스는 게시판 애플리케이션에서 `Board` 엔티티에 대한 데이터 접근을 담당합니다. Spring Data JPA를 사용하여 CRUD 및 페이징/정렬 기능을 제공합니다.

## 패키지 및 임포트
```java
package org.example.simpleboard.repository;

import org.example.simpleboard.model.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
```
- `org.springframework.data.repository.CrudRepository`: 기본적인 CRUD 기능을 제공합니다.
- `org.springframework.data.repository.PagingAndSortingRepository`: 페이징 및 정렬 기능을 추가로 제공합니다.

## 인터페이스 정의
```java
public interface BoardRepository extends CrudRepository<Board, Long>, PagingAndSortingRepository<Board, Long> {
}
```
- `CrudRepository<Board, Long>`: `Board` 엔티티에 대한 기본적인 CRUD 기능을 제공합니다.
- `PagingAndSortingRepository<Board, Long>`: 페이징 및 정렬 기능을 추가로 제공합니다.

## 주요 메서드
`CrudRepository` 및 `PagingAndSortingRepository`에서 제공하는 주요 메서드는 다음과 같습니다:

### CrudRepository 메서드
- `save(S entity)`: 엔티티를 저장합니다.
- `findById(ID id)`: ID를 사용하여 엔티티를 조회합니다.
- `findAll()`: 모든 엔티티를 조회합니다.
- `count()`: 엔티티의 총 개수를 반환합니다.
- `deleteById(ID id)`: ID를 사용하여 엔티티를 삭제합니다.
- `delete(S entity)`: 엔티티를 삭제합니다.

### PagingAndSortingRepository 메서드
- `findAll(Pageable pageable)`: 페이징된 결과를 조회합니다.
- `findAll(Sort sort)`: 정렬된 결과를 조회합니다.

## 예제
```java
import org.example.simpleboard.model.Board;
import org.example.simpleboard.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    
    @Autowired
    private BoardRepository boardRepository;

    // 모든 게시글 조회 (페이징 처리)
    public Page<Board> findAllBoards(PageRequest pageRequest) {
        return boardRepository.findAll(pageRequest);
    }

    // 게시글 저장
    public void saveBoard(Board board) {
        boardRepository.save(board);
    }

    // 게시글 ID로 조회
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    // 게시글 삭제
    public void deleteBoardById(Long id) {
        boardRepository.deleteById(id);
    }
}
```
- 위 예제는 `BoardService` 클래스에서 `BoardRepository`를 사용하여 CRUD 및 페이징/정렬 기능을 구현한 것입니다.
