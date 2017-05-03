package com.linkedu.it353.service;


import com.linkedu.it353.model.Message;

import java.util.List;

/**
 * Created by Kaiser on 5/2/2017.
 */
public interface MessageService {
    public List<Message> findConversion(int toUserId, int fromUserId);
    public Message findByMessageId(int messageId);
    public void SaveMessage(Message message);
}
