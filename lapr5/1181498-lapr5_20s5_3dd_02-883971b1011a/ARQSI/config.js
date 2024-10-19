import dotenv from 'dotenv';

// Set the NODE_ENV to 'development' by default
process.env.NODE_ENV = process.env.NODE_ENV || 'development';

const envFound = dotenv.config();
if (!envFound) {
  // This error should crash whole process

  throw new Error("⚠️  Couldn't find .env file  ⚠️");
}

export default {
  /**
   * Your favorite port
   */
  port: parseInt(process.env.PORT, 10),

  /**
   * That long string from mlab
   */
  databaseURL: process.env.MONGODB_URI,

  /**
   * Your secret sauce
   */
  jwtSecret: process.env.JWT_SECRET,

  /**
   * Used by winston logger
   */
  logs: {
    level: process.env.LOG_LEVEL || 'silly',
  },

  /**
   * API configs
   */
  api: {
    prefix: '/api',
  },

  controller: {
    node: {
      name: "nodeController",
      path: "../controllers/nodeController"
    },
    vehicleType: {
      name: "vehicleTypeController",
      path: "../controllers/vehicleTypeController"
    },
    driverType: {
      name: "driverTypeController",
      path: "../controllers/driverTypeController"
    },
    path: {
      name: "pathController",
      path: "../controllers/pathController"
    },
    import: {
      name: "importController",
      path: "../controllers/importController"
    },
    line: {
      name: "lineController",
      path: "../controllers/lineController"
    },
    mock : {
      name: "mockController",
      path: "../controllers/mockController"
    }
  },

  repos: {
    node: {
      name: "NodeRepo",
      path: "../repos/nodeRepo"
    },
    vehicleType: {
      name: "VehicleTypeRepo",
      path: "../repos/vehicleTypeRepo"
    },
    driverType: {
      name: "DriverTypeRepo",
      path: "../repos/driverTypeRepo"
    },
    path: {
      name: "PathRepo",
      path: "../repos/pathRepo"
    },
    line: {
      name: "LineRepo",
      path: "../repos/lineRepo"
    }
  },

  services: {
    node: {
      name: "NodeService",
      path: "../services/nodeService"
    },
    vehicleType: {
      name: "VehicleTypeService",
      path: "../services/vehicleTypeService"
    },
    driverType: {
      name: "DriverTypeService",
      path: "../services/driverTypeService"
    },
    path: {
      name: "PathService",
      path: "../services/pathService"
    },
    import: {
      name: "ImportService",
      path: "../services/importService"
    },
    line: {
      name: "LineService",
      path: "../services/lineService"
    }
  },
};
