package coincost.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import coincost.CoinCost;
import coincost.AllPaymentCalc;
import coincost.Wallet;


public class AllPaymentCalcUnitTests {

    @Test
    public void paymentsTest1() {
        CoinCost cc = new CoinCost(
            new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 2);
                    put(new BigDecimal("0.50"), 4);
                    put(new BigDecimal("0.10"), 6);
                }}
            ), 
            new BigDecimal("2.60")
        );
        AllPaymentCalc apc = new AllPaymentCalc(cc);

        Set<Wallet> expected = new HashSet<Wallet>() {{
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 2);
                    put(new BigDecimal("0.50"), 1);
                    put(new BigDecimal("0.10"), 1);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 2);
                    put(new BigDecimal("0.50"), 0);
                    put(new BigDecimal("0.10"), 6);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 1);
                    put(new BigDecimal("0.50"), 3);
                    put(new BigDecimal("0.10"), 1);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 1);
                    put(new BigDecimal("0.50"), 2);
                    put(new BigDecimal("0.10"), 6);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 0);
                    put(new BigDecimal("0.50"), 4);
                    put(new BigDecimal("0.10"), 6);
                }}
            ));
        }};

        Set<Wallet> actual = apc.payments();

        assertEquals(expected, actual);
    }

    @Test
    public void paymentsTest2() {
        CoinCost cc = new CoinCost(
            new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 2);
                    put(new BigDecimal("0.50"), 4);
                    put(new BigDecimal("0.10"), 6);
                }}
            ), 
            new BigDecimal("2.59")
        );
        AllPaymentCalc apc = new AllPaymentCalc(cc);

        Set<Wallet> expected = new HashSet<Wallet>() {{
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 2);
                    put(new BigDecimal("0.50"), 1);
                    put(new BigDecimal("0.10"), 1);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 2);
                    put(new BigDecimal("0.50"), 0);
                    put(new BigDecimal("0.10"), 6);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 1);
                    put(new BigDecimal("0.50"), 3);
                    put(new BigDecimal("0.10"), 1);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 1);
                    put(new BigDecimal("0.50"), 2);
                    put(new BigDecimal("0.10"), 6);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 0);
                    put(new BigDecimal("0.50"), 4);
                    put(new BigDecimal("0.10"), 6);
                }}
            ));
        }};

        Set<Wallet> actual = apc.payments();

        assertEquals(expected, actual);
    }

    @Test
    public void paymentsTest3() {
        CoinCost cc = new CoinCost(
            new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 2);
                    put(new BigDecimal("0.50"), 4);
                    put(new BigDecimal("0.10"), 6);
                }}
            ), 
            new BigDecimal("4.61")
        );
        AllPaymentCalc apc = new AllPaymentCalc(cc);

        Set<Wallet> actual = apc.payments();

        assertTrue(actual.isEmpty());
    }

    @Test
    public void paymentsTest4() {
        CoinCost cc = new CoinCost(
            new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 2);
                    put(new BigDecimal("0.50"), 6);
                    put(new BigDecimal("0.20"), 1);
                    put(new BigDecimal("0.10"), 3);
                    put(new BigDecimal("0.01"), 1);
                }}
            ),
            new BigDecimal("2.62")
        );
        AllPaymentCalc apc = new AllPaymentCalc(cc);

        Set<Wallet> expected = new HashSet<Wallet>() {{
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 2);
                    put(new BigDecimal("0.50"), 1);
                    put(new BigDecimal("0.20"), 0);
                    put(new BigDecimal("0.10"), 2);
                    put(new BigDecimal("0.01"), 1);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 1);
                    put(new BigDecimal("0.50"), 3);
                    put(new BigDecimal("0.20"), 0);
                    put(new BigDecimal("0.10"), 2);
                    put(new BigDecimal("0.01"), 1);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 0);
                    put(new BigDecimal("0.50"), 5);
                    put(new BigDecimal("0.20"), 0);
                    put(new BigDecimal("0.10"), 2);
                    put(new BigDecimal("0.01"), 1);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 0);
                    put(new BigDecimal("0.50"), 5);
                    put(new BigDecimal("0.20"), 1);
                    put(new BigDecimal("0.10"), 0);
                    put(new BigDecimal("0.01"), 1);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 1);
                    put(new BigDecimal("0.50"), 3);
                    put(new BigDecimal("0.20"), 1);
                    put(new BigDecimal("0.10"), 0);
                    put(new BigDecimal("0.01"), 1);
                }}
            ));
            add(new Wallet(
                new HashMap<BigDecimal, Integer>() {{
                    put(new BigDecimal("1.00"), 2);
                    put(new BigDecimal("0.50"), 1);
                    put(new BigDecimal("0.20"), 1);
                    put(new BigDecimal("0.10"), 0);
                    put(new BigDecimal("0.01"), 1);
                }}
            ));
        }};

        Set<Wallet> actual = apc.payments();

        assertEquals(expected, actual);
    }

}
