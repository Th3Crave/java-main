package com.feng.base.proxy.dynamic.jdk.demo2;

import java.util.Collections;
import java.util.List;

public class UserServiceImpl implements IUserService {

    /**
     * find user list.
     *
     * @return user list
     */
    @Override
    public List<User> findUserList() {
        return Collections.singletonList(new User("com/feng", 18));
    }

    /**
     * add user
     */
    @Override
    public User addUser() {
        // do something
        System.out.println("add user ---- 2023");
        return new User("2023", 2023);
    }

}