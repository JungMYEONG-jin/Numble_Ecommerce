package com.mj.webmarket.entity.user;

import com.mj.webmarket.common.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class UserImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filePath;
    private String serverFileName;
    private String originalFileName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserImage(String filePath, String serverFileName, String originalFileName, User user){
        this.filePath = filePath;
        this.serverFileName = serverFileName;
        this.originalFileName = originalFileName;
        this.user = user;
    }

    public void updateImage(String filePath, String serverFileName, String originalFileName){
        this.filePath = filePath;
        this.serverFileName = serverFileName;
        this.originalFileName = originalFileName;
    }

}
