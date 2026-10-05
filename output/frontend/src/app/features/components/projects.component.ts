import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgFor } from '@angular/common';
import { ProjectService } from '../../services/project.service';
import { Project } from '../../models/project';


@Component({
    selector: 'app-projects',
    standalone: true,
    imports: [NgFor],
    templateUrl: '../pages/projects.component.html'
})
export class ProjectsComponent implements OnInit {

    projects: Project[] = [];


    constructor(
        private projectService: ProjectService,
    ) {}

    ngOnInit(): void {
        this.projectService.getAll()
            .subscribe(data => {
                this.projects = data;
        });

    }
}