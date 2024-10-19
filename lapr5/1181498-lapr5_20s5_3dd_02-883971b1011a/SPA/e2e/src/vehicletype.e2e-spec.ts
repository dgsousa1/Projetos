import { CreateVehicleTypePage } from './create-vehicle-type.po';
import { browser, logging } from 'protractor';


describe('Vehicle Type', () => {
  let page: CreateVehicleTypePage;

  beforeEach(async () => {
    page = new CreateVehicleTypePage();
    await page.navigateTo();
  });

  it('Textbox values should be valid', async () => {
    await page.getNameTextbox().sendKeys('Autocarro');
    await page.getDescriptionTextbox().sendKeys('Autocarro');
    await page.getFuelTypeSelectOption().sendKeys('GPL');
    await page.getAutonomyTextboxNumber().sendKeys('500000');
    await page.getCostPerKilometerTextboxNumber().sendKeys('10');
    await page.getConsumptionTextboxNumber().sendKeys('30');
    await page.getAverageSpeedTextboxNumber().sendKeys('30');
    await page.getEmissionTextboxNumber().sendKeys('1050');

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-valid');

  })

  it('Textbox values should be invalid', async () => {
    await page.getNameTextbox().sendKeys('');
    await page.getDescriptionTextbox().sendKeys('');
    await page.getFuelTypeSelectOption().sendKeys('');
    await page.getAutonomyTextboxNumber().sendKeys('');
    await page.getCostPerKilometerTextboxNumber().sendKeys('');
    await page.getConsumptionTextboxNumber().sendKeys('');
    await page.getAverageSpeedTextboxNumber().sendKeys('');
    await page.getEmissionTextboxNumber().sendKeys('');

    let form = await page.getForm().getAttribute('class');
    expect(form).toContain('ng-invalid');

  })


});