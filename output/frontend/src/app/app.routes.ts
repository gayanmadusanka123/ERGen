import { Routes } from '@angular/router';

import { EmployeesComponent } from './features/components/employees.component';
import { DepartmentsComponent } from './features/components/departments.component';
import { ProjectsComponent } from './features/components/projects.component';
import { DesksComponent } from './features/components/desks.component';

export const routes: Routes = [

    {
        path: 'employees',
        component: EmployeesComponent
    },
    {
        path: 'departments',
        component: DepartmentsComponent
    },
    {
        path: 'projects',
        component: ProjectsComponent
    },
    {
        path: 'desks',
        component: DesksComponent
    }

];