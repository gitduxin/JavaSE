package com.dx.socket;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketClient_Test {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(InetAddress.getLocalHost(),9999);
        OutputStream outputStream = socket.getOutputStream();
        FileInputStream InputStream = new FileInputStream("D:\\wallhaven-x6225z.jpg");
        byte[] bytes = new byte[1024];
        int len;
        while((len = InputStream.read(bytes)) != -1){
            outputStream.write(bytes,0,len);
        }
        outputStream.flush();
        outputStream.close();
        InputStream.close();
        socket.close();
    }
}
