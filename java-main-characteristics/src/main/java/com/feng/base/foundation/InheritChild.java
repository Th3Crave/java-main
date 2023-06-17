package com.feng.base.foundation;

public class InheritChild extends InheritParent {

    protected void doScan() {
        System.out.println("Child doScan");
    }


    public static void main(String[] args) {
        InheritChild child = new InheritChild();
        child.scan();
    }
}
