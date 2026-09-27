package com.generated.controller;

import com.generated.entity.Department;
import com.generated.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@CrossOrigin(origins = "http://localhost:4200")
public class DepartmentController{
    private final DepartmentService service;

    public DepartmentController(DepartmentService service){
        this.service = service;
    }

    //create
    @PostMapping
    public ResponseEntity<Department> createDepartment(@RequestBody Department department){
        Department savedDepartment = service.save(department);

        return ResponseEntity.ok(savedDepartment);
    }

    //readall
    @GetMapping
    public ResponseEntity<List<Department>> getAllDepartments(){
        return ResponseEntity.ok(service.findAll());
    }

    //readone
    @GetMapping("/{id}")
    public ResponseEntity<Department> getDepartmentById(@PathVariable Long id){
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<Department> updateDepartment(@PathVariable Long id, @RequestBody Department department) {

        try {
            Department updatedDepartment =
                service.update(id, department);

            return ResponseEntity.ok(updatedDepartment);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDepartment(@PathVariable Long id) {

        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}