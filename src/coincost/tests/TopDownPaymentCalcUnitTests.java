package coincost.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import coincost.CoinCost;
import coincost.CoinPile;
import coincost.TopDownPaymentCalc;

public class TopDownPaymentCalcUnitTests {
    private CoinCost cc = new CoinCost(
        new CoinPile(Map.of(
            new BigDecimal("1.00"), 1,
            new BigDecimal("0.50"), 5,
            new BigDecimal("0.20"), 2,
            new BigDecimal("0.10"), 3,
            new BigDecimal("0.05"), 2,
            new BigDecimal("0.02"), 4,
            new BigDecimal("0.01"), 2
        )), 
        new BigDecimal("2.59")
    );
    private TopDownPaymentCalc tdpc = new TopDownPaymentCalc(cc);

    // @Test
    // public void startTest() {
    //     CoinPile expected = new CoinPile(
    //         Map.of(
    //             new BigDecimal("1.00"), 1,
    //             new BigDecimal("0.50"), 3,
    //             new BigDecimal("0.05"), 1,
    //             new BigDecimal("0.02"), 2
    //         )
    //     );
    
    //     CoinPile actual = tdpc.start(cc.getWallet().descendingKeySet().iterator());

    //     assertEquals(expected.toString(), actual.toString());
    // }

    @Test
    public void paymentsTest() {
        List<CoinPile> expected = List.of(
            new CoinPile(
                Map.of(
                    new BigDecimal("1.00"), 1,
                    new BigDecimal("0.50"), 3,
                    new BigDecimal("0.05"), 1,
                    new BigDecimal("0.02"), 2
                )
            ),
            new CoinPile(
                Map.of(
                    new BigDecimal("0.50"), 5,
                    new BigDecimal("0.05"), 1,
                    new BigDecimal("0.02"), 2
                )
            ),
            new CoinPile(
                Map.of(
                    new BigDecimal("0.50"), 4,
                    new BigDecimal("0.20"), 2,
                    new BigDecimal("0.10"), 1,
                    new BigDecimal("0.05"), 1,
                    new BigDecimal("0.02"), 2
                )
            ),
            new CoinPile(
                Map.of(
                    new BigDecimal("0.50"), 4,
                    new BigDecimal("0.20"), 1,
                    new BigDecimal("0.10"), 3,
                    new BigDecimal("0.05"), 1,
                    new BigDecimal("0.02"), 2
                )
            ),
            new CoinPile(
                Map.of(
                    new BigDecimal("0.50"), 4,
                    new BigDecimal("0.20"), 1,
                    new BigDecimal("0.10"), 2,
                    new BigDecimal("0.05"), 2,
                    new BigDecimal("0.02"), 4,
                    new BigDecimal("0.01"), 1
                )
            )
        );

        List<CoinPile> actual = tdpc.payments();

        assertEquals(expected.toString(), actual.toString());
    }

}
