import { browser, by, element } from 'protractor';
export class CreateVehicleTypePage {
    navigateTo() {
        return browser.get('/create-vehicle-type');
    }
    getNameTextbox() {
        return element(by.name('name'));
    }

    getDescriptionTextbox() {
        return element(by.name('description'));
    }

    getFuelTypeSelectOption() {
        return element(by.id('fuelType'));
    }

    getAutonomyTextboxNumber() {
        return element(by.name('autonomy'));
    }

    getCostPerKilometerTextboxNumber() {
        return element(by.name('costPerKilometer'));
    }

    getConsumptionTextboxNumber() {
        return element(by.name('consumption'));
    }

    getAverageSpeedTextboxNumber() {
        return element(by.name('averageSpeed'));
    }

    getEmissionTextboxNumber() {
        return element(by.name('emission'));
    }

    getForm() {
        return element(by.css('#vehicleTypeForm'));
    }

    getSubmitButton() {
        return element(by.css('#btnSubmit'));
    }

}