import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;

public class MainTest {

    @Test
    public void deveFiltrarApenasMulheres() {
        List<String> pessoas = List.of("Ricardo - Masculino", "Debora - Feminino", "Lucas - Masculino", "Marcela - Feminino");

        List<String> resultado = Main.filtraMulheres(pessoas);

        assertEquals(2, resultado.size());
        assertTrue(resultado.contains("Debora - Feminino"));
        assertTrue(resultado.contains("Marcela - Feminino"));
    }
}