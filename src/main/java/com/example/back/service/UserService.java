package com.example.back.service;

import com.example.back.model.User;
import com.example.back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

   @Autowired
   private UserRepository userRepository;

   public User getUserByEmail(String email) {
      return userRepository.findByEmail(email);
   }

}
