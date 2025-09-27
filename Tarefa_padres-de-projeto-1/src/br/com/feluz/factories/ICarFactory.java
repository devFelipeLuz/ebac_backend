package br.com.feluz.factories;

import br.com.feluz.products.ISedan;
import br.com.feluz.products.ISports;

public interface ICarFactory {
    ISports createSportsCar();
    ISedan createSedanCar();
}
