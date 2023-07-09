package com.x.microservice.spi;

public class U_Student implements IUser {
    @Override
    public String showName() {
        System.out.println("this is Student Y");
        return null;
    }
}
