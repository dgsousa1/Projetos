import { CreateDriverTypePage } from './create-driver-type.po';
import { browser, logging } from 'protractor';

describe('Driver Type', () => {
  let page: CreateDriverTypePage;

  beforeEach(async () => {
    page = new CreateDriverTypePage();
    await page.navigateTo();
  });

  it('Textbox values should be valid', async () => {
    await page.getNameTextbox().sendKeys('AutocarroTeste1');
    await page.getDescriptionTextbox().sendKeys('Condutor de autocarro de teste');

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-valid');

  })

  it('Textbox values should be invalid', async () => {
    await page.getNameTextbox().sendKeys('');
    await page.getDescriptionTextbox().sendKeys('');
    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-invalid');

  })


});