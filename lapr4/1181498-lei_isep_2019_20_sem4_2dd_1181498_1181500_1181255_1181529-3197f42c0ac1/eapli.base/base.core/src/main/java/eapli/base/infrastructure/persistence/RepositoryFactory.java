/**
 *
 */
package eapli.base.infrastructure.persistence;

import eapli.base.clientusermanagement.repositories.ClientUserRepository;

import eapli.base.clientusermanagement.repositories.SignupRequestRepository;
import eapli.base.machine.repositories.MachineRepository;
import eapli.base.material.repositories.MaterialRepository;
import eapli.base.message.domain.Message;
import eapli.base.message.repositories.MessageRepository;
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

/**
 * @author Paulo Gandra Sousa
 *
 */
public interface RepositoryFactory {

	/**
	 * factory method to create a transactional context to use in the repositories
	 *
	 * @return
	 */
	TransactionalContext newTransactionalContext();

	/**
	 *
	 * @param autoTx the transactional context to enrol
	 * @return
	 */
	UserRepository users(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	UserRepository users();

	/**
	 *
	 * @param autoTx the transactional context to enroll
	 * @return
	 */
	ClientUserRepository clientUsers(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	ClientUserRepository clientUsers();

	/**
	 *
	 * @param autoTx the transactional context to enroll
	 * @return
	 */
	SignupRequestRepository signupRequests(TransactionalContext autoTx);

	/**
	 * repository will be created in auto transaction mode
	 *
	 * @return
	 */
	SignupRequestRepository signupRequests();

	MaterialRepository materials();

    ProductRepository product();

    MachineRepository machine();

    ProductionLineRepository productionLines();

    StorageAreaRepository storageArea();

    ProductionOrderRepository productionOrders();

    UnitRepository units();

    BillOfMaterialsRepository billsofmaterials();

    RawMaterialRepository rawmaterials();

    RawMessageRepository rawMessage();
    
    ErrorNotificationRepository errorNotification();

    MessageRepository message();

    BatchRepository batches();

}
