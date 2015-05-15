package com.gracker.javareflect;

/**
 * Project name : JavaReflect . Package name : com.gracker.javareflect . Created by gaojack . Date :
 * 15/5/15 .
 */
public class SuperMan extends Person implements ActionInterface {
    @Override
    public String toString() {
        return "我是超人：我的名字叫：" + name.toString();
    }

    @Override
    public void walk() {
        System.out.println("I beleve I can Fly");
    }

    public void fly() {
        System.out.println("I can Fly , Truly");
    }

    public void fly(int time) {
        System.out.println("I can Fly for " + time + "times , Truly");
    }

}
