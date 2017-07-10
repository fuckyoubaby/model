package com.changhong.common.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * User: Jack Wang
 * Date: 15-12-1
 * Time: 下午5:11
 */
public class MoneyUtils {

    public static String getPriceStr(double price) {
        DecimalFormat fnum = new DecimalFormat("#0.00");
        return fnum.format(price);
    }

    public static double toFormatMoney(double totalMoney) {
        BigDecimal b = new BigDecimal(totalMoney);
        return b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }
}
