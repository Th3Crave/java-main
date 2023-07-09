package com.x.microservice.spi;

public class U_Teacher implements IUser {
    @Override
    public String showName() {
        System.out.println("this is Teacher X");
        return null;
    }
}
