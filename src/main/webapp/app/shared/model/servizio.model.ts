export interface IServizio {
    id?: number;
    codiceServizio?: string;
    descrizioneServizio?: string;
    durata?: number;
    costo?: number;
    fotoServizio?: any;
}

export class Servizio implements IServizio {
    constructor(
        public id?: number,
        public codiceServizio?: string,
        public descrizioneServizio?: string,
        public durata?: number,
        public costo?: number,
        public fotoServizio?: any
    ) {}
}
