package com.yishuize.netty.protocoltcp;

/**
 * <h3>netty-study</h3>
 * <p>
 *     协议包
 * </p>
 * Created by yang on 20-4-11 下午2:17
 * updated by yang on 20-4-11 下午2:17
 */
public class MessageProtocol {
    private int len; // 关键
    private byte[] content;

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }
}
