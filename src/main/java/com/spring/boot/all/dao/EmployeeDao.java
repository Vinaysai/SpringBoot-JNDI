package com.spring.boot.all.dao;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.spring.boot.all.model.Employee;

@Component
public class EmployeeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public java.util.List<Employee> getEmployeeList() {
		List<Map<String, Object>> employees = jdbcTemplate.queryForList("select * from spring.employee");
		return employees.stream().map(e -> {
			Employee employee = new Employee();
			employee.setEmployeeName((String) e.get("employeename"));
			employee.setEmployeeId((Integer) e.get("idemployee"));
			employee.setEmployeeRole((String) e.get("employeeRole"));
			return employee;
		}).collect(Collectors.toList());
	}

}
