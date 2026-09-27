import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ${entity.name} } from '../models/${entity.name?lower_case}';

@Injectable({
    providedIn: 'root'
})
export class ${entity.name}Service{
    private http = inject(HttpClient);
    private apiUrl = 'http://localhost:8081/api/${entity.name?lower_case}s';

    getAll(): Observable<${entity.name}[]>{
        return this.http.get<${entity.name}[]>(this.apiUrl);
    }

    getById(id: number): Observable<${entity.name}>{
        return this.http.get<${entity.name}>(
            <#noparse>`${this.apiUrl}/${id}`</#noparse>
        );
    }

    create(entity: ${entity.name}): Observable<${entity.name}>{
        return this.http.post<${entity.name}>(
            this.apiUrl,
            entity
        );
    }

    update(id: number, entity: ${entity.name}): Observable<${entity.name}>{
        return this.http.put<${entity.name}>(
            <#noparse>`${this.apiUrl}/${id}`</#noparse>,
            entity
        );
    }

<#noparse>
    delete(id: number): Observable<void>{
        return this.http.delete<void>(
            `${this.apiUrl}/${id}`
        );
    }
}
</#noparse>