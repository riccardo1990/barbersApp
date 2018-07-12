import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IServizio } from 'app/shared/model/servizio.model';

type EntityResponseType = HttpResponse<IServizio>;
type EntityArrayResponseType = HttpResponse<IServizio[]>;

@Injectable({ providedIn: 'root' })
export class ServizioService {
    private resourceUrl = SERVER_API_URL + 'api/servizios';

    constructor(private http: HttpClient) {}

    create(servizio: IServizio): Observable<EntityResponseType> {
        return this.http.post<IServizio>(this.resourceUrl, servizio, { observe: 'response' });
    }

    update(servizio: IServizio): Observable<EntityResponseType> {
        return this.http.put<IServizio>(this.resourceUrl, servizio, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IServizio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IServizio[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
