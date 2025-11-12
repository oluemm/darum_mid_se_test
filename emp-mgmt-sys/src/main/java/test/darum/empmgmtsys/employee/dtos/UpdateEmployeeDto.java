package test.darum.empmgmtsys.employee.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import test.darum.empmgmtsys.department.Department;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeDto {
  private String firstName;
  private String lastName;
  private String email;
  private String status;
  private String departmentId;
}