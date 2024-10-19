import { browser, by, element } from 'protractor';
export class GenerateTripPage {
    navigateTo() {
        return browser.get('/generate-trip');
    }
    getNumberOfTripsTextbox() {
        return element(by.name('tripNumber'));
    }

    getStartDateCompanyTextbox() {
        return element(by.name('startDateService'));
    }

    getLineTextbox() {
        return element(by.id('lineNameId'));
    }

    getPathTextbox() {
        return element(by.id('pathNumberId'));
    }

    getFrequencyTextbox() {
        return element(by.name('frequency'));
    }

    getForm() {
        return element(by.css('#generateTripsForm'));
    }

    getSubmitButton() {
        return element(by.css('#btnSubmit'));
    }

}