import { browser, by, element } from 'protractor';
export class CreateNodePage {
    navigateTo() {
        return browser.get('/create-node');
    }
    getNameTextbox() {
        return element(by.name('name'));
    }

    getShortNameTextbox() {
        return element(by.name('shortName'));
    }

    getLatitudeTextbox() {
        return element(by.name('latitude'));
    }

    getLongitudeTextbox() {
        return element(by.name('longitude'));
    }

    getDepotCheckbox(){
        return element(by.id('isDepot'));
    }

    getReliefCheckbox(){
        return element(by.id('isReliefPoint'));
    }

    getForm() {
        return element(by.css('#nodeForm'));
    }

    getSubmitButton() {
        return element(by.css('#btnSubmit'));
    }

}