package com.dx.test;

import java.util.Arrays;

public class Test3 {
    public static void main(String[] args) {
        int [] arr= {1,3,5,7,9,11};
        System.out.println(zb(arr,11));
    }


    public static void zs(){
        for(int x=0;x<=100;x++){
            if(x<=1)
                break;
        }
    }

    public static int ft(int n){
        if(n>2){
            return ft(n-1)+ft(n-2);
        }
        return 1;
    }

    public static int zb(int [] arr , int x){
        int low, high,mid;
        low = 0;
        high=arr.length-1;
        while(low<=high){
            mid = low +(high-low)*(x-arr[low])/(arr[high]-arr[low]);
            if(x<arr[mid])
                high = mid-1;
            else if(x>arr[mid])
                low = mid+1;
            else return mid;
        }
        return 0;
    }
}

