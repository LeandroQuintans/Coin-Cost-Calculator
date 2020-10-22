package coincost;

import java.util.Iterator;

public interface IPaymentCalculator {
    Iterator<CoinStockpile[]> payments(CoinStockpile[] wallet);
}
