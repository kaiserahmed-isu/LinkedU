package com.linkedu.it353.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.linkedu.it353.model.Message;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Kaiser on 5/2/2017.
 */
@Repository("messageRepository")
public interface MessageRepository extends JpaRepository<Message, Long>{
    public List<Message> findAllByToUserIdAndFromUserIdOrderByCreatedDateAsc(int toUserId, int fromUserId);
    public Message findByMessageId(int messageId);
}
