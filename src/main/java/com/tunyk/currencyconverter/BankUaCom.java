package com.tunyk.currencyconverter;

import com.tunyk.currencyconverter.api.Currency;
import com.tunyk.currencyconverter.api.CurrencyConverterException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class BankUaCom extends AbstractCurrencyConverter {

    private static final int DEFAULT_DP = 2;
    private static final int DPX = 10;
    private static final String BASE_CURRENCY_RATES_CACHE_KEY = "BankUaCom_BaseCurrencyRates";
    private static final String RATES_URL = "currencyconverter.bankuacom.ratesUrl";

    private Document currencyRatesDocument;
    private Map<String, Map> baseCurrencyRates;

    private XPath xpath = XPathFactory.newInstance().newXPath();

    @SuppressWarnings("unchecked")
    public BankUaCom(Currency fromCurrency, Currency toCurrency) throws CurrencyConverterException {
        super(Currency.UAH, fromCurrency, toCurrency);
        try {
            String uri = this.getProperties().getProperty(RATES_URL);
            if (cacheElementExpired(uri)) {
                URL url = new URL(uri);
                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                domFactory.setNamespaceAware(true);
                DocumentBuilder builder = domFactory.newDocumentBuilder();
                this.currencyRatesDocument = builder.parse(url.openStream());
                putCacheElement(uri, this.currencyRatesDocument);

                this.baseCurrencyRates = new HashMap<String, Map>();
                putCacheElement(BASE_CURRENCY_RATES_CACHE_KEY, this.baseCurrencyRates);
            } else {
                this.currencyRatesDocument = (Document) getCacheElement(uri);
                // TODO: fix unchecked warning
                this.baseCurrencyRates = (Map<String, Map>) getCacheElement(BASE_CURRENCY_RATES_CACHE_KEY);
            }
        } catch (MalformedURLException e) {
            throw new CurrencyConverterException(e);
        } catch (ParserConfigurationException e) {
            throw new CurrencyConverterException(e);
        } catch (IOException e) {
            throw new CurrencyConverterException(e);
        } catch (SAXException e) {
            throw new CurrencyConverterException(e);
        }
    }

    @SuppressWarnings("unchecked")
    protected Float convertToBaseCurrency(Float moneyAmount, Currency fromCurrency) throws CurrencyConverterException {
        if (this.getBaseCurrency().equals(fromCurrency)) {
            return moneyAmount;
        }
        try {
            Float size = null;
            Float rate = null;

            if (!this.baseCurrencyRates.containsKey(fromCurrency.toString())) {
                XPathExpression sizeExpr = this.xpath.compile("/chapter/item[char3=\"" + fromCurrency + "\"]/size/text()");
                NodeList nodes = (NodeList) sizeExpr.evaluate(this.currencyRatesDocument, XPathConstants.NODESET);
                if (nodes.getLength() > 0) {
                    size = new Float(nodes.item(0).getNodeValue());
                }

                XPathExpression rateExpr = this.xpath.compile("/chapter/item[char3=\"" + fromCurrency + "\"]/rate/text()");
                nodes = (NodeList) rateExpr.evaluate(this.currencyRatesDocument, XPathConstants.NODESET);
                if (nodes.getLength() > 0) {
                    rate = new Float(nodes.item(0).getNodeValue());
                }

                Map<String, Float> fromCurrencyMap = new HashMap<String, Float>();
                fromCurrencyMap.put("size", size);
                fromCurrencyMap.put("rate", rate);
                this.baseCurrencyRates.put(fromCurrency.toString(), fromCurrencyMap);
            } else {
                // TODO: fix unchecked warning
                Map<String, Float> fromCurrencyMap = (Map<String, Float>) this.baseCurrencyRates.get(fromCurrency.toString());
                size = fromCurrencyMap.get("size");
                rate = fromCurrencyMap.get("rate");
            }

            if (rate == null) {
                throw new CurrencyConverterException("Unable to parse currency rate from XML");
            }
            if (size == null) {
                throw new CurrencyConverterException("Unable to parse currency size from XML");
            }

            return (rate/size)*moneyAmount;
        } catch(XPathExpressionException e) {
            throw new CurrencyConverterException(e);
        }
    }

    public Float convertCurrency(Float moneyAmount) throws CurrencyConverterException {
        return this.convertCurrency(moneyAmount, this.getFromCurrency(), this.getToCurrency());
    }

    public Float convertCurrency(Float moneyAmount, Currency toCurrency) throws CurrencyConverterException {
        return this.convertCurrency(moneyAmount, this.getFromCurrency(), toCurrency);
    }

    public Float convertCurrency(Float moneyAmount, Currency fromCurrency, Currency toCurrency) throws CurrencyConverterException {
        if (fromCurrency.equals(toCurrency)) {
            return moneyAmount;
        }

        if (this.getBaseCurrency().equals(toCurrency)) {
            return this.round(this.convertToBaseCurrency(moneyAmount, fromCurrency));
        }

        Float from = this.convertToBaseCurrency(1f, fromCurrency);
        Float to = this.convertToBaseCurrency(1f, toCurrency);

        return this.round((from/to)*moneyAmount);
    }

    private Float round(Float moneyAmount) {
        return this.round(moneyAmount, DEFAULT_DP);
    }

    private Float round(Float moneyAmount, int decimalPlaces) {
        int dp;
        if (decimalPlaces < 0) {
            // do not round
            dp = 0;
        } else if (decimalPlaces == 0) {
            // no significant digits
            dp = 1;
        } else {
            // round price for specified amount of significant digits
            dp = (int) Math.pow(DPX, decimalPlaces);
        }

        if (dp == 0) {
            return moneyAmount;
        } else {
            int priceInt = Math.round(moneyAmount * dp);
            return (float) priceInt / dp;
        }
    }
}
