import { browser, by, element } from 'protractor';
export class CreateWorkBlockPage {
    navigateTo() {
        return browser.get('/work-block');
    }
    getKeyTextbox() {
        return element(by.name('wbKey'));
    }
    
    getStartTimeCompanyTextbox() {
        return element(by.name('startTime'));
    }

    getEndTimeCompanyTextbox() {
        return element(by.name('endTime'));
    }

    getTripTextbox() {
        return element(by.id('trip'));
    }

    getForm() {
        return element(by.css('#workBlockForm'));
    }

    getSubmitButton() {
        return element(by.css('#btnSubmit'));
    }

}