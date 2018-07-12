import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INegozio } from 'app/shared/model/negozio.model';

@Component({
    selector: 'jhi-negozio-detail',
    templateUrl: './negozio-detail.component.html'
})
export class NegozioDetailComponent implements OnInit {
    negozio: INegozio;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ negozio }) => {
            this.negozio = negozio;
        });
    }

    previousState() {
        window.history.back();
    }
}
