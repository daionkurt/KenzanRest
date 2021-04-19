package com.kenzan.rest.data;

import com.kenzan.rest.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByIdAndStatus(String id, String status);

    List<Employee> findAllByStatus(String status);
}
