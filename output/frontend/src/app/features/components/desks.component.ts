import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { DeskService } from '../../services/desk.service';
import { Desk } from '../../models/desk';

import { DepartmentService } from '../../services/department.service';
import { Department } from '../../models/department';

@Component({
    selector: 'app-desks',
    standalone: true,
    imports: [NgFor],
    templateUrl: '../pages/desks.component.html'
})
export class DesksComponent implements OnInit {

    desks: Desk[] = [];

    departments: Department[] = [];

    constructor(
        private deskService: DeskService,
        private departmentService: DepartmentService
    ) {}

    ngOnInit(): void {
        this.deskService.getAll()
            .subscribe(data => {
                this.desks = data;
        });

        this.departmentService.getAll()
            .subscribe(data => {
                this.departments = data;
        });
    }
}