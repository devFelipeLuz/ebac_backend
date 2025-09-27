package br.com.feluz.products.Toyota;

import br.com.feluz.products.ISedan;

public class ToyotaSedanCar implements ISedan {
    @Override
    public void makeSound() {
        System.out.println("Toyota Sedan: vrum vrum!");
    }
}
