import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';

import { ${entity.name}Service } from './services/${entity.name?lower_case}.service';
import { ${entity.name} } from './models/${entity.name?lower_case}.model';

<#list entity.relationships as relationship>
    <#if relationship.type == "ManyToOne" && relationship.from == entity.name>
import { ${relationship.to}Service } from '../${relationship.to?lower_case}s/services/${relationship.to?lower_case}.service';
import { ${relationship.to} } from '../${relationship.to?lower_case}s/models/${relationship.to?lower_case}.model';
    </#if>
</#list>

@Component({
    selector: 'app-${entity.name?lower_case}s',
    standalone: true,
    imports: [NgFor, FormsModule],
    templateUrl: './${entity.name?lower_case}s.component.html'
})
export class ${entity.name}sComponent implements OnInit {

    ${entity.name?lower_case}s: ${entity.name}[] = [];

    ${entity.name?lower_case}: ${entity.name} = {} as ${entity.name}

<#list entity.relationships as relationship>
<#if relationship.type == "ManyToOne" && relationship.from == entity.name>
    ${relationship.to?lower_case}s: ${relationship.to}[] = [];
</#if>
</#list>

    constructor(
        private ${entity.name?lower_case}Service: ${entity.name}Service,
        <#list entity.relationships as relationship>
            <#if relationship.type == "ManyToOne" && relationship.from == entity.name>
        private ${relationship.to?lower_case}Service: ${relationship.to}Service
            </#if>
        </#list>
    ) {}

    isEditing: boolean = false;

    ngOnInit(): void {
        this.load${entity.name}s();

<#list entity.relationships as relationship>
    <#if relationship.type == "ManyToOne" && relationship.from == entity.name>
        this.load${relationship.to}s();
    </#if>
</#list>
    }

    load${entity.name}s(): void {
        this.${entity.name?lower_case}Service.getAll()
            .subscribe(data => {
                this.${entity.name?lower_case}s = data;
            });
    }

<#list entity.relationships as relationship>
    <#if relationship.type == "ManyToOne" && relationship.from == entity.name>
    load${relationship.to}s(): void{
        this.${relationship.to?lower_case}Service.getAll()
            .subscribe(data => {
                this.${relationship.to?lower_case}s = data;
            });
    }
    </#if>
</#list>

    save(): void{
        if(this.isEditing){
            this.${entity.name?lower_case}Service
                .update(
                    this.${entity.name?lower_case}.id,
                    this.${entity.name?lower_case}
                )
                .subscribe(() => {
                    this.load${entity.name}s();
                    this.cancel();
                });
        }else{
            this.${entity.name?lower_case}Service
                .create(this.${entity.name?lower_case})
                .subscribe(() => {
                    this.load${entity.name}s();
                    this.cancel();
                });
        }
    }

    edit(item: ${entity.name}): void{
        this.${entity.name?lower_case} = { ...item };
        this.isEditing = true;
    }

    delete(id: number): void{
        this.${entity.name?lower_case}Service
            .delete(id)
            .subscribe(() => {
                this.load${entity.name}s();
            });
    }

    cancel(): void{
        this.${entity.name?lower_case} =
            {} as ${entity.name};
        this.isEditing = false;
    }

}