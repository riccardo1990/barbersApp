import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IOperatore } from 'app/shared/model/operatore.model';
import { Principal } from 'app/core';
import { OperatoreService } from './operatore.service';

@Component({
    selector: 'jhi-operatore',
    templateUrl: './operatore.component.html'
})
export class OperatoreComponent implements OnInit, OnDestroy {
    operatores: IOperatore[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private operatoreService: OperatoreService,
        private jhiAlertService: JhiAlertService,
        private dataUtils: JhiDataUtils,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.operatoreService.query().subscribe(
            (res: HttpResponse<IOperatore[]>) => {
                this.operatores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOperatores();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOperatore) {
        return item.id;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    registerChangeInOperatores() {
        this.eventSubscriber = this.eventManager.subscribe('operatoreListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
