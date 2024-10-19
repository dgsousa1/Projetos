import { browser, by, element } from 'protractor';
export class CreateDriverTypePage {
  navigateTo() {
    return browser.get('/create-driver-type');
  }
  getNameTextbox() {
    return element(by.name('name'));
  }
 
  getDescriptionTextbox() {
      return element(by.name('description'));
  }

  getForm(){
    return element(by.css('#driverTypeForm'));
  }

  getSubmitButton(){
    return element(by.css('#btnSubmit'));
  }

}