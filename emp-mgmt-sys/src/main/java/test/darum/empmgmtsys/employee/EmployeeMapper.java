package test.darum.empmgmtsys.employee;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import test.darum.empmgmtsys.employee.dtos.CreateEmployeeDto;
import test.darum.empmgmtsys.employee.dtos.GetEmployeeDto;
import test.darum.empmgmtsys.employee.dtos.UpdateEmployeeDto;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmployeeMapper {
  GetEmployeeDto toDto(Employee employee);
  Employee toEntity(CreateEmployeeDto dto);
  void update(UpdateEmployeeDto dto, @MappingTarget Employee entity);
}
