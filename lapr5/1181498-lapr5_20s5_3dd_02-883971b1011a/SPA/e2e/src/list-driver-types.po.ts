import { browser, by, element } from 'protractor';

export class ListDriverTypesPage {
    navigateTo() {
        return browser.get('/list-driver-types');
    }

}