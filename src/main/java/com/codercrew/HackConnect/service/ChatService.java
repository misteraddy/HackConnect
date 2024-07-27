package com.codercrew.HackConnect.service;

import com.codercrew.HackConnect.model.Chat;
import com.codercrew.HackConnect.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    Chat createChat(Chat chat) {
        return chatRepository.save(chat);
    }
}
