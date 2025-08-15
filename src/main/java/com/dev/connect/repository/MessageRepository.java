package com.dev.connect.repository;

import com.dev.connect.entity.Message;
import com.dev.connect.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends JpaRepository<Message,String> {
     List<Message> findByReceiverOrderByMessagedAtDesc(User user);
     List<Message> findBySenderOrderByMessagedAtDesc(User user);
     @Query("select m from Message m where (m.sender = :user1 and m.receiver = :user2) or (m.sender = :user2 and m.receiver = :user1) order by m.messagedAt Desc")
     List<Message> findByTwoUserChat(@Param("user1") User user1,@Param("user2") User user2);
}
