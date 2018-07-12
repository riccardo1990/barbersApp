import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';

import { IPrenotazione } from 'app/shared/model/prenotazione.model';
import { PrenotazioneService } from './prenotazione.service';
import { IOperatore } from 'app/shared/model/operatore.model';
import { OperatoreService } from 'app/entities/operatore';
import { IServizio } from 'app/shared/model/servizio.model';
import { ServizioService } from 'app/entities/servizio';
import { INegozio } from 'app/shared/model/negozio.model';
import { NegozioService } from 'app/entities/negozio';

@Component({
    selector: 'jhi-prenotazione-update',
    templateUrl: './prenotazione-update.component.html'
})
export class PrenotazioneUpdateComponent implements OnInit {
    private _prenotazione: IPrenotazione;
    isSaving: boolean;

    operatores: IOperatore[];

    servizios: IServizio[];

    negozios: INegozio[];
    data: string;

    constructor(
        private jhiAlertService: JhiAlertService,
        private prenotazioneService: PrenotazioneService,
        private operatoreService: OperatoreService,
        private servizioService: ServizioService,
        private negozioService: NegozioService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ prenotazione }) => {
            this.prenotazione = prenotazione;
        });
        this.operatoreService.query().subscribe(
            (res: HttpResponse<IOperatore[]>) => {
                this.operatores = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.servizioService.query().subscribe(
            (res: HttpResponse<IServizio[]>) => {
                this.servizios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.negozioService.query().subscribe(
            (res: HttpResponse<INegozio[]>) => {
                this.negozios = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.prenotazione.data = moment(this.data, DATE_TIME_FORMAT);
        if (this.prenotazione.id !== undefined) {
            this.subscribeToSaveResponse(this.prenotazioneService.update(this.prenotazione));
        } else {
            this.subscribeToSaveResponse(this.prenotazioneService.create(this.prenotazione));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IPrenotazione>>) {
        result.subscribe((res: HttpResponse<IPrenotazione>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackServizioById(index: number, item: IServizio) {
        return item.id;
    }

    trackNegozioById(index: number, item: INegozio) {
        return item.id;
    }
    get prenotazione() {
        return this._prenotazione;
    }

    set prenotazione(prenotazione: IPrenotazione) {
        this._prenotazione = prenotazione;
        this.data = moment(prenotazione.data).format(DATE_TIME_FORMAT);
    }
}
