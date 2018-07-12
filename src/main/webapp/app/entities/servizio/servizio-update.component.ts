import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IServizio } from 'app/shared/model/servizio.model';
import { ServizioService } from './servizio.service';

@Component({
    selector: 'jhi-servizio-update',
    templateUrl: './servizio-update.component.html'
})
export class ServizioUpdateComponent implements OnInit {
    private _servizio: IServizio;
    isSaving: boolean;

    constructor(private dataUtils: JhiDataUtils, private servizioService: ServizioService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ servizio }) => {
            this.servizio = servizio;
        });
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
        if (this.servizio.id !== undefined) {
            this.subscribeToSaveResponse(this.servizioService.update(this.servizio));
        } else {
            this.subscribeToSaveResponse(this.servizioService.create(this.servizio));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IServizio>>) {
        result.subscribe((res: HttpResponse<IServizio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get servizio() {
        return this._servizio;
    }

    set servizio(servizio: IServizio) {
        this._servizio = servizio;
    }
}
