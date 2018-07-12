import { INegozio } from 'app/shared/model//negozio.model';

export interface IOperatore {
    id?: number;
    codiceOperatore?: string;
    nome?: string;
    cognome?: string;
    note?: string;
    fotoProfilo?: any;
    negozios?: INegozio[];
}

export class Operatore implements IOperatore {
    constructor(
        public id?: number,
        public codiceOperatore?: string,
        public nome?: string,
        public cognome?: string,
        public note?: string,
        public fotoProfilo?: any,
        public negozios?: INegozio[]
    ) {}
}
