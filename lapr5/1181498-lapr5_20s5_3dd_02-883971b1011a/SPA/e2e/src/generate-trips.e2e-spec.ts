/**import { GenerateTripPage } from './generate-trips.po';
import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';

describe('GenerateTrip', () => {
  let page: GenerateTripPage;

  beforeEach(async () => {
    page = new GenerateTripPage();
    await page.navigateTo();
  });

  it('Textbox values should be valid', async () => {
    await page.getLineTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await page.getStartDateCompanyTextbox().sendKeys('10/10/2020 10:00');
    await page.getFrequencyTextbox().sendKeys('00:30');
    await page.getNumberOfTripsTextbox().sendKeys('1');
    await page.getPathTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-valid');
  })

  
  it('Textbox values should be invalid', async () => {
    await page.getLineTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await page.getStartDateCompanyTextbox().sendKeys('');
    await page.getFrequencyTextbox().sendKeys('');
    await page.getNumberOfTripsTextbox().sendKeys('1');
    await page.getPathTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-invalid');
  })
});*/