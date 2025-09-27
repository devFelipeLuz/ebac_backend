package br.com.feluz;

import br.com.feluz.factories.ICarFactory;
import br.com.feluz.factories.MitsubishiCarFactory;
import br.com.feluz.factories.ToyotaCarFactory;
import br.com.feluz.products.ISedan;
import br.com.feluz.products.ISports;

public class Main {

    public static void main(String[] args) {
        ICarFactory mitsubishiFactory = new MitsubishiCarFactory();
        ICarFactory toyotaFactory = new ToyotaCarFactory();

        ISedan mitsubishiSedan = mitsubishiFactory.createSedanCar();
        ISedan toyotaSedan = toyotaFactory.createSedanCar();

        ISports mitsubishiSport = mitsubishiFactory.createSportsCar();
        ISports toyotaSport = toyotaFactory.createSportsCar();

        mitsubishiSedan.makeSound();
        mitsubishiSport.makeSound();
        toyotaSedan.makeSound();
        toyotaSport.makeSound();
    }
}
