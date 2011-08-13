package com.tunyk.currencyconverter.api;

public enum Currency {
    UAH("Ukrainian Hryvnia", "₴", "&#8372;", "грн."),
    AUD("AUD", "AUD", "AUD", "AUD"),
    AZM("AZM", "AZM", "AZM", "AZM"),
    GBP("GBP", "GBP", "GBP", "GBP"),
    BYR("BYR", "BYR", "BYR", "BYR"),
    DKK("DKK", "DKK", "DKK", "DKK"),
    USD("United States Dollar", "$", "&#36;", "$"),
    EUR("Euro", "€", "&#8364;", "€"),
    ISK("ISK", "ISK", "ISK", "ISK"),
    KZT("KZT", "KZT", "KZT", "KZT"),
    CAD("CAD", "CAD", "CAD", "CAD"),
    LVL("LVL", "LVL", "LVL", "LVL"),
    LTL("LTL", "LTL", "LTL", "LTL"),
    MDL("MDL", "MDL", "MDL", "MDL"),
    NOK("NOK", "NOK", "NOK", "NOK"),
    PLN("PLN", "PLN", "PLN", "PLN"),
    RUB("Russian Ruble", "руб.", "руб.", "руб."),
    SGD("SGD", "SGD", "SGD", "SGD"),
    XDR("XDR", "XDR", "XDR", "XDR"),
    TRL("TRL", "TRL", "TRL", "TRL"),
    TMM("TMM", "TMM", "TMM", "TMM"),
    HUF("HUF", "HUF", "HUF", "HUF"),
    UZS("UZS", "UZS", "UZS", "UZS"),
    CZK("CZK", "CZK", "CZK", "CZK"),
    SEK("SEK", "SEK", "SEK", "SEK"),
    CHF("CHF", "CHF", "CHF", "CHF"),
    CNY("Chinese Yuan", "¥", "&#165;", "¥"),
    JPY("JPY", "JPY", "JPY", "JPY");


    private String curName;
    private String curSign;
    private String curHtml;
    private String curAbbreviation;

    private Currency(String pName, String pSign, String pHtml, String pAbbreviation) {
        this.curName = pName;
        this.curSign = pSign;
        this.curHtml = pHtml;
        this.curAbbreviation = pAbbreviation;
    }

    public static Currency fromString(final String pCurrency) throws CurrencyNotSupportedException {
        for (Currency c : values()) {
            if (pCurrency.equalsIgnoreCase(c.toString())) {
                return c;
            }
        }
        throw new CurrencyNotSupportedException(new StringBuilder("Currency not supported: ").append(pCurrency).toString());
    }

    public String getFullName() {
        return this.curName;
    }

    public String getSign() {
        return this.curSign;
    }

    public String getHtml() {
        return this.curHtml;
    }

    public String getAbbreviation() {
        return this.curAbbreviation;
    }
}
