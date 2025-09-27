package br.com.feluz.factories;

import br.com.feluz.products.ISedan;
import br.com.feluz.products.ISports;
import br.com.feluz.products.Mitsubishi.MitsubishiSedanCar;
import br.com.feluz.products.Mitsubishi.MitsubishiSportsCar;

public class MitsubishiCarFactory implements ICarFactory {
    @Override
    public ISedan createSedanCar() {
        return new MitsubishiSedanCar();
    }

    @Override
    public ISports createSportsCar() {
        return new MitsubishiSportsCar();
    }
}
