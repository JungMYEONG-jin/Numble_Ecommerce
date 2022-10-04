package com.mj.webmarket.service.user;

import com.mj.webmarket.entity.user.UserImage;
import com.mj.webmarket.repository.user.UserImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserImageServiceImpl implements UserImageService{

    private final UserImageRepository userImageRepository;

    @Transactional
    @Override
    public void updateMemberImage(UserImage userImage, String newPath, String newOriginalFileName, String newServerFileName) {
        userImage.updateImage(newPath, newOriginalFileName, newServerFileName);
    }

    @Override
    public UserImage findByUser(Long userId) {
        return userImageRepository.findByUserId(userId);
    }

    @Transactional
    @Override
    public UserImage save(UserImage userImage) {
        return userImageRepository.save(userImage);
    }
}
