/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BarbersAppTestModule } from '../../../test.module';
import { ServizioComponent } from 'app/entities/servizio/servizio.component';
import { ServizioService } from 'app/entities/servizio/servizio.service';
import { Servizio } from 'app/shared/model/servizio.model';

describe('Component Tests', () => {
    describe('Servizio Management Component', () => {
        let comp: ServizioComponent;
        let fixture: ComponentFixture<ServizioComponent>;
        let service: ServizioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BarbersAppTestModule],
                declarations: [ServizioComponent],
                providers: []
            })
                .overrideTemplate(ServizioComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ServizioComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServizioService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Servizio(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.servizios[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
