package com.json.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.json.domain.Person;

public class JsonService {

	public JsonService() {
	}
	
	public Person getPerson(){
		Person person = new Person(1001,"jack","�Ϻ�������");
		return person;
	}
	public List<Person> getListPerson(){
		List<Person> list = new ArrayList<Person>();
		Person person1 = new Person(1001,"jack","�Ϻ�������");
		Person person2 = new Person(1002,"rose","�Ϻ�������");
		Person person3 = new Person(1003,"mick","�Ϻ�������");
		list.add(person1);
		list.add(person2);
		list.add(person3);
		return list;
	}
	
	public List<String> getListString(){
		List<String> list = new ArrayList<String>();
		list.add("����");
		list.add("�Ϻ�");
		list.add("����");
		return list;
	}
	
	public List<Map<String,Object>> getListMaps(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map1 = new HashMap<String, Object>();
		Map<String,Object> map2 = new HashMap<String, Object>();
		map1.put("id", 1001);
		map1.put("name", "jack");
		map1.put("address", "����");
		map2.put("id", 1001);
		map2.put("name", "rose");
		map2.put("address", "�Ϻ�");
		list.add(map1);
		list.add(map2);
		return list;
	}
}
