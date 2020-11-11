package coincost.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.Test;

import coincost.CoinCost;
import coincost.CoinPile;
import coincost.TopDownPaymentCalc;

public class TopDownPaymentCalcUnitTests {
    private CoinCost cc = new CoinCost(
        new CoinPile(Map.of(
            new BigDecimal("1.00"), 1,
            new BigDecimal("0.50"), 2,
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
    //             new BigDecimal("0.50"), 2,
    //             new BigDecimal("0.20"), 2,
    //             new BigDecimal("0.10"), 1,
    //             new BigDecimal("0.05"), 1,
    //             new BigDecimal("0.02"), 2
    //         )
    //     );
    
    //     CoinPile actual = tdpc.start(cc.getWallet().descendingKeySet().iterator());

    //     assertEquals(expected.toString(), actual.toString());
    // }

}
