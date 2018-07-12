/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BarbersAppTestModule } from '../../../test.module';
import { ServizioDetailComponent } from 'app/entities/servizio/servizio-detail.component';
import { Servizio } from 'app/shared/model/servizio.model';

describe('Component Tests', () => {
    describe('Servizio Management Detail Component', () => {
        let comp: ServizioDetailComponent;
        let fixture: ComponentFixture<ServizioDetailComponent>;
        const route = ({ data: of({ servizio: new Servizio(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BarbersAppTestModule],
                declarations: [ServizioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ServizioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ServizioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.servizio).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
