package com.dx.socket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer_Test {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9999);
        System.out.println("监听端口9999，等待客户端连接");
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        FileOutputStream fos = new FileOutputStream("D:\\传奇\\上传图片.png");
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = inputStream.read(buffer)) != -1){
            fos.write(buffer, 0, len);
        }
        inputStream.close();
        fos.close();
        socket.close();
        serverSocket.close();
    }
}
