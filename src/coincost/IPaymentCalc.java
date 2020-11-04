package coincost;

import java.util.Iterator;

public interface IPaymentCalc {
    Iterator<CoinPile> payments();
}
