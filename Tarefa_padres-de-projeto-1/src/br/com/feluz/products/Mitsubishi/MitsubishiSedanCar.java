package br.com.feluz.products.Mitsubishi;

import br.com.feluz.products.ISedan;

public class MitsubishiSedanCar implements ISedan {

    @Override
    public void makeSound() {
        System.out.println("Mitsubishi Sedan: vrum vrum");
    }
}
