package coincost;

import java.util.Iterator;

public interface IPaymentCalculator {
    Iterator<CoinPile> payments(CoinPile wallet);
}
