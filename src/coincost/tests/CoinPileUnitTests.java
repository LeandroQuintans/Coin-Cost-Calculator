package coincost.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;

import coincost.CoinPile;

public class CoinPileUnitTests {
    private CoinPile cp = new CoinPile(Map.of(
        new BigDecimal("1.00"), 1,
        new BigDecimal("0.50"), 5,
        new BigDecimal("0.20"), 2,
        new BigDecimal("0.10"), 3,
        new BigDecimal("0.05"), 2,
        new BigDecimal("0.02"), 4,
        new BigDecimal("0.01"), 2
    ));

    @Test
    public void getKeyTotalTest1() {
        BigDecimal expected = new BigDecimal("2.50");
        BigDecimal actual = cp.getKeyTotal(new BigDecimal("0.50"));

        assertEquals(expected, actual);
    }

    @Test
    public void getKeyTotalTest2() {
        BigDecimal expected = new BigDecimal("0.10");
        BigDecimal actual = cp.getKeyTotal(new BigDecimal("0.05"));

        assertEquals(expected, actual);
    }

    @Test
    public void getTotalTest() {
        BigDecimal expected = new BigDecimal("4.40");
        BigDecimal actual = cp.getTotal();

        assertEquals(expected, actual);
    }

    @Test
    public void addAmountTest1() {
        Integer expected = 4;

        BigDecimal key = new BigDecimal("0.20");
        cp.addAmount(key, 2);
        Integer actual = cp.get(key);

        assertEquals(expected, actual);
    }

    @Test
    public void addAmountTest2() {
        Integer expected = 3;

        BigDecimal key = new BigDecimal("2.00");
        cp.addAmount(key, 3);
        Integer actual = cp.get(key);

        assertEquals(expected, actual);
    }

    @Test
    public void subAmountTest1() {
        Integer expected = 1;

        BigDecimal key = new BigDecimal("0.10");
        cp.subAmount(key, 2);
        Integer actual = cp.get(key);

        assertEquals(expected, actual);
    }

    @Test
    public void subAmountTest2() {
        BigDecimal key = new BigDecimal("0.30");
        
        assertThrows(RuntimeException.class, () -> cp.subAmount(key, 3));
    }

    @Test
    public void subAmountTest3() {
        BigDecimal key = new BigDecimal("0.20");
        
        assertThrows(RuntimeException.class, () -> cp.subAmount(key, 3));
    }

    @Test
    public void isPileEmptyTest1() {
        BigDecimal key = new BigDecimal("0.30");

        assertTrue(cp.isPileEmpty(key));
    }

    @Test
    public void isPileEmptyTest2() {
        BigDecimal key = new BigDecimal("0.20");

        cp.put(key, 0);

        assertTrue(cp.isPileEmpty(key));
    }

}
