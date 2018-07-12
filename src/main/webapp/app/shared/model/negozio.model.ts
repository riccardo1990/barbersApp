import { IOperatore } from 'app/shared/model//operatore.model';

export interface INegozio {
    id?: number;
    codiceNegozio?: string;
    descrizioneNegozio?: string;
    indirizzo?: string;
    cap?: number;
    citta?: string;
    telefono?: string;
    operatores?: IOperatore[];
}

export class Negozio implements INegozio {
    constructor(
        public id?: number,
        public codiceNegozio?: string,
        public descrizioneNegozio?: string,
        public indirizzo?: string,
        public cap?: number,
        public citta?: string,
        public telefono?: string,
        public operatores?: IOperatore[]
    ) {}
}
