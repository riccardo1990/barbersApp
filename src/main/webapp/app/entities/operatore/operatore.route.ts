import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Operatore } from 'app/shared/model/operatore.model';
import { OperatoreService } from './operatore.service';
import { OperatoreComponent } from './operatore.component';
import { OperatoreDetailComponent } from './operatore-detail.component';
import { OperatoreUpdateComponent } from './operatore-update.component';
import { OperatoreDeletePopupComponent } from './operatore-delete-dialog.component';
import { IOperatore } from 'app/shared/model/operatore.model';

@Injectable({ providedIn: 'root' })
export class OperatoreResolve implements Resolve<IOperatore> {
    constructor(private service: OperatoreService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((operatore: HttpResponse<Operatore>) => operatore.body));
        }
        return of(new Operatore());
    }
}

export const operatoreRoute: Routes = [
    {
        path: 'operatore',
        component: OperatoreComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.operatore.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'operatore/:id/view',
        component: OperatoreDetailComponent,
        resolve: {
            operatore: OperatoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.operatore.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'operatore/new',
        component: OperatoreUpdateComponent,
        resolve: {
            operatore: OperatoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.operatore.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'operatore/:id/edit',
        component: OperatoreUpdateComponent,
        resolve: {
            operatore: OperatoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.operatore.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const operatorePopupRoute: Routes = [
    {
        path: 'operatore/:id/delete',
        component: OperatoreDeletePopupComponent,
        resolve: {
            operatore: OperatoreResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'barbersApp.operatore.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
