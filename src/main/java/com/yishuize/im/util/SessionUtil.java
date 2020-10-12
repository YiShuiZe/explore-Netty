package com.yishuize.im.util;

import com.yishuize.im.attribute.Attributes;
import com.yishuize.im.session.Session;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <h3>netty-study</h3>
 * <p>
 *     SessionUtil 里面维持了一个 useId -> channel 的映射 map，调用 bindSession() 方法的时候，在 map 里面保存这个映射关系，SessionUtil 还提供了 getChannel() 方法，这样就可以通过 userId 拿到对应的 channel。
 * 除了在 map 里面维持映射关系之外，在 bindSession() 方法中，我们还给 channel 附上了一个属性，这个属性就是当前用户的 Session，我们也提供了 getSession() 方法，非常方便地拿到对应 channel 的会话信息。
 * 这里的 SessionUtil 其实就是前面小节的 LoginUtil，这里重构了一下，其中 hasLogin() 方法，只需要判断当前是否有用户的会话信息即可。
 * 在 LoginRequestHandler 中，我们还重写了 channelInactive() 方法，用户下线之后，我们需要在内存里面自动删除 userId 到 channel 的映射关系，这是通过调用 SessionUtil.unBindSession() 来实现的。
 * </p>
 * Created by yang on 20-5-15 下午9:33
 * updated by yang on 20-5-15 下午9:33
 */
public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();

    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel) {
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            userIdChannelMap.remove(session.getUserId());
            channel.attr(Attributes.SESSION).set(null);
            System.out.println(session + "退出登录！");
        }
    }

    public static boolean hasLogin(Channel channel) {
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId) {
        return userIdChannelMap.get(userId);
    }

    public static void bindChannelGroup(String groupId, ChannelGroup channelGroup) {
        groupIdChannelGroupMap.put(groupId, channelGroup);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }

}
