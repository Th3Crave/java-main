package com.feng.proxy.dynamic.cglib.demo1;

public class CglibProxyDemo {
    public static void main(String[] args) {
        // proxy
        UserServiceImpl userService = (UserServiceImpl) new UserCglibProxy().getCglibProxy(new UserServiceImpl());

        // call methods
        userService.findUserList();
        userService.addUser();
    }
}
