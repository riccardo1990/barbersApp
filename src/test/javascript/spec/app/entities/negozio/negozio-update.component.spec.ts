/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BarbersAppTestModule } from '../../../test.module';
import { NegozioUpdateComponent } from 'app/entities/negozio/negozio-update.component';
import { NegozioService } from 'app/entities/negozio/negozio.service';
import { Negozio } from 'app/shared/model/negozio.model';

describe('Component Tests', () => {
    describe('Negozio Management Update Component', () => {
        let comp: NegozioUpdateComponent;
        let fixture: ComponentFixture<NegozioUpdateComponent>;
        let service: NegozioService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BarbersAppTestModule],
                declarations: [NegozioUpdateComponent]
            })
                .overrideTemplate(NegozioUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(NegozioUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NegozioService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Negozio(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.negozio = entity;
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
                    const entity = new Negozio();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.negozio = entity;
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
