package com.tunyk.currencyconverter;

import com.tunyk.currencyconverter.api.Currency;
import com.tunyk.currencyconverter.api.CurrencyConverter;
import com.tunyk.currencyconverter.api.CurrencyConverterException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class AbstractCurrencyConverter implements CurrencyConverter {

    private static final String CACHE_NAME = "currencyConverter";
    private static final String PROPERTIES_FILENAME = "currencyconverter.properties";

    private Properties properties;
    private Currency baseCurr;
    private Currency fromCurr;
    private Currency toCurr;
    private CacheManager cacheManager;

    public AbstractCurrencyConverter(Currency baseCurrency, Currency fromCurrency, Currency toCurrency)
            throws CurrencyConverterException {
        InputStream inputStream  = AbstractCurrencyConverter.class.getClassLoader().getResourceAsStream(PROPERTIES_FILENAME);
        this.properties = new Properties();
        try {
            this.properties.load(inputStream);
        } catch (IOException e) {
            throw new CurrencyConverterException(e);
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            throw new CurrencyConverterException(e);
        }

        this.fromCurr = fromCurrency;
        this.toCurr = toCurrency;
        this.baseCurr = baseCurrency;

        cacheManager = CacheManager.getInstance();
    }

    protected void putCacheElement(String key, Object value) {
        Element element = new Element(key, value);
        getCache().put(element);
    }

    protected Object getCacheElement(String key) {
        return getCache().get(key).getObjectValue();
    }

    protected void removeCacheElement(String key) {
        getCache().remove(key);
    }

    protected boolean cacheElementExists(String key) {
        return getCache().isKeyInCache(key);
    }

    protected boolean cacheElementExpired(String key) {
        Element element = getCache().get(key);
        if (element != null) {
            return getCache().isExpired(element);
        }
        return true;
    }

    protected void clearCache() throws CurrencyConverterException {
        getCache().removeAll();
    }

    protected Properties getProperties() {
        return this.properties;
    }

    protected Currency getBaseCurrency() {
        return this.baseCurr;
    }

    public Currency getFromCurrency() {
        return this.fromCurr;
    }

    public void setFromCurrency(Currency fromCurrency) {
        this.fromCurr = fromCurrency;
    }

    public Currency getToCurrency() {
        return this.toCurr;
    }

    public void setToCurrency(Currency toCurrency) {
        this.toCurr = toCurrency;
    }

    protected Cache getCache() {
        return cacheManager.getCache(CACHE_NAME);
    }
}
