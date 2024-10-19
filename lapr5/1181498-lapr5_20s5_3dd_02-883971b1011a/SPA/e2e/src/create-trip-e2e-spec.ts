import { CreateTripPage } from './create-trip.po';
import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';

describe('Trip', () => {
  let page: CreateTripPage;

  beforeEach(async () => {
    page = new CreateTripPage();
    await page.navigateTo();
  });

  it('Textbox values should be valid', async () => {
    await page.getTripNumberTextbox().sendKeys('1');
    await page.getStartDateCompanyTextbox().sendKeys('10/10/2020 10:00');
    await page.getLineTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await page.getPathTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-valid');
  })

  
  it('Textbox values should be invalid', async () => {
    await page.getTripNumberTextbox().sendKeys('');
    await page.getStartDateCompanyTextbox().sendKeys('');
    await page.getLineTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await page.getLineTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await page.getPathTextbox().click()
    browser.actions().sendKeys(protractor.Key.ARROW_DOWN).perform();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-invalid');
  })
});