package coincost.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import coincost.CoinCost;
import coincost.AllPaymentCalc;
import coincost.Wallet;


public class AllPaymentCalcUnitTests {
    private CoinCost cc = new CoinCost(
        new Wallet(Map.of(
            new BigDecimal("1.00"), 2,
            new BigDecimal("0.50"), 4,
            new BigDecimal("0.10"), 6
        )), 
        new BigDecimal("2.60")
    );
    private AllPaymentCalc apc = new AllPaymentCalc(cc);

    @Test
    public void paymentsTest() {
        Set<Wallet> expected = Set.of(
            new Wallet(
                Map.of(
                    new BigDecimal("1.00"), 2,
                    new BigDecimal("0.50"), 1,
                    new BigDecimal("0.10"), 1
                )
            ),
            new Wallet(
                Map.of(
                    new BigDecimal("1.00"), 2,
                    new BigDecimal("0.50"), 0,
                    new BigDecimal("0.10"), 6
                )
            ),
            new Wallet(
                Map.of(
                    new BigDecimal("1.00"), 1,
                    new BigDecimal("0.50"), 3,
                    new BigDecimal("0.10"), 1
                )
            ),
            new Wallet(
                Map.of(
                    new BigDecimal("1.00"), 1,
                    new BigDecimal("0.50"), 2,
                    new BigDecimal("0.10"), 6
                )
            ),
            new Wallet(
                Map.of(
                    new BigDecimal("1.00"), 0,
                    new BigDecimal("0.50"), 4,
                    new BigDecimal("0.10"), 6
                )
            )
        );

        Set<Wallet> actual = apc.payments();

        assertEquals(expected, actual);
    }

}
