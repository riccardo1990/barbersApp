import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Servizio } from 'app/shared/model/servizio.model';
import { ServizioService } from './servizio.service';
import { ServizioComponent } from './servizio.component';
import { ServizioDetailComponent } from './servizio-detail.component';
import { ServizioUpdateComponent } from './servizio-update.component';
import { ServizioDeletePopupComponent } from './servizio-delete-dialog.component';
import { IServizio } from 'app/shared/model/servizio.model';

@Injectable({ providedIn: 'root' })
export class ServizioResolve implements Resolve<IServizio> {
    constructor(private service: ServizioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((servizio: HttpResponse<Servizio>) => servizio.body));
        }
        return of(new Servizio());
    }
}

export const servizioRoute: Routes = [
    {
        path: 'servizio',
        component: ServizioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.servizio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'servizio/:id/view',
        component: ServizioDetailComponent,
        resolve: {
            servizio: ServizioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.servizio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'servizio/new',
        component: ServizioUpdateComponent,
        resolve: {
            servizio: ServizioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.servizio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'servizio/:id/edit',
        component: ServizioUpdateComponent,
        resolve: {
            servizio: ServizioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.servizio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const servizioPopupRoute: Routes = [
    {
        path: 'servizio/:id/delete',
        component: ServizioDeletePopupComponent,
        resolve: {
            servizio: ServizioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.servizio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
