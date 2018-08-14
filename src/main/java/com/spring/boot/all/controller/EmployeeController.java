package com.spring.boot.all.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.all.dao.EmployeeDao;

@RestController
public class EmployeeController {

	@Autowired
	private EmployeeDao dao;

	@RequestMapping("/getEmployeeList")
	public List getemployees() {

		System.out.println("Employee details are::::->" + dao.getEmployeeList());
		return dao.getEmployeeList();

	}

}
