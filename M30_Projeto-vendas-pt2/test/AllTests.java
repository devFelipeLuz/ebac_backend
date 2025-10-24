
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({CostumerServiceTest.class, CostumerDAOTest.class, ProductServiceTest.class, ProductDAOTest.class, SaleDAOTest.class, InventoryDAOTest.class, InventoryServiceTest.class, ProductQuantityDAOTest.class})
public class AllTests {


}
