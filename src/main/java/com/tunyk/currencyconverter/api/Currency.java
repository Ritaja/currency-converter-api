package com.tunyk.currencyconverter.api;

public enum Currency {
    UAH("Ukrainian Hryvnia", "₴", "&#8372;", "грн."),
    AUD("Australian Dollar", "AUD", "AUD", "AUD"),
    AZM("Azerbaijan New Manat", "AZM", "AZM", "AZM"),
    GBP("United Kingdom Pound", "GBP", "GBP", "GBP"),
    BYR("Belarus Ruble", "BYR", "BYR", "BYR"),
    DKK("Denmark Krone", "DKK", "DKK", "DKK"),
    USD("United States Dollar", "$", "&#36;", "$"),
    EUR("Euro", "€", "&#8364;", "€"),
    ISK("Iceland Krona", "ISK", "ISK", "ISK"),
    KZT("Kazakhstan Tenge", "KZT", "KZT", "KZT"),
    CAD("Canada Dollar", "CAD", "CAD", "CAD"),
    MDL("Moldovan Leu", "MDL", "MDL", "MDL"),
    NOK("Norwegian Krone", "NOK", "NOK", "NOK"),
    PLN("Polish Zloty", "PLN", "PLN", "PLN"),
    RUB("Russian Ruble", "руб.", "руб.", "руб."),
    SGD("Singapore Dollar", "SGD", "SGD", "SGD"),
    XDR("Special Drawing Rights", "XDR", "XDR", "XDR"),
    TRL("Turkish Lira", "TRL", "TRL", "TRL"),
    TMM("Turkmenistan Manat", "TMM", "TMM", "TMM"),
    HUF("Hungary Forint ", "HUF", "HUF", "HUF"),
    UZS("Uzbekistan Som", "UZS", "UZS", "UZS"),
    CZK("Czech Republic Koruna", "CZK", "CZK", "CZK"),
    SEK("Swedish Krona", "SEK", "SEK", "SEK"),
    CHF("Switzerland Franc ", "CHF", "CHF", "CHF"),
    CNY("Chinese Yuan", "¥", "&#165;", "¥"),
    JPY("Japanese Yen", "JPY", "JPY", "JPY");


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
