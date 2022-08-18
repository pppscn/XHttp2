package com.xuexiang.xhttp2.utils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

/**
 * @author pppscn <35696959@qq.com>
 * @date 2022/08/18
 **/
public final class InternalSslSocketFactory extends SSLSocketFactory {

    private static final String[] TLS_SUPPORT_VERSION = {"TLSv1.1", "TLSv1.2", "TLSv1.3"};
    private final SSLSocketFactory mSSLSocketFactory;

    public InternalSslSocketFactory(SSLSocketFactory sslSocketFactory) {
        this.mSSLSocketFactory = sslSocketFactory;
    }

    @Override
    public String[] getDefaultCipherSuites() {
        return mSSLSocketFactory.getDefaultCipherSuites();
    }

    @Override
    public String[] getSupportedCipherSuites() {
        return mSSLSocketFactory.getSupportedCipherSuites();
    }

    @Override
    public Socket createSocket() throws IOException {
        return enableTlsOnSocket(mSSLSocketFactory.createSocket());
    }

    @Override
    public Socket createSocket(Socket s, String host, int port, boolean autoClose) throws IOException {
        return enableTlsOnSocket(mSSLSocketFactory.createSocket(s, host, port, autoClose));
    }

    @Override
    public Socket createSocket(String host, int port) throws IOException {
        return enableTlsOnSocket(mSSLSocketFactory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(String host, int port, InetAddress localHost, int localPort) throws IOException {
        return enableTlsOnSocket(mSSLSocketFactory.createSocket(host, port, localHost, localPort));
    }

    @Override
    public Socket createSocket(InetAddress host, int port) throws IOException {
        return enableTlsOnSocket(mSSLSocketFactory.createSocket(host, port));
    }

    @Override
    public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException {
        return enableTlsOnSocket(mSSLSocketFactory.createSocket(address, port, localAddress, localPort));
    }

    private Socket enableTlsOnSocket(Socket socket) {
        if (socket instanceof SSLSocket) {
            ((SSLSocket) socket).setEnabledProtocols(TLS_SUPPORT_VERSION);
        }
        return socket;
    }
}
