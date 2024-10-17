package dmit2015.repository;

import dmit2015.entity.Department;
import dmit2015.entity.Employee;
import jakarta.data.repository.Find;
import jakarta.data.repository.OrderBy;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;
import org.hibernate.annotations.processing.Pattern;

import java.util.List;

@Repository
public interface EmployeeRepository {

    @Find
    @OrderBy("departmentName")
    List<Department> departmentsBy(@Pattern String departmentName);

    @Query("from Employee e join fetch e.department join fetch e.job join fetch e.manager where e.department.id = ?1")
    List<Employee> employeesByDepartmentId(Short departmentId);

    @Find
    Department departmentByDepartmentId(Short id);

}
