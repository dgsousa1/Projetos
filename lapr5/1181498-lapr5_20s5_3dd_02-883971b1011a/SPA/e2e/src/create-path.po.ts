import { browser, by, element } from 'protractor';
export class CreatePathPage {
    navigateTo() {
        return browser.get('/create-path');
    }

    getKeyTextbox() {
        return element(by.name('key'));
    }

    getDurationTextbox() {
        return element(by.name('duracao'));
    }

    getDistanceTextbox() {
        return element(by.name('distancia'));
    }

    getInicialNodeSelect() {
        return element(by.id('inicialNodeId'));
    }

    getFinalNodeSelect() {
        return element(by.id('finalNodeId'));
    }
    
    getForm() {
        return element(by.css('#form'));
    }

    getSubmitButton() {
        return element(by.css('#btnSubmit'));
    }


}