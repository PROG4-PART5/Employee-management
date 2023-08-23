package com.example.prog4.repository;

import com.example.prog4.controller.mapper.EmployeeMapper;
import com.example.prog4.model.EmployeeFilter;
import com.example.prog4.model.exception.NotFoundException;
import com.example.prog4.repository.CnapsEmployee.CnapsEmployeeRepository;
import com.example.prog4.repository.CnapsEmployee.entity.CnapsEmployee;
import com.example.prog4.repository.SimpleEmployee.EmployeeRepository;
import com.example.prog4.repository.SimpleEmployee.dao.EmployeeManagerDao;
import com.example.prog4.repository.SimpleEmployee.entity.Employee;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
@AllArgsConstructor
public class RepositoryImpl implements RepositoryFacade{

    private EmployeeRepository repository;
    private EmployeeManagerDao employeeManagerDao;
    private CnapsEmployeeRepository cnapsEmployeeRepository;
    private EmployeeMapper employeeMapper;


    public Employee getOne(String id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Not found id=" + id));
    }

    public List<com.example.prog4.repository.SimpleEmployee.entity.Employee> getAll(EmployeeFilter filter) {
        Sort sort = Sort.by(filter.getOrderDirection(), filter.getOrderBy().toString());
        Pageable pageable = PageRequest.of(filter.getIntPage() - 1, filter.getIntPerPage(), sort);
        return employeeManagerDao.findByCriteria(
                filter.getLastName(),
                filter.getFirstName(),
                filter.getCountryCode(),
                filter.getSex(),
                filter.getPosition(),
                filter.getEntrance(),
                filter.getDeparture(),
                pageable
        );
    }

    public void saveOne(Employee employee) {
        CnapsEmployee employee1 = cnapsEmployeeRepository.findByEndToEndId(employee.getId());
        if(employee1 != null){
            employee.setCnaps(employee1.getCnaps());
        };
        repository.save(employee);
    }
}
