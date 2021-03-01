package coincost.tests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;

import coincost.Wallet;
import coincost.exceptions.NegativeCoinAmountException;

public class WalletUnitTests {
    private Wallet wallet = new Wallet(Map.of(
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
        BigDecimal actual = wallet.getKeyTotal(new BigDecimal("0.50"));

        assertEquals(expected, actual);
    }

    @Test
    public void getKeyTotalTest2() {
        BigDecimal expected = new BigDecimal("0.10");
        BigDecimal actual = wallet.getKeyTotal(new BigDecimal("0.05"));

        assertEquals(expected, actual);
    }

    @Test
    public void getFullTotalTest() {
        BigDecimal expected = new BigDecimal("4.40");
        BigDecimal actual = wallet.getFullTotal();

        assertEquals(expected, actual);
    }

    @Test
    public void addAmountTest1() {
        Integer expected = 4;

        BigDecimal key = new BigDecimal("0.20");
        wallet.addAmount(key, 2);
        Integer actual = wallet.get(key);

        assertEquals(expected, actual);
    }

    @Test
    public void addAmountTest2() {
        Integer expected = 3;

        BigDecimal key = new BigDecimal("2.00");
        wallet.addAmount(key, 3);
        Integer actual = wallet.get(key);

        assertEquals(expected, actual);
    }

    @Test
    public void subAmountTest1() {
        Integer expected = 1;

        BigDecimal key = new BigDecimal("0.10");
        wallet.subAmount(key, 2);
        Integer actual = wallet.get(key);

        assertEquals(expected, actual);
    }

    @Test
    public void subAmountTest2() {
        BigDecimal key = new BigDecimal("0.30");
        
        assertThrows(NegativeCoinAmountException.class, () -> wallet.subAmount(key, 3));
    }

    @Test
    public void subAmountTest3() {
        BigDecimal key = new BigDecimal("0.20");
        
        assertThrows(NegativeCoinAmountException.class, () -> wallet.subAmount(key, 3));
    }

    @Test
    public void isPileEmptyTest1() {
        BigDecimal key = new BigDecimal("0.30");

        assertTrue(wallet.isPileEmpty(key));
    }

    @Test
    public void isPileEmptyTest2() {
        BigDecimal key = new BigDecimal("0.20");

        wallet.put(key, 0);

        assertTrue(wallet.isPileEmpty(key));
    }

    @Test
    public void isEquals1() {
        Wallet otherWallet = new Wallet(Map.of(
            new BigDecimal("1.00"), 1,
            new BigDecimal("0.50"), 5,
            new BigDecimal("0.20"), 2,
            new BigDecimal("0.10"), 3,
            new BigDecimal("0.05"), 2,
            new BigDecimal("0.02"), 4,
            new BigDecimal("0.01"), 2
        ));

        assertTrue(wallet.equals(otherWallet));
    }

    @Test
    public void subtract1() {
        Wallet thisWallet = new Wallet(Map.of(
            new BigDecimal("1.00"), 3,
            new BigDecimal("0.50"), 2,
            new BigDecimal("0.10"), 1
        ));
        Wallet otherWallet = new Wallet(Map.of(
            new BigDecimal("1.00"), 1,
            new BigDecimal("0.50"), 1,
            new BigDecimal("0.10"), 1
        ));
        Wallet expected = new Wallet(Map.of(
            new BigDecimal("1.00"), 2,
            new BigDecimal("0.50"), 1,
            new BigDecimal("0.10"), 0
        ));
        Wallet actual = thisWallet.subtract(otherWallet);

        assertEquals(expected, actual);
    }

    @Test
    public void subtract2() {
        Wallet thisWallet = new Wallet(Map.of(
            new BigDecimal("1.00"), 3,
            new BigDecimal("0.50"), 2,
            new BigDecimal("0.10"), 1
        ));
        Wallet otherWallet = new Wallet(Map.of(
            new BigDecimal("1.00"), 4,
            new BigDecimal("0.50"), 1,
            new BigDecimal("0.10"), 1
        ));

        assertThrows(NegativeCoinAmountException.class, () -> thisWallet.subtract(otherWallet));
    }

    @Test
    public void add() {
        Wallet thisWallet = new Wallet(Map.of(
            new BigDecimal("1.00"), 3,
            new BigDecimal("0.50"), 2,
            new BigDecimal("0.10"), 1
        ));
        Wallet otherWallet = new Wallet(Map.of(
            new BigDecimal("1.00"), 1,
            new BigDecimal("0.50"), 1,
            new BigDecimal("0.10"), 1,
            new BigDecimal("0.05"), 1
        ));
        Wallet expected = new Wallet(Map.of(
            new BigDecimal("1.00"), 4,
            new BigDecimal("0.50"), 3,
            new BigDecimal("0.10"), 2,
            new BigDecimal("0.05"), 1
        ));
        Wallet actual = thisWallet.add(otherWallet);

        assertEquals(expected, actual);
    }

}
