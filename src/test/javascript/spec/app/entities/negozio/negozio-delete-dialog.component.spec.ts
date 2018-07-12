/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BarbersAppTestModule } from '../../../test.module';
import { NegozioDeleteDialogComponent } from 'app/entities/negozio/negozio-delete-dialog.component';
import { NegozioService } from 'app/entities/negozio/negozio.service';

describe('Component Tests', () => {
    describe('Negozio Management Delete Component', () => {
        let comp: NegozioDeleteDialogComponent;
        let fixture: ComponentFixture<NegozioDeleteDialogComponent>;
        let service: NegozioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BarbersAppTestModule],
                declarations: [NegozioDeleteDialogComponent]
            })
                .overrideTemplate(NegozioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(NegozioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NegozioService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
