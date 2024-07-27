package com.codercrew.HackConnect.service;

import com.codercrew.HackConnect.config.JwtProvider;
import com.codercrew.HackConnect.model.User;
import com.codercrew.HackConnect.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository ;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository ;
    }

    public User findUserProfileByJwt(String jwt) throws Exception {
        String email = JwtProvider.getEmailFromToken(jwt);
        return findUserByEmail(email);
    }

    public User findUserByEmail(String email) throws Exception {

        User user = userRepository.findByEmail(email);

        if(user == null)
        {
            throw new Exception("user not found");
        }
        return user;
    }

    User findUserById(Long userId) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);

        if(optionalUser.isEmpty()) {
            throw new Exception("user not found");
        }
        return optionalUser.get();
    }

    User updateUsersProjectSize(User user,int number) {

        user.setProjectSize(user.getProjectSize() + number);

        User savedUser = userRepository.save(user);

        return savedUser;
    }


}
