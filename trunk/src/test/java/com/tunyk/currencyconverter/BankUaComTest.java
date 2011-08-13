package com.tunyk.currencyconverter;

import com.tunyk.currencyconverter.api.Currency;
import com.tunyk.currencyconverter.api.CurrencyConverterException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BankUaComTest {

    // logger
    private static final Logger LOG = LoggerFactory.getLogger(BankUaComTest.class);

    // test object
    private BankUaCom bankUaCom;

    @BeforeClass
    public static void setUpClass() {
        LOG.info("Testing BankUaCom");
    }

    @AfterClass
    public static void tearDownClass() {
        LOG.info("BankUaCom test finished");
    }

    @Before
    public void setUp() throws CurrencyConverterException {
        LOG.info("Setting up data for testing...");
        bankUaCom = new BankUaCom(Currency.CNY, Currency.UAH);
    }

    @Test
    public void testCurrencyRate() throws CurrencyConverterException {
        LOG.info("Testing BankUaCom...");

        // TODO: mock testing, use bankUaCom.xml instead of fetching from the web each time

        System.out.println("convert yuan to hrynvia");
        System.out.println(bankUaCom.convertCurrency(100f));
        System.out.println(bankUaCom.convertCurrency(200f));
        System.out.println(bankUaCom.convertCurrency(1f));
        System.out.println(bankUaCom.convertCurrency(2f));

        System.out.println("convert yuan to dollar/euro");
        System.out.println(bankUaCom.convertCurrency(100f, Currency.USD));
        System.out.println(bankUaCom.convertCurrency(100f, Currency.EUR));

        System.out.println("convert to the same currency");
        System.out.println(bankUaCom.convertCurrency(1f, Currency.UAH, Currency.UAH));
        System.out.println(bankUaCom.convertCurrency(1f, Currency.USD, Currency.USD));

        System.out.println("convert dollar to hryvnia, and vice versa");
        System.out.println(bankUaCom.convertCurrency(1f, Currency.USD, Currency.UAH));
        System.out.println(bankUaCom.convertCurrency(1f, Currency.UAH, Currency.USD));

        System.out.println("convert euro to hryvnia, and vice versa");
        System.out.println(bankUaCom.convertCurrency(1f, Currency.EUR, Currency.UAH));
        System.out.println(bankUaCom.convertCurrency(1f, Currency.UAH, Currency.EUR));

        System.out.println("convert euro to dollar, and vice versa");
        System.out.println(bankUaCom.convertCurrency(1f, Currency.EUR, Currency.USD));
        System.out.println(bankUaCom.convertCurrency(1f, Currency.USD, Currency.EUR));

        System.out.println("convert yuan to dollar, and vice versa");
        System.out.println(bankUaCom.convertCurrency(1f, Currency.USD));
        System.out.println(bankUaCom.convertCurrency(1f, Currency.USD, Currency.CNY));

        //Assert.assertEquals(0, bankUaCom.getCurrencyRate());
        LOG.info("Passed");
    }
}
