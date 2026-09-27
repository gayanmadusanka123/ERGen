package com.generated.controller;

import com.generated.entity.${entity.name};
import com.generated.service.${entity.name}Service;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/${entity.name?uncap_first}s")
@CrossOrigin(origins = "http://localhost:4200")
public class ${entity.name}Controller{
    private final ${entity.name}Service service;

    public ${entity.name}Controller(${entity.name}Service service){
        this.service = service;
    }

    //create
    @PostMapping
    public ResponseEntity<${entity.name}> create${entity.name}(@RequestBody ${entity.name} ${entity.name?uncap_first}){
        ${entity.name} saved${entity.name} = service.save(${entity.name?uncap_first});

        return ResponseEntity.ok(saved${entity.name});
    }

    //readall
    @GetMapping
    public ResponseEntity<List<${entity.name}>> getAll${entity.name}s(){
        return ResponseEntity.ok(service.findAll());
    }

    //readone
    @GetMapping("/{id}")
    public ResponseEntity<${entity.name}> get${entity.name}ById(@PathVariable ${entity.primaryKey.type} id){
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<${entity.name}> update${entity.name}(@PathVariable ${entity.primaryKey.type} id, @RequestBody ${entity.name} ${entity.name?uncap_first}) {

        try {
            ${entity.name} updated${entity.name} =
                service.update(id, ${entity.name?uncap_first});

            return ResponseEntity.ok(updated${entity.name});

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete${entity.name}(@PathVariable ${entity.primaryKey.type} id) {

        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}