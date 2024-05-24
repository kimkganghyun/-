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

@Controller
@RequiredArgsConstructor
@RequestMapping("/list")
public class BoardController {

    private final BoardService boardService;

    // 모든 게시글 조회 뷰 -> 전달
    @GetMapping
    public String boards(@RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "size", defaultValue = "10") int size,
                         Model model) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Board> boardsPage = boardService.findAllBoards(pageRequest);
        model.addAttribute("boardsPage", boardsPage);
        return "boards/list";
    }

    // 게시글 등록 폼
    @GetMapping("/writeform")
    public String addForm(Model model) {
        model.addAttribute("board", new Board());
        return "boards/writeform";
    }

    // 게시글 등록
    @PostMapping("/writeform")
    public String addBoard(@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
        boardService.saveBoard(board);
        redirectAttributes.addFlashAttribute("message", "게시글 등록 성공!!");
        return "redirect:/list";
    }

    // 게시글 상세 페이지
    @GetMapping("/view")
    public String detailBoard(@RequestParam("id") Long id, Model model) {
        Board board = boardService.findBoardById(id);
        model.addAttribute("board", board);
        return "boards/detail";
    }

    // 게시글 삭제 폼
    @GetMapping("/deleteform")
    public String deleteForm(@RequestParam("id") Long id, Model model) {
        Board board = boardService.findBoardById(id);
        model.addAttribute("board", board);
        return "boards/deleteform";
    }

    // 게시글 삭제 후 -> 목록 페이지
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

    // 게시글 수정 폼
    @GetMapping("/updateform")
    public String editForm(@RequestParam("id") Long id, Model model) {
        model.addAttribute("board", boardService.findBoardById(id));
        return "boards/edit";
    }

    // 게시글 수정
    @PostMapping("/update")
    public String editBoard(@ModelAttribute Board board, RedirectAttributes redirectAttributes) {
        boardService.saveBoard(board);
        redirectAttributes.addFlashAttribute("message", "게시글 수정 성공!!");
        return "redirect:/list/view?id=" + board.getId();
    }
}

