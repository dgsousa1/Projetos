import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';
import { ListLinesPage } from './list-lines.po';

describe('List Lines', () => {
    let page: ListLinesPage;

    beforeEach(async () => {
        page = new ListLinesPage();
        await page.navigateTo();
    });

    it('Get all lines', async () => {
        await page.getSearchButton().click()

    })

}
);