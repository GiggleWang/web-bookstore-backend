package com.example.bookstorespringboot.repository;

import com.example.bookstorespringboot.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("SELECT o FROM Order o JOIN o.items i JOIN i.book b WHERE b.name LIKE %:bookName% AND o.orderDate BETWEEN :startDate AND :endDate")
    Page<Order> findByBookNameContaining(@Param("bookName") String bookName,
                                         @Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate,
                                         Pageable pageable);

    Page<Order> findAll(Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.userId = :userId")
    Page<Order> findByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT o FROM Order o JOIN o.items i JOIN i.book b WHERE o.userId = :userId AND b.name LIKE %:bookName% AND o.orderDate BETWEEN :startDate AND :endDate")
    Page<Order> findByUserIdAndBookNameContaining(@Param("userId") Integer userId,
                                                  @Param("bookName") String bookName,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate,
                                                  Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.items i JOIN FETCH i.book b WHERE o.userId = :userId AND b.id = i.book.id")
    List<Order> findByUserIdWithBooks(@Param("userId") Integer userId);

    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findByUserIdAndOrderDateBetween(Integer userId, LocalDateTime orderDate, LocalDateTime orderDate2);
}


