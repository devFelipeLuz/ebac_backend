package br.com.feluz.products.Toyota;

import br.com.feluz.products.ISports;

public class ToyotaSportsCar implements ISports {
    @Override
    public void makeSound() {
        System.out.println("Toyota Esporte: vruuuuuuuuum!");
    }
}
