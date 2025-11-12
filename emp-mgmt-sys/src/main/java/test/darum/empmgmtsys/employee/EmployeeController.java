package test.darum.empmgmtsys.employee;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
  public ResponseEntity<GetEmployeeDto> createEmployee(@RequestHeader("X-User-Role") String role,
                                                       @Valid @RequestBody CreateEmployeeDto dto) {
    checkAdmin(role);
    GetEmployeeDto createdEmployee = employeeService.create(dto);
    return new ResponseEntity<>(createdEmployee, HttpStatus.CREATED);
  }

  @GetMapping
  @Operation(summary = "Get all Employees")
  public ResponseEntity<List<GetEmployeeDto>> getEmployees(
      @RequestHeader("X-User-Role") String role) {
    checkAdmin(role);
    return ResponseEntity.ok(employeeService.getEmployees());
  }
  
  @GetMapping("/{id}")
  @Operation(summary = "Get Employee by Id")
  public ResponseEntity<GetEmployeeDto> getEmployee(
      @PathVariable String id,
      @RequestHeader("X-User-Role") String role,
      @RequestHeader("X-User-Email") String email) {

    GetEmployeeDto employeeDto = employeeService.findById(id);
    
    if (isAdmin(role)) {
      return ResponseEntity.ok(employeeDto);
    }
    
    if (isManager(role) && employeeService.isManagerForDepartment(email, employeeDto)) {
      return ResponseEntity.ok(employeeDto);
    }
    
    if (employeeDto.getEmail().equalsIgnoreCase(email)) {
      return ResponseEntity.ok(employeeDto);
    }
    
    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: Insufficient privileges for this employee");
  }
  
  @PutMapping("/{id}")
  @Operation(summary = "Update Employee")
  public ResponseEntity<GetEmployeeDto> updateEmployee(
      @PathVariable String id,
      @RequestHeader("X-User-Role") String role,
      @RequestBody UpdateEmployeeDto dto) {
    checkAdmin(role);
    GetEmployeeDto updatedEmployee = employeeService.update(id, dto);
    return ResponseEntity.ok(updatedEmployee);
  }
  
  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Employee")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteEmployee(
      @PathVariable String id,
      @RequestHeader("X-User-Role") String role) {
    checkAdmin(role);
    employeeService.delete(id);
  }

  private void checkAdmin(String role) {
    if (!isAdmin(role)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: Only Admin can perform this action");
    }
  }
  
  private boolean isAdmin(String role) {
    return role.equals("ADMIN");
  }
  private boolean isManager(String role) {
    return role.equals("MANAGER");
  }
}