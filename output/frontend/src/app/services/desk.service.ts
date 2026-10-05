import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Desk } from '../models/desk';

@Injectable({
    providedIn: 'root'
})
export class DeskService{
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8081/api/desks';

    getAll(): Observable<Desk[]>{
        return this.http.get<Desk[]>(this.apiUrl);
    }

    getById(id: number): Observable<Desk>{
        return this.http.get<Desk>(
            `${this.apiUrl}/${id}`
        );
    }

    create(entity: Desk): Observable<Desk>{
        return this.http.post<Desk>(
            this.apiUrl,
            entity
        );
    }

    update(id: number, entity: Desk): Observable<Desk>{
        return this.http.put<Desk>(
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
