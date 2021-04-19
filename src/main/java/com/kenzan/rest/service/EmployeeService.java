package com.kenzan.rest.service;

import com.kenzan.rest.data.EmployeeRepository;
import com.kenzan.rest.exception.DuplicateEmployeeException;
import com.kenzan.rest.exception.EmployeeNotFoundException;
import com.kenzan.rest.exception.NoEmployeesFoundException;
import com.kenzan.rest.exception.NoGrantException;
import com.kenzan.rest.model.Employee;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${security.token:XXXXXXX}")
    private String securityToken;

    private static final Logger LOGGER = LogManager.getLogger(EmployeeService.class);

    public static final String ACTIVE_STATUS = "ACTIVE";
    public static final String INACTIVE_STATUS = "INACTIVE";

    public Employee retrieveEmployeeById(String id){
        LOGGER.info("Entering retrieveEmployeeById()");
        Optional<Employee> optEmployee = employeeRepository.findByIdAndStatus(id, ACTIVE_STATUS);
        if(optEmployee.isEmpty()){
            throw new EmployeeNotFoundException("Employee not found");
        }
        LOGGER.info("Exiting retrieveEmployeeById()");
        return optEmployee.get();
    }

    public List<Employee> retrieveAllEmployees(){
        LOGGER.info("Entering retrieveAllEmployees()");
        List<Employee> allEmployees = employeeRepository.findAllByStatus(ACTIVE_STATUS);
        if(allEmployees.isEmpty()){
            throw new NoEmployeesFoundException("There are no employees");
        }
        LOGGER.info("Exiting retrieveAllEmployees()");
        return allEmployees;
    }

    public Employee createEmployee(Employee employee){
        LOGGER.info("Entering createEmployee()");
        if(employee.getId()== null || employee.getId().isEmpty()){
            employee.setId(generateId(employee));
        }
        if(employeeRepository.existsById(employee.getId())){
            throw new DuplicateEmployeeException("Employee already exists");
        }
        employee.setStatus(ACTIVE_STATUS);
        Employee savedEmployee = employeeRepository.save(employee);
        LOGGER.info("Exiting createEmployee()");
        return savedEmployee;
    }

    public void updateEmployee(String id, Employee newEmployee){
        LOGGER.info("Entering updateEmployee()");
        Optional.of(retrieveEmployeeById(id)).map(employee -> {
            // No specified fields to update, Updating everything, but maybe some of these shouldn't be updatable
            // like the date of birth, or names
            employee.setFirstName(newEmployee.getFirstName());
            employee.setMiddleInitial(newEmployee.getMiddleInitial());
            employee.setLastName(newEmployee.getLastName());
            employee.setStatus(newEmployee.getStatus());
            employee.setDateOfEmployment(newEmployee.getDateOfEmployment());
            employee.setDateOfBirth(newEmployee.getDateOfBirth());
            return employeeRepository.save(employee);
        });
        LOGGER.info("Exiting updateEmployee()");
    }

    public void deleteEmployee(String id, String authorization){
        if(hasGrant(authorization)){
            LOGGER.info("Entering deleteEmployee()");
            Optional.of(retrieveEmployeeById(id)).map(employee -> {
                employee.setStatus(INACTIVE_STATUS);
                return employeeRepository.save(employee);
            });
            //This in case we really want to delete the employee
            //employeeRepository.deleteById(retrieveEmployeeById(id).getId());
            LOGGER.info("Exiting deleteEmployee()");
        }else{
            throw new NoGrantException("You don't have the needed grants to perform this action");
        }

    }

    private boolean hasGrant(String authorization) {
        /**
         * This mechanism
         * */
        return authorization!=null && authorization.equals(securityToken);
    }

    private String generateId(Employee employee){
        return employee.getMiddleInitial() + employee.getFirstName().substring(0,3) + employee.getLastName().substring(0,3);
    }
}
