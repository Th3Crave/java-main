package com.feng.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序
 * https://blog.csdn.net/weixin_44531966/article/details/116464294
 */
public class QuickSort {

    public static void main(String[] args) {
        int arr[] = {3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48};
        quickSort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

    /**
     * 1.从序列中随机挑出一个元素，做为基准（pivot，这里选择序列的最左边元素作为基准）；
     * 2.重新排列序列，将所有比基准值小的元素摆放在基准前面，所有比基准值大的摆在基准的后面。该操作结束之后，该基准就处于数列的中间位置。这个操作称为分区（partition）；
     * 3.递归地把小于基准值元素的子序列和大于基准值元素的子序列进行上述操作即可。
     */
    public static void quickSort(int[] arr, int low, int high) {
        if (low > high) {
            return;
        }

        int i = low, j = high;
        /**
         * 基准位
         */
        int temp = arr[low];

        while (i < j) {
            // 先从右边开始往左递减，找到比temp小的值停止
            while (temp <= arr[j] && i < j) {
                j--;
            }
            // 再从左边开始往右递增，找到比temp大的值停止
            while (temp >= arr[i] && i < j) {
                i++;
            }
            // 满足i<j就交换，继续循环wile(i<j)
            if (i < j) {
                int t = arr[j];
                arr[j] = arr[i];
                arr[i] = t;
            }
        }
        // 此时i=j 将基准位与a[i\j]交换
        arr[low] = arr[i];
        arr[i] = temp;

        // 左递归
        quickSort(arr, low, i - 1);
        // 右递归
        quickSort(arr, i + 1, high);
    }


}
