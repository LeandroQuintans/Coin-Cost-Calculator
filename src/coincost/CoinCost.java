package coincost;

import java.math.BigDecimal;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class CoinCost {
    private CoinPile wallet;
    private BigDecimal cost;

    public CoinCost(CoinPile wallet, BigDecimal cost) {
        this.wallet = new CoinPile(wallet);
        this.cost = new BigDecimal(cost.toString());
    }

    public CoinPile getWallet() {
        return wallet;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Set<CoinPile> payments() {
        return new TopDownPaymentCalc(this).payments();
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();

        sBuilder.append(wallet.toString());

        sBuilder.append(cost);

        return sBuilder.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] coinPileStrings = sc.nextLine().split(" ");
        BigDecimal cost = new BigDecimal(sc.next());
        sc.close();
        
        Pattern pattern = Pattern.compile("\\((\\d+.\\d+|\\d+),(\\d+)\\)");
        
        CoinPile coinPiles = new CoinPile();
        for (String coinPileString : coinPileStrings) {
            Matcher matcher = pattern.matcher(coinPileString);
            matcher.matches();
            BigDecimal value = new BigDecimal(matcher.group(1));
            int amount = Integer.parseInt(matcher.group(2));

            coinPiles.put(value, amount);
        }

        CoinCost coinCost = new CoinCost(coinPiles, cost);
        Set<CoinPile> payments = coinCost.payments();
        for (CoinPile payment : payments)
            System.out.println(payment);
    }
}
