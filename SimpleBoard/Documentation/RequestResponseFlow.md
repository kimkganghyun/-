```markdown
# 요청/응답 처리 클래스 및 시퀀스 다이어그램

## 개요
요청/응답 처리 클래스는 클라이언트의 요청을 받아 적절한 서비스 메서드를 호출하고, 그 결과를 클라이언트에게 반환하는 역할을 합니다. `BoardController` 클래스가 이 역할을 수행하며, `BoardService`와 `BoardRepository`가 비즈니스 로직 및 데이터 접근을 처리합니다.

## 흐름 제어 설명

1. **클라이언트 요청**: 클라이언트는 웹 브라우저를 통해 특정 URL로 요청을 보냅니다.
2. **컨트롤러**: `BoardController` 클래스는 해당 URL에 매핑된 메서드를 호출하여 요청을 처리합니다.
3. **서비스**: 컨트롤러는 비즈니스 로직을 처리하기 위해 `BoardService` 클래스의 메서드를 호출합니다.
4. **리포지토리**: 서비스는 데이터베이스 접근을 위해 `BoardRepository`의 메서드를 호출합니다.
5. **응답**: 데이터베이스 작업이 완료되면, 서비스는 결과를 컨트롤러로 반환하고, 컨트롤러는 이를 뷰로 전달하여 클라이언트에게 응답을 반환합니다.

## 시퀀스 다이어그램

```markdown
sequenceDiagram
    participant Client
    participant Controller
    participant Service
    participant Repository

    Client ->> Controller: 요청 (예: /list)
    Controller ->> Service: findAllBoards(PageRequest pageRequest)
    Service ->> Repository: findAll(PageRequest pageRequest)
    Repository -->> Service: 게시글 목록 반환
    Service -->> Controller: 게시글 목록 반환
    Controller -->> Client: 뷰 반환 (예: boards/list)

    Client ->> Controller: 게시글 등록 요청 (예: /list/writeform)
    Controller ->> Service: saveBoard(Board board)
    Service ->> Repository: save(Board board)
    Repository -->> Service: 저장된 게시글 반환
    Service -->> Controller: 저장된 게시글 반환
    Controller -->> Client: 리다이렉트 (예: /list)
```

## 흐름 제어 설명 (텍스트 형식)

```markdown
Client    ->    Controller    ->    Service    ->    Repository
 |                 |               |               |
 |    요청 전송      |               |               |
 |    ------------>|               |               |
 |                 |     요청 처리   |               |
 |                 |   ----------->|               |
 |                 |                |   요청 처리    |
 |                 |               |   ----------->|
 |                 |               |               |
 |                 |               |   응답 반환     |
 |                 |               |   <-----------|
 |                 |    응답 반환    |               |
 |                 |   <-----------|               |
 |    응답 반환      |               |               |
 |    <------------|               |               |
```

## 주요 클래스 및 메서드
- **BoardController**
    - `boards()`: 모든 게시글을 조회하여 목록을 반환합니다.
    - `addForm()`: 게시글 등록 폼을 반환합니다.
    - `addBoard()`: 새로운 게시글을 저장합니다.
    - `detailBoard()`: 특정 게시글의 상세 정보를 반환합니다.
    - `deleteForm()`: 게시글 삭제 폼을 반환합니다.
    - `deleteBoard()`: 특정 게시글을 삭제합니다.
    - `editForm()`: 게시글 수정 폼을 반환합니다.
    - `editBoard()`: 특정 게시글을 수정합니다.

- **BoardService**
    - `findAllBoards()`: 모든 게시글을 조회합니다.
    - `saveBoard()`: 새로운 게시글을 저장합니다.
    - `findBoardById()`: 특정 게시글을 조회합니다.
    - `deleteBoardById()`: 특정 게시글을 삭제합니다.
    - `findAllBoards(Pageable pageable)`: 페이징 및 정렬된 게시글 목록을 조회합니다.

- **BoardRepository**
    - `findAll()`: 모든 게시글을 조회합니다.
    - `findById(Long id)`: ID로 특정 게시글을 조회합니다.
    - `save(Board board)`: 게시글을 저장합니다.
    - `deleteById(Long id)`: ID로 특정 게시글을 삭제합니다.

## 예제
### 게시글 목록 조회 예제
1. **클라이언트**: `/list` URL로 GET 요청을 보냅니다.
2. **컨트롤러**: `BoardController.boards()` 메서드가 호출됩니다.
3. **서비스**: `BoardService.findAllBoards(PageRequest pageRequest)` 메서드가 호출됩니다.
4. **리포지토리**: `BoardRepository.findAll(PageRequest pageRequest)` 메서드가 호출됩니다.
5. **응답**: 조회된 게시글 목록이 반환되어 클라이언트에게 `boards/list` 뷰로 전달됩니다.

### 게시글 등록 예제
1. **클라이언트**: `/list/writeform` URL로 POST 요청을 보냅니다.
2. **컨트롤러**: `BoardController.addBoard(Board board)` 메서드가 호출됩니다.
3. **서비스**: `BoardService.saveBoard(Board board)` 메서드가 호출됩니다.
4. **리포지토리**: `BoardRepository.save(Board board)` 메서드가 호출됩니다.
5. **응답**: 저장된 게시글 정보가 반환되고, 클라이언트는 `/list` URL로 리다이렉트됩니다.

위 설명을 바탕으로 프로젝트의 문서화 폴더에 다음과 같이 파일을 저장할 수 있습니다:

