package com.mj.webmarket.service.user;

import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.exception.UserNotFoundException;
import com.mj.webmarket.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    @Transactional
    public User join(User user) {
        // password encode
        user.encodePassword(encoder);
        return userRepository.save(user);
    }

    @Override
    public User findUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException());
    }

    @Override
    @Transactional
    public User update(User user, String newNickName) {
       return user.updateNickName(newNickName);
    }

    @Override
    public boolean duplicateEmailCheck(String email) {
        return userRepository.existsByEmail(email);
    }
}
