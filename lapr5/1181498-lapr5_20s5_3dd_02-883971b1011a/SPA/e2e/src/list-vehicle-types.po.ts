import { browser, by, element } from 'protractor';

export class ListVehicleTypesPage {
    navigateTo() {
        return browser.get('/list-vehicle-types');
    }

}