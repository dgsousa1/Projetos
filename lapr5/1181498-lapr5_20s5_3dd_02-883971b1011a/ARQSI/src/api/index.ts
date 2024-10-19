import { Router } from 'express';
//import auth from './routes/authRoute';
import node from './routes/nodeRoute';
import vehicleType from './routes/vehicleTypeRoute';
import driverType from './routes/driverTypeRoute';
import path from './routes/pathRoute';
import importFile from './routes/importFileRoute';
import line from './routes/lineRoute';
import mock from './routes/mockRoute';

export default () => {
	const app = Router();

	//auth(app);
	//user(app);
	//role(app);
	node(app);
	vehicleType(app);
	driverType(app);
	path(app);
	importFile(app);
	line(app);
	mock(app);
	return app
}