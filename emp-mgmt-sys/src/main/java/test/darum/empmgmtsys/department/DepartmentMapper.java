package test.darum.empmgmtsys.department;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import test.darum.empmgmtsys.department.dtos.CreateDepartmentDto;
import test.darum.empmgmtsys.department.dtos.GetDepartmentDto;
import test.darum.empmgmtsys.department.dtos.UpdateDepartmentDto;


@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DepartmentMapper {
  GetDepartmentDto toDto(Department entity);
  Department toEntity(CreateDepartmentDto dto);
  void update(UpdateDepartmentDto dto, @MappingTarget Department entity);
}
