import java.util.ArrayList;
import java.util.List;

public class Pessoa {
    private String name;
    private String gender;

    public Pessoa(String name, String gender) {
        this.name = name;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public String toString() {
        return name + "(" + gender + ")";
    }
}
