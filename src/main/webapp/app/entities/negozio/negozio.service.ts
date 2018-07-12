import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { INegozio } from 'app/shared/model/negozio.model';

type EntityResponseType = HttpResponse<INegozio>;
type EntityArrayResponseType = HttpResponse<INegozio[]>;

@Injectable({ providedIn: 'root' })
export class NegozioService {
    private resourceUrl = SERVER_API_URL + 'api/negozios';

    constructor(private http: HttpClient) {}

    create(negozio: INegozio): Observable<EntityResponseType> {
        return this.http.post<INegozio>(this.resourceUrl, negozio, { observe: 'response' });
    }

    update(negozio: INegozio): Observable<EntityResponseType> {
        return this.http.put<INegozio>(this.resourceUrl, negozio, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<INegozio>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<INegozio[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
