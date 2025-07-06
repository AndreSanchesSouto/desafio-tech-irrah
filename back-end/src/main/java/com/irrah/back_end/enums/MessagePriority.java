package com.irrah.back_end.enums;
import java.math.BigDecimal;

public enum MessagePriority {
    NORMAL("normal", new BigDecimal("0.25")),
    URGENT("urgent", new BigDecimal("0.50"));

    private final String type;
    private final BigDecimal price;

    MessagePriority(String type, BigDecimal price) {
        this.type = type;
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static MessagePriority fromType(String type) {
        for (MessagePriority p : values()) {
            if (p.type.equalsIgnoreCase(type)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Valor da propriedade inválida");
    }
}
