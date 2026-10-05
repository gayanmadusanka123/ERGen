import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { DepartmentService } from '../../services/department.service';
import { Department } from '../../models/department';

import { DepartmentService } from '../../services/department.service';
import { Department } from '../../models/department';
import { DepartmentService } from '../../services/department.service';
import { Department } from '../../models/department';

@Component({
    selector: 'app-departments',
    standalone: true,
    imports: [NgFor],
    templateUrl: '../pages/departments.component.html'
})
export class DepartmentsComponent implements OnInit {

    departments: Department[] = [];

    departments: Department[] = [];
    departments: Department[] = [];

    constructor(
        private departmentService: DepartmentService,
        private departmentService: DepartmentService
        private departmentService: DepartmentService
    ) {}

    ngOnInit(): void {
        this.departmentService.getAll()
            .subscribe(data => {
                this.departments = data;
        });

        this.departmentService.getAll()
            .subscribe(data => {
                this.departments = data;
        });
        this.departmentService.getAll()
            .subscribe(data => {
                this.departments = data;
        });
    }
}