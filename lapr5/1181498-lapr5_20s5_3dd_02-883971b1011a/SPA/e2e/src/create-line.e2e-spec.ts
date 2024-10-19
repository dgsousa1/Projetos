import { CreateLinePage } from './create-line.po';
import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';

describe('Create Line', () => {
    let page: CreateLinePage;

    beforeEach(async () => {
        page = new CreateLinePage();
        await page.navigateTo();
    });

    it('Textbox values should be valid', async () => {
        await page.getGoTextbox().click();
        browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
        browser.actions().sendKeys(protractor.Key.ENTER).perform();
        await page.getReturnTextbox().click()
        browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
        browser.actions().sendKeys(protractor.Key.ENTER).perform();
        await page.getNameTextbox().sendKeys('LinhaSPAT1');
        await page.getColorTextbox().sendKeys('#FFFFFF');
        await page.getVehicleTypeSelect().click();
        browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
        browser.actions().sendKeys(protractor.Key.ENTER).perform();
        await page.getDriverTypeTextbox().click();
        browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
        browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
        browser.actions().sendKeys(protractor.Key.ENTER).perform();

        let form = await page.getForm().getAttribute('class');
        expect(form).toContain('ng-valid');

    })

    it('Textbox values should be invalid', async () => {
        await page.getNameTextbox().sendKeys('');
        await page.getColorTextbox().sendKeys('');
        await page.getGoTextbox().sendKeys('');
        await page.getReturnTextbox().sendKeys('');
        await page.getVehicleTypeSelect().sendKeys('');
        await page.getDriverTypeTextbox().sendKeys('');
    
        let form = await page.getForm().getAttribute('class');
        expect(form).toContain('ng-invalid');

    })
});