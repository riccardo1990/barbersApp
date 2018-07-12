import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BarbersAppPrenotazioneModule } from './prenotazione/prenotazione.module';
import { BarbersAppNegozioModule } from './negozio/negozio.module';
import { BarbersAppServizioModule } from './servizio/servizio.module';
import { BarbersAppOperatoreModule } from './operatore/operatore.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BarbersAppPrenotazioneModule,
        BarbersAppNegozioModule,
        BarbersAppServizioModule,
        BarbersAppOperatoreModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BarbersAppEntityModule {}
