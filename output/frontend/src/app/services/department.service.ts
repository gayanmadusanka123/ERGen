import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Department } from '../models/department';

@Injectable({
    providedIn: 'root'
})
export class DepartmentService{
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8081/api/departments';

    getAll(): Observable<Department[]>{
        return this.http.get<Department[]>(this.apiUrl);
    }

    getById(id: number): Observable<Department>{
        return this.http.get<Department>(
            `${this.apiUrl}/${id}`
        );
    }

    create(entity: Department): Observable<Department>{
        return this.http.post<Department>(
            this.apiUrl,
            entity
        );
    }

    update(id: number, entity: Department): Observable<Department>{
        return this.http.put<Department>(
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
