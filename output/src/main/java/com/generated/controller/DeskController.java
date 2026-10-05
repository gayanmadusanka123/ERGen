package com.generated.controller;

import com.generated.entity.Desk;
import com.generated.service.DeskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/desks")
@CrossOrigin(origins = "http://localhost:4200")
public class DeskController{
    private final DeskService service;

    public DeskController(DeskService service){
        this.service = service;
    }

    //create
    @PostMapping
    public ResponseEntity<Desk> createDesk(@RequestBody Desk desk){
        Desk savedDesk = service.save(desk);

        return ResponseEntity.ok(savedDesk);
    }

    //readall
    @GetMapping
    public ResponseEntity<List<Desk>> getAllDesks(){
        return ResponseEntity.ok(service.findAll());
    }

    //readone
    @GetMapping("/{id}")
    public ResponseEntity<Desk> getDeskById(@PathVariable Long id){
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    //update
    @PutMapping("/{id}")
    public ResponseEntity<Desk> updateDesk(@PathVariable Long id, @RequestBody Desk desk) {

        try {
            Desk updatedDesk =
                service.update(id, desk);

            return ResponseEntity.ok(updatedDesk);

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDesk(@PathVariable Long id) {

        try {
            service.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}