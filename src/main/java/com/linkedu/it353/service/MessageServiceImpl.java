package com.linkedu.it353.service;

import com.linkedu.it353.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linkedu.it353.repository.MessageRepository;

import java.util.List;

/**
 * Created by Kaiser on 5/2/2017.
 */
@Service("messageService")
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> findConversion(int toUserId, int fromUserId) {
        return messageRepository.findAllByToUserIdAndFromUserIdOrderByCreatedDateAsc(toUserId,fromUserId);
    }

    @Override
    public Message findByMessageId(int messageId) {
        return messageRepository.findByMessageId(messageId);
    }

    @Override
    public void SaveMessage(Message message) {

        messageRepository.save(message);

    }
}