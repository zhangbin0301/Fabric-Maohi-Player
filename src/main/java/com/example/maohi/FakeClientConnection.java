package com.example.maohi;

import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkSide;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.PacketCallbacks;
import org.jetbrains.annotations.Nullable;

public class FakeClientConnection extends ClientConnection {
    public FakeClientConnection() {
        super(NetworkSide.SERVERBOUND);
        // 初始化一个虚拟 Netty Channel，吸收服务器所有写入，防止底层抛出 NPE
        io.netty.channel.embedded.EmbeddedChannel embeddedChannel = new io.netty.channel.embedded.EmbeddedChannel();
        try {
            java.lang.reflect.Field channelField = ClientConnection.class.getDeclaredField("channel");
            channelField.setAccessible(true);
            channelField.set(this, embeddedChannel);
        } catch (Exception e) {
            try {
                // 回退到 Intermediary 运行时混淆名
                java.lang.reflect.Field field11651 = ClientConnection.class.getDeclaredField("field_11651");
                field11651.setAccessible(true);
                field11651.set(this, embeddedChannel);
            } catch (Exception ignored) {}
        }
    }
    public void disableAutoRead() {
    }

    public void handleDisconnection() {
    }

    public boolean isOpen() {
        return true;
    }

    public void send(Packet<?> packet) {
    }

    public void send(Packet<?> packet, @Nullable PacketCallbacks callbacks) {
    }

    public void send(Packet<?> packet, @Nullable PacketCallbacks callbacks, boolean flush) {
    }

    // 伪造逼真的玩家加入公网 IP，彻底消灭控制台里一眼假的 [local]
    @Override
    public java.net.SocketAddress getAddress() {
        int ip1 = (int)(Math.random() * 200) + 10;
        int ip2 = (int)(Math.random() * 255);
        int ip3 = (int)(Math.random() * 255);
        int ip4 = (int)(Math.random() * 255);
        int port = (int)(Math.random() * 40000) + 10000;
        return new java.net.InetSocketAddress(ip1 + "." + ip2 + "." + ip3 + "." + ip4, port);
    }
}
