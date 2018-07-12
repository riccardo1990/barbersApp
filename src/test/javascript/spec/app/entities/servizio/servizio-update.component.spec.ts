/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BarbersAppTestModule } from '../../../test.module';
import { ServizioUpdateComponent } from 'app/entities/servizio/servizio-update.component';
import { ServizioService } from 'app/entities/servizio/servizio.service';
import { Servizio } from 'app/shared/model/servizio.model';

describe('Component Tests', () => {
    describe('Servizio Management Update Component', () => {
        let comp: ServizioUpdateComponent;
        let fixture: ComponentFixture<ServizioUpdateComponent>;
        let service: ServizioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BarbersAppTestModule],
                declarations: [ServizioUpdateComponent]
            })
                .overrideTemplate(ServizioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ServizioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServizioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Servizio(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.servizio = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Servizio();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.servizio = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
