package test.darum.empmgmtsys.department;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.darum.empmgmtsys.common.exceptions.NotFoundException;
import test.darum.empmgmtsys.common.exceptions.ResourceConflictException;
import test.darum.empmgmtsys.department.dtos.CreateDepartmentDto;
import test.darum.empmgmtsys.department.dtos.GetDepartmentDto;
import test.darum.empmgmtsys.department.dtos.UpdateDepartmentDto;
import test.darum.empmgmtsys.employee.EmployeeRepository;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class DepartmentService {
  private DepartmentRepository departmentRepository;
  private EmployeeRepository employeeRepository;
  private DepartmentMapper mapper;

  public GetDepartmentDto create(CreateDepartmentDto dto) {
    var existingDepartment = departmentRepository.findByName(dto.getName());
    if (existingDepartment.isPresent()) {
      throw new ResourceConflictException("Department with name " + dto.getName() + " already exists");
    }

    Department department = mapper.toEntity(dto);
    Department savedDepartment = departmentRepository.save(department);
    return mapper.toDto(savedDepartment);
  }

  public GetDepartmentDto update(String id, UpdateDepartmentDto dto) {
    UUID uuid = parseId(id, "Department");
    Department existingDepartment = departmentRepository.findById(uuid)
                                                        .orElseThrow(() -> new NotFoundException("Department not found"));

    if (dto.getName() != null && !existingDepartment.getName().equalsIgnoreCase(dto.getName())) {
      var conflict = departmentRepository.findByName(dto.getName());
      if (conflict.isPresent()) {
        throw new ResourceConflictException("Department with name " + dto.getName() + " already exists");
      }
    }

    mapper.update(dto, existingDepartment);

    Department updatedDepartment = departmentRepository.save(existingDepartment);
    return mapper.toDto(updatedDepartment);
  }

  public void delete(String id) {
    UUID uuid = parseId(id, "Department");
    Department department = departmentRepository.findById(uuid)
                                                .orElseThrow(() -> new NotFoundException("Department not found"));

    // Check if the department is currently assigned to any employee
    if (employeeRepository.existsByDepartment(department)) {
      throw new ResourceConflictException("Cannot delete department as it is currently assigned to one or more employees");
    }

    departmentRepository.delete(department);
  }

  public GetDepartmentDto findById(String id) {
    UUID uuid = parseId(id, "Department");
    Department department = departmentRepository.findById(uuid)
                                                .orElseThrow(() -> new NotFoundException("Department not found"));
    return mapper.toDto(department);
  }

  public List<GetDepartmentDto> getDepartments() {
    var departments = departmentRepository.findAll();
    return departments.stream().map(mapper::toDto).toList();
  }

  private UUID parseId(String id, String type) {
    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new NotFoundException(type + " with Id: " + id + " not found");
    }
  }
}