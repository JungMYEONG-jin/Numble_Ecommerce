package com.mj.webmarket.service.user;

import com.mj.webmarket.entity.user.UserImage;
import org.springframework.stereotype.Service;

@Service
public interface UserImageService {
    void updateMemberImage(UserImage userImage, String URL, String originalFileName, String serverURL);
    UserImage findByUser(Long userId);
    UserImage save(UserImage userImage);
}
