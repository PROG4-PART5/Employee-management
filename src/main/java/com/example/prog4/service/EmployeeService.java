package com.example.prog4.service;

import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.repository.RepositoryImpl;
import com.example.prog4.repository.SimpleEmployee.entity.Employee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private RepositoryImpl repository;

    public Employee getOne(String id){
        return repository.getOne(id);
    }

    public List<Employee> getAll(EmployeeFilter filter){
        return repository.getAll(filter);
    }

    public void saveOne(Employee employee){
        repository.saveOne(employee);
    }

}