/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BarbersAppTestModule } from '../../../test.module';
import { OperatoreDeleteDialogComponent } from 'app/entities/operatore/operatore-delete-dialog.component';
import { OperatoreService } from 'app/entities/operatore/operatore.service';

describe('Component Tests', () => {
    describe('Operatore Management Delete Component', () => {
        let comp: OperatoreDeleteDialogComponent;
        let fixture: ComponentFixture<OperatoreDeleteDialogComponent>;
        let service: OperatoreService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BarbersAppTestModule],
                declarations: [OperatoreDeleteDialogComponent]
            })
                .overrideTemplate(OperatoreDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OperatoreDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OperatoreService);
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
