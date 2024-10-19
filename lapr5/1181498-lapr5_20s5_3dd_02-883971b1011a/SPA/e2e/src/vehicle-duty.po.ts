import { browser, by, element } from 'protractor';
export class CreateVehicleDutyPage {
    navigateTo() {
        return browser.get('/service-duty');
    }
    getCodeTextbox() {
        return element(by.name('code'));
    }
    
    getValidDateTextbox() {
        return element(by.name('validDate'));
    }

    getWorkBlocksTextbox() {
        return element(by.id('workblock'));
    }

    getColorTextbox() {
        return element(by.name('color'));
    }

    getForm() {
        return element(by.css('#serviceDutyForm'));
    }

    getSubmitButton() {
        return element(by.css('#btnSubmit'));
    }

}