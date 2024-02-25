package com.google.cms.repositories;

import com.google.cms.models.Card;
import com.google.cms.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CardRepo extends JpaRepository<Card, Long> {
    List<Card> findByDeletedFlag(Character deletedFlag);

    List<Card> findByUserAndDeletedFlag(User user, Character deletedFlag);
    @Query(value = "SELECT c.* FROM card c WHERE " +
            "c.color LIKE %:color% AND " +
            "c.name LIKE %:name% AND " +
            "c.status LIKE %:status% AND " +
            "c.user_id = :userId AND " +
            "c.deleted_flag = :deletedFlag AND " +
            "DATE(c.posted_time) BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Page<Card> findByUserAndNameAndColorAndStatusAndDeletedFlagAndPostedTimeBetweenOrderByPostedTimeDesc(
            @Param("userId") Long userId,
            @Param("name") String name,
            @Param("color") String color,
            @Param("status") String status,
            @Param("deletedFlag") char deletedFlag,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable
    );
    @Query(value = "SELECT c.* FROM card c WHERE " +
            "c.name LIKE %:name% AND " +
            "c.color LIKE %:color% AND " +
            "c.status LIKE %:status% AND " +
            "c.deleted_flag = :deletedFlag AND " +
            "DATE(c.posted_time) BETWEEN :fromDate AND :toDate", nativeQuery = true)
    Page<Card> findByNameAndColorAndStatusAndDeletedFlagAndPostedTimeBetweenOrderByPostedTimeDesc(
            @Param("name") String name,
            @Param("color") String color,
            @Param("status") String status,
            @Param("deletedFlag") char deletedFlag,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate") LocalDate toDate,
            Pageable pageable
    );

}

