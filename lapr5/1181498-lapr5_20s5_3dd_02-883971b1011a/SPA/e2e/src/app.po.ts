import { browser, by, element } from 'protractor';

export class AppPage {
  async navigateTo(): Promise<unknown> {
    //return browser.get(browser.baseUrl);
    return browser.get('/');
  }

  async getTitleText(): Promise<string> {
    return element(by.css('app-navbar')).getText();
  }
}
