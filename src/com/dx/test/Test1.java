package com.dx.test;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Test1 {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException {
//        FileInputStream inputStream = new FileInputStream("D:\\下载\\1.txt");
//        FileOutputStream outputStream = new FileOutputStream("D:\\下载\\2.txt");
//        byte [] bytes = new byte[1024];
//        //返回值：每次读10个字节 到最后没有了 返回-1
//        int len= -1;
//        //在不等于-1 说明文件里还有字节 一直输入直到 返回值为-1
//        while ((len=inputStream.read(bytes)) != -1){
//            //在输入的同时输出，输入输出共用一个缓冲区 buf
//            outputStream.write(bytes,0,len);
//        FileChannel fin = inputStream.getChannel();
//        FileChannel font = outputStream.getChannel();
//        ByteBuffer buffer = ByteBuffer.allocate(1024);
//
//        while(true){
//            int r = fin.read(buffer);
//            if(r == -1){
//                break;
//            }
//            buffer.flip();
//            font.write(buffer);
//            buffer.clear();
//        }
        System.out.println(date1(12312312123l));
    }

    static String date1(long time){
        Date date = new Date();
        date.setTime(time);
        SimpleDateFormat  dd = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        return dd.format(date);
    }
}
