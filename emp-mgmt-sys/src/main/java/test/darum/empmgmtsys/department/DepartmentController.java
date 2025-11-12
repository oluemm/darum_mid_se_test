package test.darum.empmgmtsys.department;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import test.darum.empmgmtsys.department.dtos.CreateDepartmentDto;
import test.darum.empmgmtsys.department.dtos.GetDepartmentDto;
import test.darum.empmgmtsys.department.dtos.UpdateDepartmentDto;

import java.util.List;

@Tag(name = "Department", description = "CRUD end-points for managing departments (Admin only)")
@RestController
@AllArgsConstructor
@RequestMapping("/departments")
public class DepartmentController {
  private DepartmentService departmentService;
  
  private void checkAdmin(String role) {
    if (!isAdmin(role)) {
      throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied: Only Admin can perform this action");
    }
  }

  private boolean isAdmin(String role) {
    return "ADMIN".equals(role);
  }
  

  @PostMapping
  @Operation(summary = "Create Department (Admin Only)")
  public ResponseEntity<GetDepartmentDto> createDepartment(
      @RequestHeader("X-User-Role") String role,
      @Valid @RequestBody CreateDepartmentDto dto) {
    checkAdmin(role);
    GetDepartmentDto createdDepartment = departmentService.create(dto);
    return new ResponseEntity<>(createdDepartment, HttpStatus.CREATED);
  }

  @GetMapping
  @Operation(summary = "Get all Departments (Admin Only)")
  public ResponseEntity<List<GetDepartmentDto>> getDepartments(
      @RequestHeader("X-User-Role") String role) {
    checkAdmin(role);
    return ResponseEntity.ok(departmentService.getDepartments());
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get Department by Id (Admin Only)")
  public ResponseEntity<GetDepartmentDto> getDepartment(
      @PathVariable String id,
      @RequestHeader("X-User-Role") String role) {
    checkAdmin(role);
    return ResponseEntity.ok(departmentService.findById(id));
  }

  @PutMapping("/{id}")
  @Operation(summary = "Update Department (Admin Only)")
  public ResponseEntity<GetDepartmentDto> updateDepartment(
      @PathVariable String id,
      @RequestHeader("X-User-Role") String role,
      @RequestBody UpdateDepartmentDto dto) {
    checkAdmin(role);
    GetDepartmentDto updatedDepartment = departmentService.update(id, dto);
    return ResponseEntity.ok(updatedDepartment);
  }

  @DeleteMapping("/{id}")
  @Operation(summary = "Delete Department (Admin Only)")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteDepartment(
      @PathVariable String id,
      @RequestHeader("X-User-Role") String role) {
    checkAdmin(role);
    departmentService.delete(id);
  }
}
