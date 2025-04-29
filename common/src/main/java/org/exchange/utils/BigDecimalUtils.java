package org.exchange.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {

    // 辅助方法，将字符串转换为 BigDecimal，处理空值
    public static BigDecimal parseBigDecimal(String value) {
        if (value == null || value.isEmpty()) {
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            return BigDecimal.ZERO;
        }
    }

}
