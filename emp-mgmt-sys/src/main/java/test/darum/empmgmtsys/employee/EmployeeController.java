package test.darum.empmgmtsys.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.darum.empmgmtsys.employee.dtos.CreateEmployeeDto;
import test.darum.empmgmtsys.employee.dtos.GetEmployeeDto;
import test.darum.empmgmtsys.employee.dtos.UpdateEmployeeDto;

import java.util.List;

@Tag(name = "Employee", description = "CRUD end-points for managing employees")
@RestController
@AllArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {
  private EmployeeService employeeService;

  @PostMapping
  @Operation(summary = "Create Employee")
  public ResponseEntity<GetEmployeeDto> createEmployee(@Valid @RequestBody CreateEmployeeDto dto) {
    GetEmployeeDto createdEmployee = employeeService.create(dto);
    return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
  }

  @GetMapping
  @Operation(summary = "Get all Employees")
  public ResponseEntity<List<GetEmployeeDto>> getEmployees() {
    return ResponseEntity.ok(employeeService.getEmployees());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Employee by Id")
  public ResponseEntity<GetEmployeeDto> getEmployee(@PathVariable String id) {
    return ResponseEntity.ok(employeeService.findById(id));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update Employee")
  public ResponseEntity<GetEmployeeDto> updateEmployee(@PathVariable String id, @RequestBody UpdateEmployeeDto dto) {
    GetEmployeeDto updatedEmployee = employeeService.update(id, dto);
    return ResponseEntity.ok(updatedEmployee);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Employee")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(@PathVariable String id) {
    employeeService.delete(id);
  }
}