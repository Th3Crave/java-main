package com.feng.proxy.dynamic.cglib.demo1;

import java.util.Collections;
import java.util.List;

public class UserServiceImpl {

    /**
     * find user list.
     *
     * @return user list
     */
    public List<User> findUserList() {
        return Collections.singletonList(new User("feng", 18));
    }

    /**
     * add user
     */
    public User addUser() {
        // do something
        System.out.println("add user ---- 2023");
        return new User("2023", 2023);
    }

}
