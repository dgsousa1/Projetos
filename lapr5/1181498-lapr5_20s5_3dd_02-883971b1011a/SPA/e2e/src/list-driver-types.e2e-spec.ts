import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';
import { ListDriverTypesPage } from './list-driver-types.po';

describe('List driver type', () => {
    let page: ListDriverTypesPage;

    beforeEach(async () => {
        page = new ListDriverTypesPage();
        await page.navigateTo();
    });

}
);