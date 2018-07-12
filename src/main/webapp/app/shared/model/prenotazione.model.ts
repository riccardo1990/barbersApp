import { Moment } from 'moment';
import { IOperatore } from 'app/shared/model//operatore.model';
import { IServizio } from 'app/shared/model//servizio.model';
import { INegozio } from 'app/shared/model//negozio.model';

export interface IPrenotazione {
    id?: number;
    codicePrenotazione?: string;
    data?: Moment;
    note?: string;
    operatore?: IOperatore;
    servizio?: IServizio;
    negozio?: INegozio;
}

export class Prenotazione implements IPrenotazione {
    constructor(
        public id?: number,
        public codicePrenotazione?: string,
        public data?: Moment,
        public note?: string,
        public operatore?: IOperatore,
        public servizio?: IServizio,
        public negozio?: INegozio
    ) {}
}
