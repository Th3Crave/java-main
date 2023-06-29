package com.feng.base.annotation.meta.retention;

public class RetentionTest {

    @SourcePolicy
    public void sourcePolicy() {
        System.out.println("hello");
    }

    @ClassPolicy
    public void classPolicy() {
        System.out.println("hello");
    }

    @RuntimePolicy
    public void runtimePolicy() {
        System.out.println("hello");
    }

    public static void main(String[] args) {
        System.out.println("hello");
        RetentionTest test = new RetentionTest();
        test.classPolicy();
        test.sourcePolicy();
        test.runtimePolicy();
    }
}
