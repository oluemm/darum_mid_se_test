package test.darum.empmgmtsys.employee.dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeDto {
  private String firstName;
  private String lastName;
  private String email;
  private String status;
}