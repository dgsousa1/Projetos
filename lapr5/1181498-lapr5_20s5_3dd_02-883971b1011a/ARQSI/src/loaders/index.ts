import expressLoader from './express';
import dependencyInjectorLoader from './dependencyInjector';
import mongooseLoader from './mongoose';
import Logger from './logger';

import config from '../../config';

export default async ({ expressApp }) => {
  const mongoConnection = await mongooseLoader();
  Logger.info('✌️ DB loaded and connected!');

  const nodeSchema = {
    // compare with the approach followed in repos and services
    name: 'nodeSchema',
    schema: '../persistence/schemas/nodeSchema',
 };

 const vehicleTypeSchema = {
  // compare with the approach followed in repos and services
  name: 'vehicleTypeSchema',
  schema: '../persistence/schemas/vehicleTypeSchema',
  };

  const driverTypeSchema = {
    // compare with the approach followed in repos and services
    name: 'driverTypeSchema',
    schema: '../persistence/schemas/driverTypeSchema',
  };

  const pathSchema = {
    // compare with the approach followed in repos and services
    name: 'pathSchema',
    schema: '../persistence/schemas/pathSchema',
  };

  const lineSchema = {
    // compare with the approach followed in repos and services
    name: 'lineSchema',
    schema: '../persistence/schemas/lineSchema',
  };

  const nodeController = {
    name: config.controller.node.name,
    path: config.controller.node.path
  }

  const vehicleTypeController = {
    name: config.controller.vehicleType.name,
    path: config.controller.vehicleType.path
  }

  const driverTypeController = {
    name: config.controller.driverType.name,
    path: config.controller.driverType.path
  }

  const importController = {
    name: config.controller.import.name,
    path: config.controller.import.path
  }

  const pathController = {
    name: config.controller.path.name,
    path: config.controller.path.path
  }

  const lineController = {
    name: config.controller.line.name,
    path: config.controller.line.path
  }

  const mockController = {
    name: config.controller.mock.name,
    path: config.controller.mock.path
  }

  const nodeRepo = {
    name: config.repos.node.name,
    path: config.repos.node.path
  }

  const vehicleTypeRepo = {
    name: config.repos.vehicleType.name,
    path: config.repos.vehicleType.path
  }

  const driverTypeRepo = {
    name: config.repos.driverType.name,
    path: config.repos.driverType.path
  }

  const pathRepo = {
    name: config.repos.path.name,
    path: config.repos.path.path
  }

  const lineRepo = {
    name: config.repos.line.name,
    path: config.repos.line.path
  }

  const nodeService = {
    name: config.services.node.name,
    path: config.services.node.path
  }

  const vehicleTypeService = {
    name: config.services.vehicleType.name,
    path: config.services.vehicleType.path
  }

  const driverTypeService = {
    name: config.services.driverType.name,
    path: config.services.driverType.path
  }

  const pathService = {
    name: config.services.path.name,
    path: config.services.path.path
  }

  const importService = {
    name: config.services.import.name,
    path: config.services.import.path
  }

  const lineService = {
    name: config.services.line.name,
    path: config.services.line.path
  }

  await dependencyInjectorLoader({
    mongoConnection,
    schemas: [
      nodeSchema,
      vehicleTypeSchema,
      driverTypeSchema,
      pathSchema,
      lineSchema
    ],
    controllers: [
      nodeController,
      vehicleTypeController,
      driverTypeController,
      pathController,
      importController,
      lineController,
      mockController
    ],
    repos: [
      nodeRepo,
      vehicleTypeRepo,
      driverTypeRepo,
      pathRepo,
      lineRepo
    ],
    services: [
      nodeService,
      vehicleTypeService,
      driverTypeService,
      pathService,
      importService,
      lineService
    ]
  });
  Logger.info('✌️ Schemas, Controllers, Repositories, Services, etc. loaded');

  await expressLoader({ app: expressApp });
  Logger.info('✌️ Express loaded');
};
