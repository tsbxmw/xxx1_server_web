package com.json.domain;
public class Person {
	private String hello;
	private int id;
	public String name;
	private String address;
	
	public Person() {
		super();
	}
	public Person(int id, String name, String addrss) {
		super();
		this.id = id;
		this.name = name;
		this.address = addrss;
	}
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
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Override
	public String toString() {
		return "Person [addrss=" + address + ", id=" + id + ", name=" + name
				+ "]";
	}
	
}
