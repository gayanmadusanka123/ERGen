package com.generated.service;

import com.generated.entity.${entity.name};
import com.generated.repository.${entity.name}Repository;

import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ${entity.name}Service{

    private final ${entity.name}Repository repository;

    //constructor
    public ${entity.name}Service(${entity.name}Repository repository){
        this.repository = repository;
    }

    //create
    public ${entity.name} save(${entity.name} entity){
        return repository.save(entity);
    }

    //findall
    public List<${entity.name}> findAll(){
        return repository.findAll();
    }

    //findone
    public Optional<${entity.name}> findById(${entity.primaryKey.type} id){
        return repository.findById(id);
    }

    //update
    public ${entity.name} update(${entity.primaryKey.type} id, ${entity.name} entityDetails){
        ${entity.name} existingEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("${entity.name} not found with id: " + id));

        <#list entity.attributes as attribute>
            <#if attribute.name != "id">
        existingEntity.set${attribute.name?cap_first}(entityDetails.get${attribute.name?cap_first}());
            </#if>
        </#list>
        return repository.save(existingEntity);
    }

    //delete
    public void delete(${entity.primaryKey.type} id){
        if(!repository.existsById(id)){
            throw new RuntimeException("${entity.name} Not Found with id: " + id);
        }
        repository.deleteById(id);
    }
}