package com.course.design.build;

/**
 * @author qinlei
 * @date 2021/6/3 下午12:40
 */
public class Child {

    private int id;
    private String name;
    private int age;

    // 子产品
    private Father father;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Father getFather() {
        return father;
    }

    public void setFather(Father father) {
        this.father = father;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", name=" + name + ", age=" + age + ", father=" + father + "]";
    }
}
