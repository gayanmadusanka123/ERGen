package com.generated.service;

import com.generated.entity.Project;
import com.generated.repository.ProjectRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService{

    private final ProjectRepository repository;

    //constructor
    public ProjectService(ProjectRepository repository){
        this.repository = repository;
    }

    //create
    public Project save(Project entity){
        return repository.save(entity);
    }

    //findall
    public List<Project> findAll(){
        return repository.findAll();
    }

    //findone
    public Optional<Project> findById(Long id){
        return repository.findById(id);
    }

    //update
    public Project update(Long id, Project entityDetails){
        Project existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        existingEntity.setName(entityDetails.getName());
        existingEntity.setStartDate(entityDetails.getStartDate());
        existingEntity.setEndDate(entityDetails.getEndDate());
        return repository.save(existingEntity);
    }

    //delete
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Project Not Found with id: " + id);
        }
        repository.deleteById(id);
    }
}