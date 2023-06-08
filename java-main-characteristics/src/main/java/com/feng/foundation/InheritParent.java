package com.feng.foundation;

public class InheritParent {

    /**
     * public方法被子类继承
     */
    public void scan() {
        doScan();
    }

    protected void doScan() {
        System.out.println("Parent doScan");
    }
}
