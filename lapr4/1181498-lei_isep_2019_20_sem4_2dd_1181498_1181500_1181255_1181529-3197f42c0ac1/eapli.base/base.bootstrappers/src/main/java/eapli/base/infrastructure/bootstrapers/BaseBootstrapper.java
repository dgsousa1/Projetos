/*
 * Copyright (c) 2013-2019 the original author or authors.
 *
 * MIT License
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package eapli.base.infrastructure.bootstrapers;

import eapli.base.infrastructure.persistence.PersistenceContext;
import eapli.base.machine.application.AddMachineController;
import eapli.base.machine.application.AssociateConfigurationFileController;
import eapli.base.machine.domain.Machine;
import eapli.base.machine.domain.MachineModel;
import eapli.base.material.application.AddMaterialController;
import eapli.base.product.application.AddProductController;
import eapli.base.product.domain.Product;
import eapli.base.product.repositories.ProductRepository;
import eapli.base.productionorder.application.AddProductionOrderController;
import eapli.base.services.application.AddRawMessageToLogController;
import eapli.base.storagearea.application.AddStorageAreaController;
import eapli.base.productionline.application.AddProductionLineController;
import eapli.base.utils.application.UnitController;
import eapli.base.utils.repositories.UnitRepository;
import eapli.framework.strings.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eapli.base.usermanagement.domain.BaseRoles;
import eapli.base.usermanagement.domain.UserBuilderHelper;
import eapli.framework.actions.Action;
import eapli.framework.domain.repositories.ConcurrencyException;
import eapli.framework.domain.repositories.IntegrityViolationException;
import eapli.framework.infrastructure.authz.application.AuthenticationService;
import eapli.framework.infrastructure.authz.application.AuthorizationService;
import eapli.framework.infrastructure.authz.application.AuthzRegistry;
import eapli.framework.infrastructure.authz.domain.model.SystemUser;
import eapli.framework.infrastructure.authz.domain.model.SystemUserBuilder;
import eapli.framework.infrastructure.authz.domain.repositories.UserRepository;

import eapli.framework.validations.Invariants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Base Bootstrapping data app
 *
 * @author Paulo Gandra de Sousa
 */
@SuppressWarnings("squid:S106")
public class BaseBootstrapper implements Action {
    private static final Logger LOGGER = LoggerFactory.getLogger(
            BaseBootstrapper.class);

    private static final String POWERUSER_A1 = "poweruserA1";
    private static final String POWERUSER = "poweruser";
    private static final String PRODUCTION_MANAGER = "prodmanag";
    private static final String PRODUCTION_MANAGER_PASS = "Password123";
    private static final String FACTORY_FLOOR_MANAGER = "factorymanage";
    private static final String FACTORY_FLOOR_MANAGER_PASS = "Password123";

    private final AuthorizationService authz = AuthzRegistry.authorizationService();
    private final AuthenticationService authenticationService = AuthzRegistry.authenticationService();
    private final UserRepository userRepository = PersistenceContext.repositories().users();
    private final UnitController unitController = new UnitController();

    @Override
    public boolean execute() {
        // declare bootstrap actions
        final Action[] actions = {new MasterUsersBootstrapper(),};

        registerPowerUser();
        registerFactoryFloorManager();
        registerProductionManager();
        authenticateForBootstrapping();

        registerUnit();
        registerMaterial();
        registerProduct();
        registerMachine();
        registerStorageArea();
        registerProductionLine();
        registerProductionOrder();
        registerRawMessage();
        registerConfigurationFile();

        // execute all bootstrapping
        boolean ret = true;
        for (final Action boot : actions) {
            System.out.println("Bootstrapping " + nameOfEntity(boot) + "...");
            ret &= boot.execute();
        }
        return ret;
    }


    /**
     * register a factory floor manager directly in the persistence layer as we need to
     * circumvent authorisations in the Application Layer
     */
    private boolean registerFactoryFloorManager() {
        final SystemUserBuilder userBuilder = UserBuilderHelper.builder();
        userBuilder.withUsername(FACTORY_FLOOR_MANAGER).withPassword(FACTORY_FLOOR_MANAGER_PASS).withName("Jane", "Doe")
                .withEmail("jayjay@email.org").withRoles(BaseRoles.FACTORY_MANAGER);
        final SystemUser newUser = userBuilder.build();

        SystemUser poweruser;
        try {
            poweruser = userRepository.save(newUser);
            assert poweruser != null;
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", newUser.username());
            LOGGER.trace("Assuming existing record", e);
            return false;
        }
    }


    /**
     * register a production manager directly in the persistence layer as we need to
     * circumvent authorisations in the Application Layer
     */
    private boolean registerProductionManager() {
        final SystemUserBuilder userBuilder = UserBuilderHelper.builder();
        userBuilder.withUsername(PRODUCTION_MANAGER).withPassword(PRODUCTION_MANAGER_PASS).withName("John", "Doe")
                .withEmail("jojo@email.org").withRoles(BaseRoles.PRODUCTION_MANAGER);
        final SystemUser newUser = userBuilder.build();

        SystemUser poweruser;
        try {
            poweruser = userRepository.save(newUser);
            assert poweruser != null;
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", newUser.username());
            LOGGER.trace("Assuming existing record", e);
            return false;
        }
    }

    /**
     * register a power user directly in the persistence layer as we need to
     * circumvent authorisations in the Application Layer
     */
    private boolean registerPowerUser() {
        final SystemUserBuilder userBuilder = UserBuilderHelper.builder();
        userBuilder.withUsername(POWERUSER).withPassword(POWERUSER_A1).withName("joe", "power")
                .withEmail("joe@email.org").withRoles(BaseRoles.POWER_USER);
        final SystemUser newUser = userBuilder.build();

        SystemUser poweruser;
        try {
            poweruser = userRepository.save(newUser);
            assert poweruser != null;
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            // ignoring exception. assuming it is just a primary key violation
            // due to the tentative of inserting a duplicated user
            LOGGER.warn("Assuming {} already exists (activate trace log for details)", newUser.username());
            LOGGER.trace("Assuming existing record", e);
            return false;
        }
    }


    /**
     * authenticate a super user to be able to register new users
     */
    protected void authenticateForBootstrapping() {
        authenticationService.authenticate(POWERUSER, POWERUSER_A1);
        Invariants.ensure(authz.hasSession());
    }

    private String nameOfEntity(final Action boot) {
        final String name = boot.getClass().getSimpleName();
        return Strings.left(name, name.length() - "Bootstrapper".length());
    }

    private boolean registerMaterial() {
        final AddMaterialController materialController = new AddMaterialController();
        final UnitRepository unitRepository = PersistenceContext.repositories().units();
        try {
            materialController.addMaterial("AB11", "Material1",
                    "CAT1", "Category1", "File1", "FilePath1", unitRepository.findUnit("UN").get());
            materialController.addMaterial("AB22", "Material2",
                    "CAT2", "Category2", "File2", "FilePath2", unitRepository.findUnit("UN").get());
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.trace("Assuming existing record", e);
            return false;
        }
    }

    private boolean registerUnit() {
        try {
            unitController.addUnit("UN");
            unitController.addUnit("KG");
            unitController.addUnit("L");
            unitController.addUnit("M");
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.trace("Assuming existing record", e);
            return false;
        }

    }


    private boolean registerProduct() {
        final AddProductController productController = new AddProductController();
        final UnitRepository unitRepository = PersistenceContext.repositories().units();

        try {
            productController.addProduct("1111", "2222",
                    "Product1", "First Product", unitRepository.findUnit("UN").get(), "CAT1");
            productController.addProduct("2222", "5555",
                    "Product2", "Second Product", unitRepository.findUnit("UN").get(), "CAT2");
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.trace("Assuming existing record", e);
            return false;
        }
    }

    private boolean registerProductionOrder() {
        final AddProductController productController = new AddProductController();
        final AddProductionOrderController addProductionOrderController = new AddProductionOrderController();
        final ProductRepository productRepository = PersistenceContext.repositories().product();
        final String DATE_FORMAT = "yyyy-MM-dd HH:mm";
        final String EXECUTION_DATE = "2020-08-15 17:30";
        final String ISSUE_DATE = "2020-08-15 18:30";
        final String EXECUTION_DATE2 = "2020-08-20 11:30";
        final String ISSUE_DATE2 = "2020-08-21 12:30";
        final UnitRepository unitRepository = PersistenceContext.repositories().units();


        try {
            Product p1 = productController.addProduct("3333", "3333",
                    "Aluminum", "Aluminum Foil", unitRepository.findUnit("UN").get(), "MAT1");
            Product p2 = productController.addProduct("4444", "4444",
                    "Titanium", "Titanium alloy", unitRepository.findUnit("UN").get(), "TI1");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDateTime issueDate = LocalDateTime.parse(ISSUE_DATE, formatter);
            LocalDateTime issueDate2 = LocalDateTime.parse(ISSUE_DATE2, formatter);
            List<String> commissions1 = new ArrayList<>();
            commissions1.add("Encomenda01");
            commissions1.add("Encomenda02");
            List<String> commissions2 = new ArrayList<>();
            commissions2.add("Encomenda03");
            commissions2.add("Encomenda04");
            addProductionOrderController.addProductionOrder("BOOTP01", EXECUTION_DATE, issueDate,
                    productRepository.findByManufacturingCode("3333").get(), 2f, commissions1);
            addProductionOrderController.addProductionOrder("BOOTP02", EXECUTION_DATE2, issueDate2,
                    productRepository.findByManufacturingCode("4444").get(), 20f, commissions2);

            return true;
        } catch (Exception e) {
            LOGGER.trace("Assuming existing record", e);
            return false;
        }
    }

    private boolean registerStorageArea() {
        final AddStorageAreaController storageAreaController = new AddStorageAreaController();
        try {
            storageAreaController.addStorageArea("StorageArea1");
            storageAreaController.addStorageArea("StorageArea2");
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.trace("Assuming existing record", e);
            return false;
        }
    }

    private boolean registerMachine() {
        final AddMachineController machineController = new AddMachineController();
        try {
            machineController.addMachine("DD4", "Machine Description 1",
                    "CAT", Long.valueOf(11111));
            machineController.addMachine("T3", "Machine Description 2",
                    "Samsung", Long.valueOf(22222));
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.trace("Assuming existing record", e);
            return false;
        }
    }

    private boolean registerProductionLine() {
        final AddProductionLineController productionLineController = new AddProductionLineController();
        try {
            List<Machine> machines = new LinkedList<>();
            machines.add(new Machine(Long.valueOf(12121), new MachineModel("Model3", "Machine Description 3", "Brand1")));
            machines.add(new Machine(Long.valueOf(33333), new MachineModel("Model4", "Machine Description 4", "Brand2")));
            productionLineController.addProductionLine(machines);
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.trace("Assuming existing record", e);
            return false;
        }
    }

    private boolean registerRawMessage() {
        final AddRawMessageToLogController addRawMessageToLogController = new AddRawMessageToLogController();
        try {
            //date 2020-10-26 15:17:17
            addRawMessageToLogController.addRawMessageToLogs(12121L, "Brand1;C9;20201026151717;2222;7517;StorageArea1");
            //date 2020-10-26 15:18:17
            addRawMessageToLogController.addRawMessageToLogs(12121L, "Brand1;C0;20201026151817;2222;7517;StorageArea1");
            //date 2020-10-27 15:17:17
            addRawMessageToLogController.addRawMessageToLogs(33333L, "Brand2;S8;20201027151717");
            //date 2020-10-27 18:17:17
            addRawMessageToLogController.addRawMessageToLogs(33333L, "Brand2;S0;20201027151717;BOOTP01");
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.trace("Assuming existing record", e);
            return false;
        }
    }

    private boolean registerConfigurationFile() {
        final AssociateConfigurationFileController associateConfigurationFileController = new AssociateConfigurationFileController();

        try {
            Iterable<Machine> machines = associateConfigurationFileController.allMachines();
            List<Machine> mach = new ArrayList<>();
            for (Machine m : machines) {
                mach.add(m);
            }
            associateConfigurationFileController.associateConfigurationFile(mach.get(0), "ConfigFile.txt", "description");
            return true;
        } catch (ConcurrencyException | IntegrityViolationException e) {
            LOGGER.trace("Assuming existing record", e);
            return false;
        }

    }
}
