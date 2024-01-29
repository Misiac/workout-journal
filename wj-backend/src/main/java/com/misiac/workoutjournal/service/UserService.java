package com.misiac.workoutjournal.service;

import com.misiac.workoutjournal.entity.User;
import com.misiac.workoutjournal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void checkIfUserExistsInDb(String email) {

        var user = userRepository.findUserByEmail(email);

        if (user.isEmpty()) {

            User newUser = new User();
            newUser.setEmail(email);
            userRepository.save(newUser);
        }
    }
}
