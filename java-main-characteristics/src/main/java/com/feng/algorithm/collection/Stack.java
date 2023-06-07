package com.feng.algorithm.collection;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class Stack {

    public static void main(String[] args) {
        // java用Deque来实现栈

        // 每日温度
//        int[] T = {22,23,24,20,19,18,26,25};
        int[] T = {73, 74, 75, 71, 69, 72, 76, 73};

        Deque<Integer> stack = new ArrayDeque<>();
        int[] result = new int[T.length];

        for (int i = 0; i < T.length; i++) {
            while (!stack.isEmpty() && T[i] > T[stack.peek()]) {
                int pre = stack.pop();
                result[pre] = i - pre;
            }
            stack.push(i);
        }
        System.out.println(Arrays.toString(result));
    }
}
