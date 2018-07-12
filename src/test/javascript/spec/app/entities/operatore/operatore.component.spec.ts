/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BarbersAppTestModule } from '../../../test.module';
import { OperatoreComponent } from 'app/entities/operatore/operatore.component';
import { OperatoreService } from 'app/entities/operatore/operatore.service';
import { Operatore } from 'app/shared/model/operatore.model';

describe('Component Tests', () => {
    describe('Operatore Management Component', () => {
        let comp: OperatoreComponent;
        let fixture: ComponentFixture<OperatoreComponent>;
        let service: OperatoreService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BarbersAppTestModule],
                declarations: [OperatoreComponent],
                providers: []
            })
                .overrideTemplate(OperatoreComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OperatoreComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperatoreService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Operatore(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.operatores[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
