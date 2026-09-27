package com.generated.service;

import com.generated.entity.Desk;
import com.generated.repository.DeskRepository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DeskService{

    private final DeskRepository repository;

    //constructor
    public DeskService(DeskRepository repository){
        this.repository = repository;
    }

    //create
    public Desk save(Desk entity){
        return repository.save(entity);
    }

    //findall
    public List<Desk> findAll(){
        return repository.findAll();
    }

    //findone
    public Optional<Desk> findById(Long id){
        return repository.findById(id);
    }

    //update
    public Desk update(Long id, Desk entityDetails){
        Desk existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Desk not found with id: " + id));

        existingEntity.setName(entityDetails.getName());
        return repository.save(existingEntity);
    }

    //delete
    public void delete(Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Desk Not Found with id: " + id);
        }
        repository.deleteById(id);
    }
}