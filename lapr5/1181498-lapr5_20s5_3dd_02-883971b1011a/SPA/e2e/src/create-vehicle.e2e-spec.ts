import { CreateVehiclePage } from './create-vehicle.po';
import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';

describe('Vehicle', () => {
  let page: CreateVehiclePage;

  beforeEach(async () => {
    page = new CreateVehiclePage();
    await page.navigateTo();
  });

  it('Textbox values should be valid', async () => {
    await page.getLicensePlateTextbox().sendKeys('AA-00-00');
    await page.getVINTextbox().sendKeys('12345678913245678');
    await page.getVehicleTypeTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await page.getStartDateCompanyTextbox().sendKeys('10/10/2020');

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-valid');
  })

  
  it('Textbox values should be invalid', async () => {
    await page.getLicensePlateTextbox().sendKeys('');
    await page.getVINTextbox().sendKeys('');
    await page.getVehicleTypeTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await page.getStartDateCompanyTextbox().sendKeys('');

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-invalid');
  })
});