package com.campus.management.repository;

import com.campus.management.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findTop10ByOrderByPostedDateDesc();
}
