import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IOperatore } from 'app/shared/model/operatore.model';
import { OperatoreService } from './operatore.service';
import { INegozio } from 'app/shared/model/negozio.model';
import { NegozioService } from 'app/entities/negozio';

@Component({
    selector: 'jhi-operatore-update',
    templateUrl: './operatore-update.component.html'
})
export class OperatoreUpdateComponent implements OnInit {
    private _operatore: IOperatore;
    isSaving: boolean;

    negozios: INegozio[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private operatoreService: OperatoreService,
        private negozioService: NegozioService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ operatore }) => {
            this.operatore = operatore;
        });
        this.negozioService.query().subscribe(
            (res: HttpResponse<INegozio[]>) => {
                this.negozios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.operatore.id !== undefined) {
            this.subscribeToSaveResponse(this.operatoreService.update(this.operatore));
        } else {
            this.subscribeToSaveResponse(this.operatoreService.create(this.operatore));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOperatore>>) {
        result.subscribe((res: HttpResponse<IOperatore>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackNegozioById(index: number, item: INegozio) {
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
    get operatore() {
        return this._operatore;
    }

    set operatore(operatore: IOperatore) {
        this._operatore = operatore;
    }
}
