package com.tunyk.currencyconverter.api;

public interface CurrencyConverter {

    public Float convertCurrency(Float moneyAmount) throws CurrencyConverterException;
    public Float convertCurrency(Float moneyAmount, Currency toCurrency) throws CurrencyConverterException;
    public Float convertCurrency(Float moneyAmount, Currency fromCurrency, Currency toCurrency) throws CurrencyConverterException;

    public Currency getFromCurrency();
    public void setFromCurrency(Currency fromCurrency);
    public Currency getToCurrency();
    public void setToCurrency(Currency toCurrency);
}
