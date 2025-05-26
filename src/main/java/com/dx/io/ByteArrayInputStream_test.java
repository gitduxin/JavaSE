package com.dx.io;

import java.io.*;

public class ByteArrayInputStream_test {
    public static void main(String[] args) {
        String s = "D:\\1.txt";
        String e = "D:\\2.txt";
        copyFile(s,e);
    }

    private static void copyFile(String s, String e){
        try (
            FileInputStream fis = new FileInputStream(s);
            FileOutputStream fos = new FileOutputStream(e)) {
            byte[] b = new byte[1024];
            int len;
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);
                fos.flush();
            }
        } catch (IOException ex) {
            System.out.println("该目录没找到该文件");
        }
    }


}
