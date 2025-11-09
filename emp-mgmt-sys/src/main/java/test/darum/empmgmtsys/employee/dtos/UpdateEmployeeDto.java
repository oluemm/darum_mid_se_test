package test.darum.empmgmtsys.employee.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdateEmployeeDto {
  private String firstName;
  private String lastName;
  private String email;
  private String status;
}