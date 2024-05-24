package org.example.simpleboard.repository;

import org.example.simpleboard.model.Board;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BoardRepository extends CrudRepository<Board, Long>,PagingAndSortingRepository<Board, Long> {
}
