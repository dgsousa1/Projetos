import { CreateDriverPage } from './create-driver.po';
import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';

describe('Driver', () => {
  let page: CreateDriverPage;

  beforeEach(async () => {
    page = new CreateDriverPage();
    await page.navigateTo();
  });

  it('Textbox values should be valid', async () => {
    await page.getMecNumberTextbox().sendKeys('123465798');
    await page.getNameTextbox().sendKeys('DriverTeste');
    await page.getBirthDateTextbox().sendKeys('10/10/2020');
    await page.getCCTextbox().sendKeys('12345678');
    await page.getNifTextbox().sendKeys('123456789');
    await page.getDriverTypeTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await page.getStartDateCompanyTextbox().sendKeys('10/10/2020');
    await page.getFinalDateCompanyTextbox().sendKeys('11/10/2020');
    await page.getLicenseNumberTextbox().sendKeys('123456789123');
    await page.getLicenseDateTextbox().sendKeys('11/10/2020');

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-valid');
  })

  
  it('Textbox values should be invalid', async () => {
    await page.getMecNumberTextbox().sendKeys('');
    await page.getNameTextbox().sendKeys('');
    await page.getBirthDateTextbox().sendKeys('');
    await page.getCCTextbox().sendKeys('');
    await page.getNifTextbox().sendKeys('');
    await page.getDriverTypeTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await page.getStartDateCompanyTextbox().sendKeys('');
    await page.getFinalDateCompanyTextbox().sendKeys('');
    await page.getLicenseNumberTextbox().sendKeys('');
    await page.getLicenseDateTextbox().sendKeys('');

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-invalid');
  })
});