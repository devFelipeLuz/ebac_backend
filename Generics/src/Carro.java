public abstract class Carro {
    private Integer id;
    private String model;
    private String color;
    private Integer year;
    private String fabricante = this.getClass().getSimpleName();

    public Carro(Integer id, String model, String color, Integer year) {
        this.id = id;
        this.color = color;
        this.model = model;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Carro{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", fabricante='" + fabricante + '\'' +
                '}';
    }

    public void printInfo() {
        System.out.println();
        System.out.println("Fabricante: " + fabricante);
        System.out.println("Modelo: " + model);
        System.out.println("Cor: " + color);
        System.out.println("Ano: " + year);
    }
}
