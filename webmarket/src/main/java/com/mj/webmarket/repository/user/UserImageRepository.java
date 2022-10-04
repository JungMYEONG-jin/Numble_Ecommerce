package com.mj.webmarket.repository.user;

import com.mj.webmarket.entity.user.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepository extends JpaRepository<UserImage, Long> {

    UserImage findByUserId(Long userId);
}
