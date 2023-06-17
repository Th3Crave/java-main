package com.feng.base.proxy.dynamic.cglib.demo1;

public class CglibProxyDemo {
    public static void main(String[] args) {
        // proxy
        UserService userService = (UserService) new UserCglibProxy().getCglibProxy(UserService.class);

        // call methods
        userService.findUserList();
        userService.addUser();
    }
}
