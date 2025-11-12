package test.darum.empmgmtsys.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.darum.empmgmtsys.common.exceptions.BadRequestException;
import test.darum.empmgmtsys.common.exceptions.NotFoundException;
import test.darum.empmgmtsys.common.exceptions.ResourceConflictException;
import test.darum.empmgmtsys.department.DepartmentRepository;
import test.darum.empmgmtsys.employee.dtos.CreateEmployeeDto;
import test.darum.empmgmtsys.employee.dtos.GetEmployeeDto;
import test.darum.empmgmtsys.employee.dtos.UpdateEmployeeDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class EmployeeService {
  private EmployeeRepository employeeRepository;
  private DepartmentRepository deptRepository;
  private EmployeeMapper mapper;

  public GetEmployeeDto create(CreateEmployeeDto dto) {
    // validate
    var department = deptRepository.findById(parseId(dto.getDepartmentId(), "Department"))
                                   .orElseThrow(() -> new BadRequestException("Department not found"));

    var existingEmployee = employeeRepository.findByEmail(dto.getEmail());
    if (existingEmployee != null) {
      throw new ResourceConflictException("Employee already exists");
    }

    Employee employee = mapper.toEntity(dto);
    employee.setDepartment(department);
    Employee savedEmployee = employeeRepository.save(employee);

    return mapper.toDto(savedEmployee);
  }

  public GetEmployeeDto update(String id, UpdateEmployeeDto dto) {
    UUID uuid = parseId(id, "Employee");
    var department = deptRepository.findById(parseId(dto.getDepartmentId(), "Department"))
                                   .orElseThrow(() -> new BadRequestException("Department not found"));

    Employee existingEmployee = employeeRepository.findById(uuid)
                                                  .orElseThrow(() -> new NotFoundException("Employee not found"));

    mapper.update(dto, existingEmployee);
    existingEmployee.setDepartment(department);

    employeeRepository.save(existingEmployee);
    return mapper.toDto(existingEmployee);
  }

  public void delete(String id) {
    UUID uuid = parseId(id, "Employee");
    Employee employee = employeeRepository.findById(uuid)
                                          .orElseThrow(() -> new NotFoundException("Employee not found"));
    employeeRepository.delete(employee);
  }

  public GetEmployeeDto findById(String id) {
    UUID uuid = parseId(id, "Employee");
    Employee employee = employeeRepository.findById(uuid)
                                          .orElseThrow(() -> new NotFoundException("Employee not found"));
    return mapper.toDto(employee);
  }

  public List<GetEmployeeDto> getEmployees() {
    var employees = employeeRepository.findAll();
    return employees.stream().map(mapper::toDto).toList();
  }

  public boolean isManagerForDepartment(String managerEmail, GetEmployeeDto employee) {
    if (managerEmail == null || employee.getDepartment().getId() == null) {
      return false;
    }

    return deptRepository.findById(employee.getDepartment().getId())
                         .map(department -> managerEmail.equalsIgnoreCase(department.getManagerEmail()))
                         .orElse(false);
  }

  private UUID parseId(String id, String type) {
    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new NotFoundException(type + " with Id: " + id + " not found");
    }
  }
}
