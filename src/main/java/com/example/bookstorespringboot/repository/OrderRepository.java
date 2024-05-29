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
    @Query("SELECT DISTINCT o FROM Order o JOIN o.items i JOIN i.book b WHERE b.name LIKE CONCAT('%', :bookName, '%')")
    Page<Order> findByBookName(@Param("bookName") String bookName, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o WHERE o.orderDate BETWEEN :start AND :end")
    Page<Order> findByDateRange(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o JOIN o.items i JOIN i.book b WHERE b.name LIKE CONCAT('%', :bookName, '%') AND o.orderDate BETWEEN :start AND :end")
    Page<Order> findByBookNameAndDateRange(@Param("bookName") String bookName, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o JOIN o.items i JOIN i.book b WHERE b.name LIKE CONCAT('%', :bookName, '%') AND o.orderDate BETWEEN :startDate AND :endDate")
    Page<Order> findByBookNameContaining(@Param("bookName") String bookName,
                                         @Param("startDate") LocalDateTime startDate,
                                         @Param("endDate") LocalDateTime endDate,
                                         Pageable pageable);
    Page<Order> findAll(Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o WHERE o.userId = :userId")
    Page<Order> findByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o JOIN o.items i JOIN i.book b WHERE o.userId = :userId AND b.name LIKE CONCAT('%', :bookName, '%') AND o.orderDate BETWEEN :startDate AND :endDate")
    Page<Order> findByUserIdAndBookNameContaining(@Param("userId") Integer userId,
                                                  @Param("bookName") String bookName,
                                                  @Param("startDate") LocalDateTime startDate,
                                                  @Param("endDate") LocalDateTime endDate,
                                                  Pageable pageable);


    @Query("SELECT DISTINCT o FROM Order o JOIN FETCH o.items i JOIN FETCH i.book b WHERE o.userId = :userId AND b.id = i.book.id")
    List<Order> findByUserIdWithBooks(@Param("userId") Integer userId);

    List<Order> findByOrderDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    List<Order> findByUserIdAndOrderDateBetween(Integer userId, LocalDateTime orderDate, LocalDateTime orderDate2);

    @Query("SELECT DISTINCT o FROM Order o WHERE o.userId = :userId AND o.orderDate BETWEEN :start AND :end")
    Page<Order> findByUserIdAndDateRange(@Param("userId") Integer userId, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o JOIN o.items i JOIN i.book b WHERE o.userId = :userId AND b.name LIKE CONCAT('%', :bookName, '%')")
    Page<Order> findByUserIdAndBookName(@Param("userId") Integer userId, @Param("bookName") String bookName, Pageable pageable);

    @Query("SELECT DISTINCT o FROM Order o JOIN o.items i JOIN i.book b WHERE o.userId = :userId AND b.name LIKE CONCAT('%', :bookName, '%') AND o.orderDate BETWEEN :start AND :end")
    Page<Order> findByUserIdAndBookNameAndDateRange(@Param("userId") Integer userId, @Param("bookName") String bookName, @Param("start") LocalDateTime start, @Param("end") LocalDateTime end, Pageable pageable);

}


