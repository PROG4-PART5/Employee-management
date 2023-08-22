package com.example.prog4.repository;

import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.repository.SimpleEmployee.entity.Employee;
import java.util.List;


public interface RepositoryFacade {

    Employee getOne(String id);
    List<Employee> getAll(EmployeeFilter filter);
    void saveOne(Employee employee);

}
