package org.example.miniproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.miniproject.exception.EmployeeNotFoundException;
import org.example.miniproject.model.Employee;
import org.example.miniproject.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllEmployees_Return200() throws Exception {
        when(employeeService.getAllEmployees())
                .thenReturn(List.of(new Employee(1L, "Nguyen Van A", "Engineering", 15000000.0)));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Nguyen Van A"));
    }

    @Test
    void getEmployeeById_Found_Return200() throws Exception {
        when(employeeService.getEmployeeById(1L))
                .thenReturn(new Employee(1L, "Nguyen Van A", "Engineering", 15000000.0));

        mockMvc.perform(get("/api/employees/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Nguyen Van A"));
    }

    @Test
    void getEmployeeById_NotFound_Return404() throws Exception {
        when(employeeService.getEmployeeById(999L))
                .thenThrow(new EmployeeNotFoundException(999L));

        mockMvc.perform(get("/api/employees/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void addEmployee_Return201() throws Exception {
        Employee employee = new Employee(null, "Nguyen Van A", "Engineering", 15000000.0);
        Employee saved = new Employee(1L, "Nguyen Van A", "Engineering", 15000000.0);

        when(employeeService.addEmployee(any(Employee.class))).thenReturn(saved);

        mockMvc.perform(post("/api/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
    }
}
