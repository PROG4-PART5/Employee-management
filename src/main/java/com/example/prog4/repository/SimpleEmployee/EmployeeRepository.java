package com.example.prog4.repository.SimpleEmployee;

import com.example.prog4.repository.SimpleEmployee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
