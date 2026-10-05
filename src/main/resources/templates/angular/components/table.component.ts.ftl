import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { ${entity.name}Service } from '../../services/${entity.name?lower_case}.service';
import { ${entity.name} } from '../../models/${entity.name?lower_case}';

<#list entity.relationships as relationship>
    <#if relationship.type == "ManyToOne">
import { ${relationship.to}Service } from '../../services/${relationship.to?lower_case}.service';
import { ${relationship.to} } from '../../models/${relationship.to?lower_case}';
    </#if>
</#list>

@Component({
    selector: 'app-${entity.name?lower_case}s',
    standalone: true,
    imports: [NgFor],
    templateUrl: '../pages/${entity.name?lower_case}s.component.html'
})
export class ${entity.name}sComponent implements OnInit {

    ${entity.name?lower_case}s: ${entity.name}[] = [];

<#list entity.relationships as relationship>
<#if relationship.type == "ManyToOne">
    ${relationship.to?lower_case}s: ${relationship.to}[] = [];
</#if>
</#list>

    constructor(
        private ${entity.name?lower_case}Service: ${entity.name}Service,
        <#list entity.relationships as relationship>
            <#if relationship.type == "ManyToOne">
        private ${relationship.to?lower_case}Service: ${relationship.to}Service
            </#if>
        </#list>
    ) {}

    ngOnInit(): void {
        this.${entity.name?lower_case}Service.getAll()
            .subscribe(data => {
                this.${entity.name?lower_case}s = data;
        });

        <#list entity.relationships as relationship>
            <#if relationship.type == "ManyToOne">
        this.${relationship.to?lower_case}Service.getAll()
            .subscribe(data => {
                this.${relationship.to?lower_case}s = data;
        });
            </#if>
        </#list>
    }
}