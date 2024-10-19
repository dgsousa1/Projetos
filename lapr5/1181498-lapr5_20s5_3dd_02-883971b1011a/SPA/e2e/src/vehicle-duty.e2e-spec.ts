import { CreateVehicleDutyPage } from './vehicle-duty.po';
import { browser, logging } from 'protractor';
import { protractor } from 'protractor/built/ptor';


describe('VehicleDuty', () => {
  let page: CreateVehicleDutyPage;

  beforeEach(async () => {
    page = new CreateVehicleDutyPage();
    await page.navigateTo();
  });
  /*
  it('Textbox values should be valid', async () => {
    await page.getCodeTextbox().sendKeys('1');
    await page.getWorkBlocksTextbox().click();
    browser.actions().sendKeys(protractor.Key.ENTER).perform();
    await page.getValidDateTextbox().sendKeys('10/10/2020 10:00');
    await page.getColorTextbox().sendKeys('#FFFFFF');

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-valid');
  })*/

  
  it('Textbox values should be invalid', async () => {
    await page.getCodeTextbox().sendKeys('');
    await page.getWorkBlocksTextbox().click();
    await page.getValidDateTextbox().sendKeys('');
    await page.getColorTextbox().sendKeys('');

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-invalid');
  })
});