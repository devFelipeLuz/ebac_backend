package br.com.feluz.factories;

import br.com.feluz.products.ISedan;
import br.com.feluz.products.ISports;
import br.com.feluz.products.Toyota.ToyotaSedanCar;
import br.com.feluz.products.Toyota.ToyotaSportsCar;

public class ToyotaCarFactory implements ICarFactory {

    @Override
    public ISedan createSedanCar() {
        return new ToyotaSedanCar();
    }

    @Override
    public ISports createSportsCar() {
        return new ToyotaSportsCar();
    }
}
