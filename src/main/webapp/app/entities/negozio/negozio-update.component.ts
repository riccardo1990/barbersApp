import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { INegozio } from 'app/shared/model/negozio.model';
import { NegozioService } from './negozio.service';
import { IOperatore } from 'app/shared/model/operatore.model';
import { OperatoreService } from 'app/entities/operatore';

@Component({
    selector: 'jhi-negozio-update',
    templateUrl: './negozio-update.component.html'
})
export class NegozioUpdateComponent implements OnInit {
    private _negozio: INegozio;
    isSaving: boolean;

    operatores: IOperatore[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private negozioService: NegozioService,
        private operatoreService: OperatoreService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ negozio }) => {
            this.negozio = negozio;
        });
        this.operatoreService.query().subscribe(
            (res: HttpResponse<IOperatore[]>) => {
                this.operatores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.negozio.id !== undefined) {
            this.subscribeToSaveResponse(this.negozioService.update(this.negozio));
        } else {
            this.subscribeToSaveResponse(this.negozioService.create(this.negozio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<INegozio>>) {
        result.subscribe((res: HttpResponse<INegozio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackOperatoreById(index: number, item: IOperatore) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get negozio() {
        return this._negozio;
    }

    set negozio(negozio: INegozio) {
        this._negozio = negozio;
    }
}
