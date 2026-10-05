import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { EmployeeService } from '../../services/employee.service';
import { Employee } from '../../models/employee';

import { DepartmentService } from '../../services/department.service';
import { Department } from '../../models/department';

@Component({
    selector: 'app-employees',
    standalone: true,
    imports: [NgFor],
    templateUrl: '../pages/employees.component.html'
})
export class EmployeesComponent implements OnInit {

    employees: Employee[] = [];

    departments: Department[] = [];

    constructor(
        private employeeService: EmployeeService,
        private departmentService: DepartmentService
    ) {}

    ngOnInit(): void {
        this.employeeService.getAll()
            .subscribe(data => {
                this.employees = data;
        });

        this.departmentService.getAll()
            .subscribe(data => {
                this.departments = data;
        });
    }
}