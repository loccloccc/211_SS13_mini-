package org.example.miniproject.service;

import org.example.miniproject.exception.EmployeeNotFoundException;
import org.example.miniproject.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    void setUp() {
        employee = new Employee(null, "Nguyen Van A", "Engineering", 15000000.0);
    }

    @Test
    void getAllEmployees_ReturnList() {
        employeeService.addEmployee(employee);
        assertFalse(employeeService.getAllEmployees().isEmpty());
    }

    @Test
    void getById_Found() {
        Employee saved = employeeService.addEmployee(employee);
        Employee found = employeeService.getEmployeeById(saved.getId());
        assertEquals(saved.getId(), found.getId());
        assertEquals("Nguyen Van A", found.getFullName());
    }

    @Test
    void getById_NotFound_ThrowException() {
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(999L));
    }

    @Test
    void addEmployee_Success() {
        Employee saved = employeeService.addEmployee(employee);
        assertNotNull(saved.getId());
        assertEquals("Nguyen Van A", saved.getFullName());
    }

    @Test
    void deleteEmployee_RemovesCorrectElement() {
        Employee saved = employeeService.addEmployee(employee);
        employeeService.deleteEmployee(saved.getId());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(saved.getId()));
    }
}
