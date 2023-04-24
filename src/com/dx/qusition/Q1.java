package com.dx.qusition;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Q1 {
    public static void main(String[] args) {
        q6();
    }

    public static void q1(){
        int [] arr = {1,2,3,4,5};
        for(int x=0;x<arr.length;x++){
            System.out.print(arr[x]+" ");
        }
    }

    /**
     * 现有一个小数数组{12.9, 53.54, 75.0, 99.1, 3.14}。请编写代码，找出数组中的最小值并打印。
     */
    public static void q2(){
        double [] d = {12.9, 53.54, 75.0, 99.1, 3.14};
        double min=d[0];

        for(int x=1;x<d.length;x++){
            if(min>d[x]){
                min = d[x];
            }
        }
        System.out.println(min);
    }


    public static void q3(){
        int [] arr =new int[6];
        for(int x=0;x<6;x++){
            Random random = new Random();
            arr[x]  = random.nextInt(100);
        }
        int sum = 0;
        for(int s : arr){
            sum +=s;
        }

        System.out.println(sum);
    }

    public static void q4(){
        //1.定义数组
        int[] arr = {2, 3, 5, 7, 9};
        //2.定义变量存储最终的整数
        int num = 0;
        //3.对数组进行遍历
        for (int i = 0; i < arr.length; i++) {
            //4.计算，高位乘以10再加上当前位的值
            num = num * 10 + arr[i];
        }
        //5.打印结果
        System.out.println(num);
    }

    public static void q5(){
        int sum = 0;
        int[] arr = {72, 89, 65, 87, 91, 82, 71, 93, 76, 68};
        for(int s : arr){
            sum +=s;
        }
        System.out.println(sum/arr.length);
    }

    public static void q6(){
        int[] arr = {12,14,23,45,66,68,70,77,90,91};
        int[] nrr = new int[11];
        Scanner scanner = new Scanner(System.in);
        int c = scanner.nextInt();
        int index = 0;
        for(int x=0;x<arr.length;x++){
            if(arr[x]<=c) {
                nrr[x] = arr[x];
                index = x+1;
            }else {
                nrr[x+1] = arr[x];
            }
        }
        nrr[index] = c;
        System.out.println(Arrays.toString(nrr));
    }

    public static void q7(){
        int[] arr = {12,14,23,45,66,68,70,77,90,91};
        for(int x=0;x<arr.length/2-1;x++){
            
        }
    }

}
