import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IServizio } from 'app/shared/model/servizio.model';

@Component({
    selector: 'jhi-servizio-detail',
    templateUrl: './servizio-detail.component.html'
})
export class ServizioDetailComponent implements OnInit {
    servizio: IServizio;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
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
    previousState() {
        window.history.back();
    }
}
