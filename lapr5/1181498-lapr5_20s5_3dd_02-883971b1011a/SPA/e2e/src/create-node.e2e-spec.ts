import { CreateNodePage } from './create-node.po';
import { browser, logging } from 'protractor';

describe('Create Node', () => {
    let page: CreateNodePage;

    beforeEach(async () => {
        page = new CreateNodePage();
        await page.navigateTo();
    });

    it('Textbox values should be valid', async () => {
        await page.getNameTextbox().sendKeys('SPATeste1');
        await page.getShortNameTextbox().sendKeys('SPAT1');
        await page.getLatitudeTextbox().sendKeys('41');
        await page.getLongitudeTextbox().sendKeys('-8');
        await page.getDepotCheckbox().click();
        await page.getDepotCheckbox().click();
        let form = await page.getForm().getAttribute('class');
        expect(form).toContain('ng-valid');

    })

    it('Textbox values should be invalid', async () => {
        await page.getNameTextbox().sendKeys('');
        await page.getShortNameTextbox().sendKeys('');
        await page.getLatitudeTextbox().sendKeys('');
        await page.getLongitudeTextbox().sendKeys('');
        await page.getDepotCheckbox().click();

        let form = await page.getForm().getAttribute('class');
        expect(form).toContain('ng-invalid');
    })


});