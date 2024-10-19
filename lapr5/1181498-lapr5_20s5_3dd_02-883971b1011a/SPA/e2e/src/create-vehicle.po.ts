import { browser, by, element } from 'protractor';
export class CreateVehiclePage {
    navigateTo() {
        return browser.get('/vehicle');
    }
    getLicensePlateTextbox() {
        return element(by.name('licensePlate'));
    }

    getVINTextbox() {
        return element(by.name('vin'));
    }

    getVehicleTypeTextbox() {
        return element(by.id('idVehicleType'));
    }

    getStartDateCompanyTextbox() {
        return element(by.name('startDateService'));
    }

    getForm() {
        return element(by.css('#vehicleForm'));
    }

    getSubmitButton() {
        return element(by.css('#btnSubmit'));
    }

}