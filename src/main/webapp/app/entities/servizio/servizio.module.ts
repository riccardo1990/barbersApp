import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BarbersAppSharedModule } from 'app/shared';
import {
    ServizioComponent,
    ServizioDetailComponent,
    ServizioUpdateComponent,
    ServizioDeletePopupComponent,
    ServizioDeleteDialogComponent,
    servizioRoute,
    servizioPopupRoute
} from './';

const ENTITY_STATES = [...servizioRoute, ...servizioPopupRoute];

@NgModule({
    imports: [BarbersAppSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ServizioComponent,
        ServizioDetailComponent,
        ServizioUpdateComponent,
        ServizioDeleteDialogComponent,
        ServizioDeletePopupComponent
    ],
    entryComponents: [ServizioComponent, ServizioUpdateComponent, ServizioDeleteDialogComponent, ServizioDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarbersAppServizioModule {}
