package com.feng.algorithm.collection;

import java.util.Arrays;
import java.util.PriorityQueue;

public class TopK {
    /**
     * 求数组中前K个最小的元素
     * @param array
     * @param k
     * @return
     */
    public static int[] topk(int[] array, int k){
        //1.创建一个大小为k的大根堆
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(k, (o1, o2) -> o2 - o1);
        //2.遍历数组中当中的元素，前k个元素放到队列中
        for (int i = 0; i < array.length; i++) {
            if (maxHeap.size() < k){
                maxHeap.offer(array[i]);
            }else {
                int top = maxHeap.peek();
                if (top > array[i]){
                    maxHeap.poll();
                    maxHeap.offer(array[i]);//将新元素放入其中
                }
            }
        }
        int[] ret = new int[k];
        for (int i = 0; i < k; i++) {
            ret[i] = maxHeap.poll();
        }
        return ret;
    }
    public static void main(String[] args) {
        int[] array = {18,21,8,10,34,21};
        int[] ret = topk(array, 3);
        System.out.println(Arrays.toString(ret));
    }
}
