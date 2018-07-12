import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarbersAppSharedModule } from 'app/shared';
import {
    NegozioComponent,
    NegozioDetailComponent,
    NegozioUpdateComponent,
    NegozioDeletePopupComponent,
    NegozioDeleteDialogComponent,
    negozioRoute,
    negozioPopupRoute
} from './';

const ENTITY_STATES = [...negozioRoute, ...negozioPopupRoute];

@NgModule({
    imports: [BarbersAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        NegozioComponent,
        NegozioDetailComponent,
        NegozioUpdateComponent,
        NegozioDeleteDialogComponent,
        NegozioDeletePopupComponent
    ],
    entryComponents: [NegozioComponent, NegozioUpdateComponent, NegozioDeleteDialogComponent, NegozioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarbersAppNegozioModule {}
