package com.example.prog4.service;

import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.RepositoryFacade;
import com.example.prog4.repository.SimpleEmployee.EmployeeRepository;
import com.example.prog4.repository.SimpleEmployee.dao.EmployeeManagerDao;
import com.example.prog4.repository.SimpleEmployee.entity.Employee;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmployeeService {
    private RepositoryFacade repositoryFacade;

    public Employee getOne(String id){
        return repositoryFacade.getOne(id);
    }

    public List<Employee> getAll(EmployeeFilter filter){
        return repositoryFacade.getAll(filter);
    }

    public void saveOne(Employee employee){
        repositoryFacade.saveOne(employee);
    }

}