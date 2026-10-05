package com.generated.controller;

import com.generated.entity.Project;
import com.generated.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController{
    private final ProjectService service;

    public ProjectController(ProjectService service){
        this.service = service;
    }

    //create
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project){
        Project savedProject = service.save(project);

        return ResponseEntity.ok(savedProject);
    }

    //readall
    @GetMapping
    public ResponseEntity<List<Project>> getAllProjects(){
        return ResponseEntity.ok(service.findAll());
    }

    //readone
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id){
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {

        try {
            Project updatedProject =
                service.update(id, project);

            return ResponseEntity.ok(updatedProject);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {

        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}