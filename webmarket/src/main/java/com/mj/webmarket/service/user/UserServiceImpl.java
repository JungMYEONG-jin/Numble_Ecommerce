package com.mj.webmarket.service.user;

import com.mj.webmarket.entity.dto.user.UserResponseDto;
import com.mj.webmarket.entity.user.User;
import com.mj.webmarket.exception.UserNotFoundException;
import com.mj.webmarket.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

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
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("해당 이메일로 가입된 계정이 존재하지 않습니다."));
    }

    @Override
    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("해당 아이디가 존재하지 않습니다."));
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

    @Override
    public UserResponseDto toUserResponseDto(User user) {
        String userImg = "/images/carrot.png";
        return UserResponseDto.builder().userId(user.getId()).nickName(user.getNickname()).profileImage(user.getUserImage()==null?userImg:user.getUserImage().getServerFileName())
                .heartProducts(user.getHearts().stream().map(heart -> heart.getProductInfo()).collect(Collectors.toList())).build();
    }

}
