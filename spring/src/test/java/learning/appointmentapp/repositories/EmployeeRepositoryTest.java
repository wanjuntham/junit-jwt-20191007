package learning.appointmentapp.repositories;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import learning.appointmentapp.entities.Employee;


public class EmployeeRepositoryTest extends BaseRepositoryTest {

    @Test
    public void testFindAllEmpty() {
        // Given: There is no employee data in the table

        // When: We query the repository
        List<Employee> results = empRepo.findAll();

        // Then: We get an empty array
        assertEquals(0, results.size());
    }

    // scenario 2: if there data in the table, we get an array of employee data
    @Test
    public void testFindAllSuccess() {
        // Given: There is employee data in the table
        // if we insert data for 2 employees
        seedEmployee("Ming Xiang", "mingxiangchan@gmail.com");
        seedEmployee("Wan Jun", "wanjuntham@gmail.com");

        // When: We query the repository
        List<Employee> results = empRepo.findAll();

        // Then: We get an array of employee data
        // we expect an array with size 2
        assertEquals(2, results.size());

    }

    @Test
    public void testFindIdSuccess() {
        Employee emp1 = seedEmployee("test1", "test1@test.com");
        seedEmployee("test2", "test2@test.com");

        Employee results = empRepo.findById(emp1.getId()).orElse(null);
        
        assertEquals(emp1.getId(), results.getId());
    }

    @Test
    public void testFindIdFail() {
        Employee result = empRepo.findById(5L).orElse(null);

        assertEquals(null, result);
    }

    @Test
    public void testFindEmailSuccess() {
        Employee emp1 = seedEmployee("Ming Xiang", "mingxiangchan@gmail.com");
        seedEmployee("Wan Jun", "wanjuntham@gmail.com");

        Employee results = empRepo.findByEmail(emp1.getEmail());

        assertEquals(emp1.getId(), results.getId());
    }

    @Test
    public void testFindNameSuccess() {
        Employee emp1 = seedEmployee("Wan Jun", "wanjuntham@gmail.com");

        List<Employee> results = empRepo.findByName(emp1.getName());
        
        assertEquals(emp1.getName(), results.get(emp1.getId().intValue()).getName());
    }

    @Test

    public void findAllWithSorting() {
        Employee emp1 = seedEmployee("a", "a@gmail.com");
        Employee emp2 = seedEmployee("b", "b@gmail.com");
        Employee emp3 = seedEmployee("c", "c@gmail.com");

        List<Employee> results = empRepo.findAllByOrderByName();

        assertEquals(emp1.getName(),results.get(0).getName());
        assertEquals(emp2.getName(),results.get(1).getName());
        assertEquals(emp3.getName(),results.get(2).getName());
    }


    
}