package com.yishuize.im.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h3>netty-study</h3>
 * <p>${description}</p>
 * Created by yang on 20-5-15 下午9:26
 * updated by yang on 20-5-15 下午9:26
 */
@Data
@NoArgsConstructor
public class Session {

    // 用户唯一标识
    private String userId;
    private String userName;

    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return userId + ":" + userName;
    }
}
