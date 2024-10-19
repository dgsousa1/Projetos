import { CreatePathPage } from './create-path.po';
import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';

describe('Create Path', () => {
    let page: CreatePathPage;

    beforeEach(async () => {
        page = new CreatePathPage();
        await page.navigateTo();
    });

    it('Textbox values should be valid', async () => {
        await page.getKeyTextbox().sendKeys(1);
        await page.getDurationTextbox().sendKeys(1);
        await page.getDistanceTextbox().sendKeys(1);
        await page.getInicialNodeSelect().click();
        browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
        browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
        browser.actions().sendKeys(protractor.Key.ENTER).perform();
        await page.getFinalNodeSelect().click();
        browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
        browser.actions().sendKeys(protractor.Key.ENTER).perform();
        let form = await page.getForm().getAttribute('class');
        expect(form).toContain('ng-valid');

    })

    it('Textbox values should be invalid', async () => {
        await page.getKeyTextbox().sendKeys('');

        let form = await page.getForm().getAttribute('class');
        expect(form).toContain('ng-invalid');
    })
});