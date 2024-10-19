import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';
import { ListVehicleTypesPage } from './list-vehicle-types.po';

describe('List vehicle type', () => {
    let page: ListVehicleTypesPage;

    beforeEach(async () => {
        page = new ListVehicleTypesPage();
        await page.navigateTo();
    });

}
);