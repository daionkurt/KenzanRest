package com.kenzan.rest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.gson.Gson;

import com.kenzan.rest.service.EmployeeService;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@DynamicUpdate
public class Employee {

    /**
     * ID - Unique identifier for an employee
     * FirstName - Employees first name
     * MiddleInitial - Employees middle initial
     * LastName - Employed last name
     * DateOfBirth - Employee birthday and year
     * DateOfEmployment - Employee start date
     * Status - ACTIVE or INACTIVE */

    @Id
    @Column
    private String id;

    @Column
    @NotNull
    @NotEmpty
    @Size(max = 128)
    private String firstName;

    @Column
    @Size(max = 1)
    private String middleInitial;

    @Column
    @NotNull
    @NotEmpty
    @Size(max = 128)
    private String lastName;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @Column
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfEmployment;

    @Column
    private String status = EmployeeService.ACTIVE_STATUS;

    public Employee() {
    }

    public Employee(@NotNull @NotEmpty @Size(max = 128) String firstName, String middleInitial,
                    @NotNull @NotEmpty @Size(max = 128) String lastName, Date dateOfBirth, Date dateOfEmployment,
                    @Size(min = 3, max = 3) String status) {
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.dateOfEmployment = dateOfEmployment;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Date getDateOfEmployment() {
        return dateOfEmployment;
    }

    public void setDateOfEmployment(Date dateOfEmployment) {
        this.dateOfEmployment = dateOfEmployment;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString(){
        return new Gson().toJson(this);
    }
}
