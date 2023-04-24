package com.dx.io;

import java.io.*;

public class InputeStreamTest {
    public static void main(String[] args) throws IOException {
//        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream("D:\\宝石打孔.txt")));
//        String result;
//        System.out.println(bufferedReader.markSupported());
//        while((result = bufferedReader.readLine()) !=null){
//            System.out.println(result);
//        }

        BufferedInputStream bis = new BufferedInputStream(new FileInputStream("D:\\宝石打孔.txt"));
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:\\1234.txt"));
        byte [] bts = new byte[1024];
        int len;
        while((len = bis.read(bts)) != -1){
            bos.write(bts,0,len);
        }
        bos.flush();    
        bos.close();
        bis.close();
    }
}
