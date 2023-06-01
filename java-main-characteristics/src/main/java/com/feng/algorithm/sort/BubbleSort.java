package com.feng.algorithm.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 */
public class BubbleSort {

    public static void bubbleSore(int[] arr) {
        int len = arr.length;

        for (int i = 0; i < len - 1; i++) {
            boolean flag = true;
            for (int j = 0; j < len - 1 - i; j++) {
                // 前者大于后者 交换之
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = false;
                }
            }
            // 节省最后几趟过程，前面的元素可能已经有序了
            if (flag) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = new int[]{11, 25, 4, 999, 3, 1};
        bubbleSore(arr);
        System.out.println(Arrays.toString(arr));
    }
}
