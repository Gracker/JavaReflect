package com.gracker.javareflect;

/**
 * Project name : JavaReflect . Package name : com.gracker.javareflect . Created by gaojack . Date :
 * 15/5/15 .
 */
public class Person {

    public String name;
    public String age;

    public Person() {
        this(null, null);
    }

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Name = " + name + " Age = " + age;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }
}
