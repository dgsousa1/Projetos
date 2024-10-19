import 'reflect-metadata'; // We need this in order to use @Decorators

import config from '../config';

import express from 'express';

import Logger from './loaders/logger';

async function startServer() {
  const app = express();

  app.set('port', (process.env.PORT || 8080));

  await require('./loaders').default({ expressApp: app });

  app.get('/', function (request, response) {
    var result = 'App is running'
    response.send(result);
  }).listen(app.get('port'), function () {

    Logger.info(`
      ################################################
      🛡️  Server listening on port: ${config.port} 🛡️ 
      ################################################
    `);
  })
};
startServer();
