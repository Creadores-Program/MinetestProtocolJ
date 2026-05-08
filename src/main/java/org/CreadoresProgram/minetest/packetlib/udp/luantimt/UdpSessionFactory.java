package org.CreadoresProgram.minetest.packetlib.udp.luantimt;

import com.github.steveice10.packetlib.Client;
import com.github.steveice10.packetlib.ConnectionListener;
import com.github.steveice10.packetlib.ProxyInfo;
import com.github.steveice10.packetlib.Server;
import com.github.steveice10.packetlib.Session;
import com.github.steveice10.packetlib.SessionFactory;

/**
 * A session factory used to create UDP sessions.
 */
public class UdpSessionFactory implements SessionFactory {
    private ProxyInfo clientProxy;

    public UdpSessionFactory() {
    }

    public UdpSessionFactory(ProxyInfo clientProxy) {
        this.clientProxy = clientProxy;
    }

    @Override
    public Session createClientSession(final Client client) {
        return new UdpClientSession(client.getHost(), client.getPort(), client.getPacketProtocol(), client, this.clientProxy);
    }

    @Override
    public ConnectionListener createServerListener(final Server server) {
        return new UdpConnectionListener(server.getHost(), server.getPort(), server);
    }
}
