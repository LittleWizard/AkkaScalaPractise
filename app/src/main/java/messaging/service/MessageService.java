package messaging.service;


import messaging.entity.Message;
import org.springframework.stereotype.Component;
import messaging.repository.MessageRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class MessageService {

    @Inject
    private MessageRepository messageRepository;

    public boolean saveMessage(String userId, String content) {
        Message message = new Message();
        message.setUserId(userId);
        message.setContent(content);
        message.setCreationDate(new Date());
        messageRepository.save(message);
        return true;
    }

    public List<Message> getMessages(String userId, Integer limit) {
        List<Message> messages = new ArrayList<>();
        messageRepository.findByUser(userId ,limit).forEach(m -> messages.add(m));
        return messages;
    }

}
