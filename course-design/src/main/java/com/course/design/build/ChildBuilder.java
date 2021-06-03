package com.course.design.build;

/**
 * 构建器
 * 
 * @author qinlei
 * @date 2021/6/3 下午12:41
 */
public class ChildBuilder {

	/**
	 * 需要构建的对象
	 */
	private Child child = new Child();

	public ChildBuilder id(int id) {
		child.setId(id);
		return this;
	}

	public ChildBuilder name(String name) {
		child.setName(name);
		return this;
	}

	public ChildBuilder age(int age) {
		child.setAge(age);
		return this;
	}

	public ChildBuilder father(String fatherName) {
		Father father = new Father();
		father.setName(fatherName);
		child.setFather(father);
		return this;
	}

	// 构建对象
	public Child build() {
		return child;
	}
}
