import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Negozio } from 'app/shared/model/negozio.model';
import { NegozioService } from './negozio.service';
import { NegozioComponent } from './negozio.component';
import { NegozioDetailComponent } from './negozio-detail.component';
import { NegozioUpdateComponent } from './negozio-update.component';
import { NegozioDeletePopupComponent } from './negozio-delete-dialog.component';
import { INegozio } from 'app/shared/model/negozio.model';

@Injectable({ providedIn: 'root' })
export class NegozioResolve implements Resolve<INegozio> {
    constructor(private service: NegozioService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((negozio: HttpResponse<Negozio>) => negozio.body));
        }
        return of(new Negozio());
    }
}

export const negozioRoute: Routes = [
    {
        path: 'negozio',
        component: NegozioComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.negozio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'negozio/:id/view',
        component: NegozioDetailComponent,
        resolve: {
            negozio: NegozioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.negozio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'negozio/new',
        component: NegozioUpdateComponent,
        resolve: {
            negozio: NegozioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.negozio.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'negozio/:id/edit',
        component: NegozioUpdateComponent,
        resolve: {
            negozio: NegozioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.negozio.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const negozioPopupRoute: Routes = [
    {
        path: 'negozio/:id/delete',
        component: NegozioDeletePopupComponent,
        resolve: {
            negozio: NegozioResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.negozio.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
