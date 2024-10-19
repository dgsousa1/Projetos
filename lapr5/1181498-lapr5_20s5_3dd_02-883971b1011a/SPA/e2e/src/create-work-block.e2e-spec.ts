import { CreateWorkBlockPage } from './create-work-block.po';
import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';

describe('WorkBlock', () => {
  let page: CreateWorkBlockPage;

  beforeEach(async () => {
    page = new CreateWorkBlockPage();
    await page.navigateTo();
  });
  /*
  it('Textbox values should be valid', async () => {
    await page.getKeyTextbox().sendKeys('1');
    await page.getStartTimeCompanyTextbox().sendKeys('10/10/2020 10:00');
    await page.getEndTimeCompanyTextbox().sendKeys('11/10/2020 10:00');
    await page.getTripTextbox().click();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-valid');
  })*/

  
  it('Textbox values should be invalid', async () => {
    await page.getKeyTextbox().sendKeys('');
    await page.getStartTimeCompanyTextbox().sendKeys('');
    await page.getEndTimeCompanyTextbox().sendKeys('');
    await page.getTripTextbox().click();

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-invalid');
  })
});