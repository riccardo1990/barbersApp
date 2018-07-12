/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BarbersAppTestModule } from '../../../test.module';
import { ServizioDeleteDialogComponent } from 'app/entities/servizio/servizio-delete-dialog.component';
import { ServizioService } from 'app/entities/servizio/servizio.service';

describe('Component Tests', () => {
    describe('Servizio Management Delete Component', () => {
        let comp: ServizioDeleteDialogComponent;
        let fixture: ComponentFixture<ServizioDeleteDialogComponent>;
        let service: ServizioService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BarbersAppTestModule],
                declarations: [ServizioDeleteDialogComponent]
            })
                .overrideTemplate(ServizioDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ServizioDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ServizioService);
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
