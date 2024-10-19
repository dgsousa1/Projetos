import { browser, by, element } from 'protractor';
export class CreateLinePage {
  navigateTo() {
    return browser.get('/create-line');
  }
  getNameTextbox() {
    //return element(by.name('name'));
    //return element(by.css('.divName name'));
    return element(by.name('name'));
  }

  getColorTextbox() {
    return element(by.name('color'));
  }

  getGoTextbox() {
    //  return element(by.name('pathGo'));
    return element(by.id('go'));

  }

  getReturnTextbox() {
    //   return element(by.name('pathReturn'));
    return element(by.id('return'));

  }


  getForm() {
    return element(by.css('#lineForm'));
  }

  getRemoveButton() {
    return element(by.css('.divPath #removeBtn'));
  }

  getAddButton() {
    return element(by.css('.divPath #addBtn'));
  }

  getVehicleTypeSelect() {
    //    return element(by.name('vehicleType'));
    return element(by.id('vehicleTypeId'));
  }

  getDriverTypeTextbox() {
    //   return element(by.name('driverType'));
    return element(by.id('driverTypeId'));


  }

  getSubmitButton() {
    return element(by.css('#btnSubmit'));
  }
}