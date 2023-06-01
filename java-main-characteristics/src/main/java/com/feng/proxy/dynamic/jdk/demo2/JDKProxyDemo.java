package com.feng.proxy.dynamic.jdk.demo2;

public class JDKProxyDemo {

    public static void main(String[] args) {
        System.out.println("1");
        // proxy
        IUserService userServiceProxy = new UserJDKProxy1(new UserServiceImpl()).getLoggingProxy();
        // call method
        userServiceProxy.findUserList();
        userServiceProxy.addUser();


        System.out.println("2");
        // proxy
        IUserService userServiceProxy2 = UserJDKProxy2.getProxy(new UserServiceImpl(), IUserService.class);
        // call method
        userServiceProxy2.findUserList();
        userServiceProxy2.addUser();
    }
}
