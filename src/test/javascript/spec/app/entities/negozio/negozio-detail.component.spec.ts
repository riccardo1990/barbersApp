/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BarbersAppTestModule } from '../../../test.module';
import { NegozioDetailComponent } from 'app/entities/negozio/negozio-detail.component';
import { Negozio } from 'app/shared/model/negozio.model';

describe('Component Tests', () => {
    describe('Negozio Management Detail Component', () => {
        let comp: NegozioDetailComponent;
        let fixture: ComponentFixture<NegozioDetailComponent>;
        const route = ({ data: of({ negozio: new Negozio(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BarbersAppTestModule],
                declarations: [NegozioDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(NegozioDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NegozioDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.negozio).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
