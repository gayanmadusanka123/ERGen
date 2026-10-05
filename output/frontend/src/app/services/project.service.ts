import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Project } from '../models/project';

@Injectable({
    providedIn: 'root'
})
export class ProjectService{
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8081/api/projects';

    getAll(): Observable<Project[]>{
        return this.http.get<Project[]>(this.apiUrl);
    }

    getById(id: number): Observable<Project>{
        return this.http.get<Project>(
            `${this.apiUrl}/${id}`
        );
    }

    create(entity: Project): Observable<Project>{
        return this.http.post<Project>(
            this.apiUrl,
            entity
        );
    }

    update(id: number, entity: Project): Observable<Project>{
        return this.http.put<Project>(
            `${this.apiUrl}/${id}`,
            entity
        );
    }

    delete(id: number): Observable<void>{
        return this.http.delete<void>(
            `${this.apiUrl}/${id}`
        );
    }
}
