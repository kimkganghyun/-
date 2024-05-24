```markdown
# BoardController 클래스

## 개요
`BoardController` 클래스는 게시판 기능을 제공하는 웹 애플리케이션의 컨트롤러로, 게시글의 목록 조회, 등록, 수정, 삭제 등의 기능을 담당합니다. Spring MVC 아키텍처를 사용하며, `BoardService`를 통해 비즈니스 로직을 처리합니다.

## 패키지 및 임포트
```java
package org.example.simpleboard.controller;

import lombok.RequiredArgsConstructor;
import org.example.simpleboard.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.example.simpleboard.service.BoardService;
```
- `lombok.RequiredArgsConstructor`: 생성자를 자동 생성하여 의존성을 주입합니다.
- `org.springframework.data.domain.Page` 및 `PageRequest`: 페이징 처리를 위한 클래스입니다.
- `org.springframework.stereotype.Controller`: 해당 클래스가 컨트롤러임을 나타냅니다.
- `org.springframework.ui.Model`: 뷰로 데이터를 전달하기 위한 인터페이스입니다.
- `org.springframework.web.bind.annotation.*`: 요청 매핑을 위한 어노테이션들입니다.
- `org.springframework.web.servlet.mvc.support.RedirectAttributes`: 리다이렉트 시 플래시 속성을 추가하기 위한 클래스입니다.

## 어노테이션
- `@Controller`: 해당 클래스가 컨트롤러임을 나타냅니다.
- `@RequiredArgsConstructor`: `final` 필드에 대한 생성자를 자동으로 생성하여 의존성을 주입합니다.
- `@RequestMapping("/list")`: 기본 요청 경로를 `/list`로 설정합니다.

## 필드
```java
private final BoardService boardService;
```
- `boardService`: 게시글에 대한 비즈니스 로직을 처리하는 서비스 클래스입니다.

## 메서드
### 게시글 목록 조회
```java
@GetMapping
public String boards(@RequestParam(value = "page", defaultValue = "0") int page,
                     @RequestParam(value = "size", defaultValue = "10") int size,
                     Model model) {
    PageRequest pageRequest = PageRequest.of(page, size);
    Page<Board> boardsPage = boardService.findAllBoards(pageRequest);
    model.addAttribute("boardsPage", boardsPage);
    return "boards/list";
}
```
- 게시글 목록을 조회하여 페이징 처리된 결과를 뷰로 전달합니다.

### 게시글 등록 폼
```java
@GetMapping("/writeform")
public String addForm(Model model) {
    model.addAttribute("board", new Board());
    return "boards/writeform";
}
```
- 게시글 등록을 위한 폼을 뷰로 전달합니다.

### 게시글 등록
```java
@PostMapping("/writeform")
public String addBoard(@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
    boardService.saveBoard(board);
    redirectAttributes.addFlashAttribute("message", "게시글 등록 성공!!");
    return "redirect:/list";
}
```
- 게시글을 등록하고 성공 메시지를 전달합니다.

### 게시글 상세 페이지
```java
@GetMapping("/view")
public String detailBoard(@RequestParam("id") Long id, Model model) {
    Board board = boardService.findBoardById(id);
    model.addAttribute("board", board);
    return "boards/detail";
}
```
- 게시글의 상세 정보를 조회하여 뷰로 전달합니다.

### 게시글 삭제 폼
```java
@GetMapping("/deleteform")
public String deleteForm(@RequestParam("id") Long id, Model model) {
    Board board = boardService.findBoardById(id);
    model.addAttribute("board", board);
    return "boards/deleteform";
}
```
- 게시글 삭제를 위한 폼을 뷰로 전달합니다.

### 게시글 삭제
```java
@PostMapping("/delete")
public String deleteBoard(@RequestParam("id") Long id, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
    Board board = boardService.findBoardById(id);
    if (board != null && board.getPassword().equals(password)) {
        boardService.deleteBoardById(id);
        redirectAttributes.addFlashAttribute("message", "게시글 삭제 성공!!");
    } else {
        redirectAttributes.addFlashAttribute("error", "비밀번호가 일치하지 않습니다.");
        return "redirect:/list/deleteform?id=" + id;
    }
    return "redirect:/list";
}
```
- 비밀번호 확인 후 게시글을 삭제하고 성공 메시지를 전달합니다.

### 게시글 수정 폼
```java
@GetMapping("/updateform")
public String editForm(@RequestParam("id") Long id, Model model) {
    model.addAttribute("board", boardService.findBoardById(id));
    return "boards/edit";
}
```
- 게시글 수정을 위한 폼을 뷰로 전달합니다.

### 게시글 수정
```java
@PostMapping("/update")
public String editBoard(@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
    boardService.saveBoard(board);
    redirectAttributes.addFlashAttribute("message", "게시글 수정 성공!!");
    return "redirect:/list/view?id=" + board.getId();
}
```
- 게시글을 수정하고 성공 메시지를 전달합니다.

## 예제
```java
// 목록 조회
@GetMapping("/list")
public String boards(Model model) {
    model.addAttribute("boards", boardService.findAllBoards());
    return "boards/list";
}

// 게시글 등록
@PostMapping("/list/writeform")
public String addBoard(@ModelAttribute Board board) {
    boardService.saveBoard(board);
    return "redirect:/list";
}
```