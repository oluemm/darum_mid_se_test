package test.darum.empmgmtsys.employee;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import test.darum.empmgmtsys.common.exceptions.NotFoundException;
import test.darum.empmgmtsys.common.exceptions.ResourceConflictException;
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
  private EmployeeMapper mapper;

  public GetEmployeeDto create(CreateEmployeeDto dto) {
    // validate
    var existingEmployee = employeeRepository.findByEmail(dto.getEmail());
    if (existingEmployee != null) {
      throw new ResourceConflictException("Employee already exists");
    }
    Employee employee = mapper.toEntity(dto);
    employee.setCreatedAt(LocalDateTime.now());

    Employee savedEmployee = employeeRepository.save(employee);
    return mapper.toDto(savedEmployee);
  }

  public GetEmployeeDto update(String id, UpdateEmployeeDto dto) {
    UUID uuid = parseId(id);
    Employee existingEmployee = employeeRepository.findById(uuid)
                                                  .orElseThrow(() -> new NotFoundException("Employee not found"));

    mapper.update(dto, existingEmployee);
    employeeRepository.save(existingEmployee);
    return mapper.toDto(existingEmployee);
  }

  public void delete(String id) {
    UUID uuid = parseId(id);
    Employee employee = employeeRepository.findById(uuid)
                                          .orElseThrow(() -> new NotFoundException("Employee not found"));
    employeeRepository.delete(employee);
  }

  public GetEmployeeDto findById(String id) {
    UUID uuid = parseId(id);
    Employee employee = employeeRepository.findById(uuid)
                                          .orElseThrow(() -> new NotFoundException("Employee not found"));
    return mapper.toDto(employee);
  }

  public List<GetEmployeeDto> getEmployees() {
    var employees = employeeRepository.findAll();
    return employees.stream().map(mapper::toDto).toList();
  }

  private UUID parseId(String id) {
    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException e) {
      throw new NotFoundException("Employee not found");
    }
  }
}
