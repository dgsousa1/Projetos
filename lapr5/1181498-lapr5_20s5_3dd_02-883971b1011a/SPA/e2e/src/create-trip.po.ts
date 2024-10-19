import { browser, by, element } from 'protractor';
export class CreateTripPage {
    navigateTo() {
        return browser.get('/create-trip');
    }
    getTripNumberTextbox() {
        return element(by.name('tripNumber'));
    }

    getStartDateCompanyTextbox() {
        return element(by.name('startDateService'));
    }

    getLineTextbox() {
        return element(by.id('lineName'));
    }

    getPathTextbox() {
        return element(by.id('pathNumber'));
    }

    getForm() {
        return element(by.css('#tripForm'));
    }

    getSubmitButton() {
        return element(by.css('#btnSubmit'));
    }

}