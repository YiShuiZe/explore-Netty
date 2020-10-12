package com.yishuize.im.protocol;

import com.yishuize.im.protocol.request.*;
import com.yishuize.im.protocol.response.*;
import com.yishuize.im.serialize.JSONSerializer;
import com.yishuize.im.serialize.Serializer;
import io.netty.buffer.ByteBuf;

import java.util.HashMap;
import java.util.Map;

import static com.yishuize.im.protocol.command.Command.*;

/**
 * <h3>netty-study</h3>
 * <p>
 *     编码：封装成二进制的过程
 * </p>
 * Created by yang on 20-5-5 下午10:03
 * updated by yang on 20-5-5 下午10:03
 */
public class PacketCodec {

    // 魔数
    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodec INSTANCE = new PacketCodec();

    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    private PacketCodec() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);
        packetTypeMap.put(LOGOUT_REQUEST, LogoutRequestPacket.class);
        packetTypeMap.put(LOGOUT_RESPONSE, LogoutResponsePacket.class);
        packetTypeMap.put(CREATE_GROUP_REQUEST, CreateGroupRequestPacket.class);
        packetTypeMap.put(CREATE_GROUP_RESPONSE, CreateGroupResponsePacket.class);
        packetTypeMap.put(JOIN_GROUP_REQUEST, JoinGroupRequestPacket.class);
        packetTypeMap.put(JOIN_GROUP_RESPONSE, JoinGroupResponsePacket.class);
        packetTypeMap.put(QUIT_GROUP_REQUEST, QuitGroupRequestPacket.class);
        packetTypeMap.put(QUIT_GROUP_RESPONSE, QuitGroupResponsePacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_REQUEST, ListGroupMembersRequestPacket.class);
        packetTypeMap.put(LIST_GROUP_MEMBERS_RESPONSE, ListGroupMembersResponsePacket.class);
        packetTypeMap.put(GROUP_MESSAGE_REQUEST, GroupMessageRequestPacket.class);
        packetTypeMap.put(GROUP_MESSAGE_RESPONSE, GroupMessageResponsePacket.class);
        packetTypeMap.put(HEARTBEAT_REQUEST, HeartBeatRequestPacket.class);
        packetTypeMap.put(HEARTBEAT_RESPONSE, HeartBeatResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JSONSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    // 编码： 封装成二进制的过程
    // PacketCodeC 不再需要手动创建对象，不再需要再把创建完的 ByteBuf 进行返回
    public ByteBuf encode(ByteBuf byteBuf, Packet packet) {
        // 1. 创建 ByteBuf 对象
        //ByteBuf byteBuf = byteBufAllocator.ioBuffer();
        // 1. 序列化 Java 对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        // 3. 实际编码过程
        byteBuf
                .writeInt(MAGIC_NUMBER)
                .writeByte(packet.getVersion())
                .writeByte(Serializer.DEFAULT.getSerializerAlgorithm())
                .writeByte(packet.getCommand())
                .writeInt(bytes.length)
                .writeBytes(bytes);
        return byteBuf;
    }

    // 解码： 解析Java对象的过程
    public Packet decode(ByteBuf byteBuf) {
        // 跳过 magic number
        byteBuf.skipBytes(4);

        // 跳过版本号
        byteBuf.skipBytes(1);

        // 序列化算法标识
        byte serializeAlgorithm = byteBuf.readByte();

        // 指令
        byte command = byteBuf.readByte();

        // 数据包长度
        int length = byteBuf.readInt();

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        Class<? extends Packet> requestType = getRequestType(command);
        Serializer serializer = getSerializer(serializeAlgorithm);

        if (requestType != null && serializer != null) {
            return serializer.deserialize(requestType, bytes);
        }

        return null;
    }

    private Serializer getSerializer(byte serializerAlgorithm) {
        return serializerMap.get(serializerAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
