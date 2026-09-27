import { Component, OnInit } from '@angular/core';
import { NgFor } from '@angular/common';
import { ${entity.name}Service } from '../../services/${entity.name?lower_case}.service';
import { ${entity.name} } from '../../models/${entity.name?lower_case}';

@Component({
    selector: 'app-${entity.name?lower_case}s',
    standalone: true,
    imports: [NgFor],
    templateUrl: '../pages/${entity.name?lower_case}s.component.html'
})
export class ${entity.name}sComponent implements OnInit {

    ${entity.name?lower_case}s: ${entity.name}[] = [];

    constructor(
        private ${entity.name?lower_case}Service: ${entity.name}Service
    ) {}

    ngOnInit(): void {
        this.${entity.name?lower_case}Service.getAll()
            .subscribe(data => {
                this.${entity.name?lower_case}s = data;
        });
    }
}