package coincost;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CoinCost {
    private CoinStockpile[] wallet;
    private BigDecimal cost;

    public CoinCost(CoinStockpile[] wallet, BigDecimal cost) {
        this.wallet = wallet.clone();
        this.cost = new BigDecimal(cost.toString());
    }

    public CoinStockpile[] getWallet() {
        return wallet;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Iterator<CoinStockpile[]> payments() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sBuilder = new StringBuilder();

        for (CoinStockpile pouch : wallet) {
            sBuilder.append(pouch.toString());
            sBuilder.append(' ');
        }

        sBuilder.append(cost);

        return sBuilder.toString();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] coinStockpileStrings = sc.nextLine().split(" ");
        BigDecimal cost = new BigDecimal(sc.next());
        sc.close();
        
        Pattern pattern = Pattern.compile("\\((\\d+.\\d+|\\d+),(\\d+)\\)");
        
        CoinStockpile[] coinStockpiles = new CoinStockpile[coinStockpileStrings.length];
        int i = 0;
        for (String coinStockpileString : coinStockpileStrings) {
            Matcher matcher = pattern.matcher(coinStockpileString);
            matcher.matches();
            String value = matcher.group(1);
            int amount = Integer.parseInt(matcher.group(2));

            coinStockpiles[i++] = new CoinStockpile(value, amount);
        }

        CoinCost coinCost = new CoinCost(coinStockpiles, cost);

        Iterator<CoinStockpile[]> payments = coinCost.payments();

        while (payments.hasNext()) {
            for (CoinStockpile coinStockpile : payments.next()) {
                System.out.print(coinStockpile.toString() + ' ');
            }
            System.out.println();
        }
    }
}
