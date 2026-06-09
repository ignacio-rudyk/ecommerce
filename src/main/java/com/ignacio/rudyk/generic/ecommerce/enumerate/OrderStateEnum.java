package com.ignacio.rudyk.generic.ecommerce.enumerate;

public enum OrderStateEnum {

    FINALIZADA("001"),
    EN_PROCESO("002"),
    CANCELADA("003"),
    REEMBOLSADA("004");

    private String code;

    OrderStateEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }


}
