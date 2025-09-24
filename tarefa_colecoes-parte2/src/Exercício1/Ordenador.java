package Exercício1;

public class Ordenador {

    public static void bubbleSortStrings(String[] arr) {
        String aux;
        for (int i = 0; i < arr.length; i++) {
            for (int index = 0; index < arr.length - 1; index++) {
                if (arr[index].compareTo(arr[index + 1]) > 0) {
                    aux = arr[index];
                    arr[index] = arr[index + 1];
                    arr[index + 1] = aux;
                }
            }
        }
    }
}
