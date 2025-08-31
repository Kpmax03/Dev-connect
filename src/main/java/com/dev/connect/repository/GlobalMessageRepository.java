package com.dev.connect.repository;

import com.dev.connect.entity.GlobalMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GlobalMessageRepository extends JpaRepository<GlobalMessage,Long> {
    public List<GlobalMessage> findAllByOrderByTimeStampDesc();
}
