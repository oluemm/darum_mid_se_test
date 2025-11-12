package test.darum.empmgmtsys.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.darum.empmgmtsys.department.Department;

import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
  Object findByEmail(String email);

  boolean existsByDepartment(Department department);
}
