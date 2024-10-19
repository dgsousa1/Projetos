import { browser, by, element } from 'protractor';
export class CreateDriverPage {
    navigateTo() {
        return browser.get('/driver');
    }
    getMecNumberTextbox() {
        return element(by.name('mecNumber'));
    }

    getNameTextbox() {
        return element(by.name('name'));
    }

    getBirthDateTextbox() {
        return element(by.name('birthDate'));
    }

    getCCTextbox() {
        return element(by.name('cc'));
    }

    getNifTextbox() {
        return element(by.name('nif'));
    }

    getDriverTypeTextbox() {
        return element(by.id('idDriverType'));
    }

    getStartDateCompanyTextbox() {
        return element(by.name('startDateCompany'));
    }

    getFinalDateCompanyTextbox() {
        return element(by.name('finalDateCompany'));
    }

    getLicenseNumberTextbox() {
        return element(by.name('licenseNumber'));
    }

    getLicenseDateTextbox() {
        return element(by.name('licenseDate'));
    }

    getForm() {
        return element(by.css('#driverForm'));
    }

    getSubmitButton() {
        return element(by.css('#btnSubmit'));
    }

}