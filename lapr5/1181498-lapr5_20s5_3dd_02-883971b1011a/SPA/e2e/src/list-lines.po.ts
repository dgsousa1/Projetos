import { browser, by, element } from 'protractor';

export class ListLinesPage {
    navigateTo() {
        return browser.get('/list-lines');
    }

    getSearchButton() {
        return element(by.buttonText('Procurar'));
        //return element(by.id('btnSearch'));
    }



}