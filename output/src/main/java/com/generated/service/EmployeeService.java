package com.generated.service;

import com.generated.entity.Employee;
import com.generated.repository.EmployeeRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService{

    private final EmployeeRepository repository;

    //constructor
    public EmployeeService(EmployeeRepository repository){
        this.repository = repository;
    }

    //create
    public Employee save(Employee entity){
        return repository.save(entity);
    }

    //findall
    public List<Employee> findAll(){
        return repository.findAll();
    }

    //findone
    public Optional<Employee> findById(Long id){
        return repository.findById(id);
    }

    //update
    public Employee update(Long id, Employee entityDetails){
        Employee existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));

        existingEntity.setName(entityDetails.getName());
        existingEntity.setEmail(entityDetails.getEmail());
        return repository.save(existingEntity);
    }

    //delete
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Employee Not Found with id: " + id);
        }
        repository.deleteById(id);
    }
}