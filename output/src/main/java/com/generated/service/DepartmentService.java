package com.generated.service;

import com.generated.entity.Department;
import com.generated.repository.DepartmentRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService{

    private final DepartmentRepository repository;

    //constructor
    public DepartmentService(DepartmentRepository repository){
        this.repository = repository;
    }

    //create
    public Department save(Department entity){
        return repository.save(entity);
    }

    //findall
    public List<Department> findAll(){
        return repository.findAll();
    }

    //findone
    public Optional<Department> findById(Long id){
        return repository.findById(id);
    }

    //update
    public Department update(Long id, Department entityDetails){
        Department existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found with id: " + id));

        existingEntity.setName(entityDetails.getName());
        existingEntity.setLocation(entityDetails.getLocation());
        return repository.save(existingEntity);
    }

    //delete
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Department Not Found with id: " + id);
        }
        repository.deleteById(id);
    }
}