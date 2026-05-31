package org.example.miniproject.service;

import lombok.extern.slf4j.Slf4j;
import org.example.miniproject.exception.EmployeeNotFoundException;
import org.example.miniproject.model.Employee;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class EmployeeService {
    private final List<Employee> employees = new ArrayList<>(List.of(
            new Employee(1L, "Nguyễn Văn A", "Engineering", 95000.0),
            new Employee(2L, "Nguyễn Văn B", "Marketing",   72000.0),
            new Employee(3L, "Nguyễn Văn C", "HR",          68000.0),
            new Employee(4L, "Nguyễn Văn D", "Finance",     85000.0),
            new Employee(5L, "Nguyễn Văn E", "Engineering", 98000.0)
    ));
    private final AtomicLong idCounter = new AtomicLong(6);

    public List<Employee> getAllEmployees() {
        log.info("GET /api/employees called");
        return employees;
    }

    public Employee getEmployeeById(Long id) {
        log.info("GET /api/employees/{} called", id);
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> {
                    log.warn("Employee not found with id: {}", id);
                    return new EmployeeNotFoundException(id);
                });
    }

    public Employee addEmployee(Employee employee) {
        log.info("POST /api/employees called");
        employee.setId(idCounter.getAndIncrement());
        employees.add(employee);
        return employee;
    }

    public Employee updateEmployee(Long id, Employee updated) {
        log.info("PUT /api/employees/{} called", id);
        Employee existing = getEmployeeById(id);
        existing.setFullName(updated.getFullName());
        existing.setDepartment(updated.getDepartment());
        existing.setSalary(updated.getSalary());
        return existing;
    }

    public void deleteEmployee(Long id) {
        log.info("DELETE /api/employees/{} called", id);
        Employee existing = getEmployeeById(id);
        employees.remove(existing);
    }
}
