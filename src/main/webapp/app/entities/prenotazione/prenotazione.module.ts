import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarbersAppSharedModule } from 'app/shared';
import {
    PrenotazioneComponent,
    PrenotazioneDetailComponent,
    PrenotazioneUpdateComponent,
    PrenotazioneDeletePopupComponent,
    PrenotazioneDeleteDialogComponent,
    prenotazioneRoute,
    prenotazionePopupRoute
} from './';

const ENTITY_STATES = [...prenotazioneRoute, ...prenotazionePopupRoute];

@NgModule({
    imports: [BarbersAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        PrenotazioneComponent,
        PrenotazioneDetailComponent,
        PrenotazioneUpdateComponent,
        PrenotazioneDeleteDialogComponent,
        PrenotazioneDeletePopupComponent
    ],
    entryComponents: [
        PrenotazioneComponent,
        PrenotazioneUpdateComponent,
        PrenotazioneDeleteDialogComponent,
        PrenotazioneDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarbersAppPrenotazioneModule {}
