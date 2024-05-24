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

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 모든 게시글 조회
    @Transactional(readOnly = true)
    public Iterable<Board> findAllBoards() {
        return boardRepository.findAll();
    }

    // 새로운 게시글 저장
    @Transactional
    public Board saveBoard(Board board) {
        board.prePersist(); // 생성하면 생성 날짜 설정
        return boardRepository.save(board);
    }

    // id 조회
    @Transactional(readOnly = true)
    public Board findBoardById(Long id) {
        return boardRepository.findById(id).orElse(null);
    }

    // id 삭제
    @Transactional
    public void deleteBoardById(Long id) {
        boardRepository.deleteById(id);
    }

    // 게시글 목록 조회
    @Transactional(readOnly = true)
    public Page<Board> findAllBoards(Pageable pageable) {
        Pageable sortedByDescId = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "id"));
        return boardRepository.findAll(sortedByDescId);
    }
}
