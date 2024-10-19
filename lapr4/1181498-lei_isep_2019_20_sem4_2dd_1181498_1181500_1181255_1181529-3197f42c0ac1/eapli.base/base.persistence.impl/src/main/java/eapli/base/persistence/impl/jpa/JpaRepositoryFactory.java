package eapli.base.persistence.impl.jpa;

import eapli.base.Application;
import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.infrastructure.persistence.RepositoryFactory;
import eapli.base.machine.repositories.MachineRepository;
import eapli.base.material.repositories.MaterialRepository;
import eapli.base.message.domain.Message;
import eapli.base.processementerrornotification.repositories.ErrorNotificationRepository;
import eapli.base.product.repositories.BillOfMaterialsRepository;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productionline.repositories.ProductionLineRepository;
import eapli.base.productionorder.repositories.ProductionOrderRepository;
import eapli.base.rawmaterial.repositories.RawMaterialRepository;
import eapli.base.services.repositories.RawMessageRepository;
import eapli.base.storagearea.repositories.BatchRepository;
import eapli.base.storagearea.repositories.StorageAreaRepository;
import eapli.base.utils.repositories.UnitRepository;
import eapli.framework.domain.repositories.TransactionalContext;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;
import eapli.framework.infrastructure.authz.repositories.impl.JpaAutoTxUserRepository;
import eapli.framework.infrastructure.repositories.impl.jpa.JpaAutoTxRepository;

/**
 *
 * Created by nuno on 21/03/16.
 */
public class JpaRepositoryFactory implements RepositoryFactory {

	@Override
	public UserRepository users(final TransactionalContext autoTx) {
		return new JpaAutoTxUserRepository(autoTx);
	}

	@Override
	public UserRepository users() {
		return new JpaAutoTxUserRepository(Application.settings().getPersistenceUnitName(),
				Application.settings().getExtendedPersistenceProperties());
	}


	@Override
	public JpaClientUserRepository clientUsers(final TransactionalContext autoTx) {
		return new JpaClientUserRepository(autoTx);
	}

	@Override
	public JpaClientUserRepository clientUsers() {
		return new JpaClientUserRepository(Application.settings().getPersistenceUnitName());
	}

	@Override
	public SignupRequestRepository signupRequests(final TransactionalContext autoTx) {
		return new JpaSignupRequestRepository(autoTx);
	}

	@Override
	public SignupRequestRepository signupRequests() {
		return new JpaSignupRequestRepository(Application.settings().getPersistenceUnitName());
	}


	@Override
	public TransactionalContext newTransactionalContext() {
		return JpaAutoTxRepository.buildTransactionalContext(Application.settings().getPersistenceUnitName(),
				Application.settings().getExtendedPersistenceProperties());
	}

	@Override
	public MaterialRepository materials(){return new JpaMaterialRepository();}

	@Override
	public ProductRepository product() {return new JpaProductRepository();}

	@Override
	public MachineRepository machine() {return new JpaMachineRepository();}

	@Override
	public ProductionLineRepository productionLines() { return new JpaProductionLineRepository();}

	@Override
	public StorageAreaRepository storageArea() {return new JpaStorageAreaRepository();}

	@Override
	public ProductionOrderRepository productionOrders() { return new JpaProductionOrderRepository();}


	@Override
	public UnitRepository units() { return  new JpaUnitRepository(); }

	@Override
	public BillOfMaterialsRepository billsofmaterials() { return new JpaBillOfMaterialsRepository();}

	@Override
	public RawMaterialRepository rawmaterials() {
		return new JpaRawMaterialRepository();
	}

	@Override
	public RawMessageRepository rawMessage() { return new JpaRawMessageRepository(); }
        
        @Override
        public ErrorNotificationRepository errorNotification(){ return new JpaErrorNotificationRepository();}

	@Override
	public JpaMessageRepository message() {return new JpaMessageRepository();}

	@Override
	public BatchRepository batches() {
		return new JpaBatchRepository();
	}


}
