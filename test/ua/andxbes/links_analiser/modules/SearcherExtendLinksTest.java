/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.andxbes.links_analiser.modules;

import java.net.URISyntaxException;
import java.util.LinkedHashSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author andr
 */
public class SearcherExtendLinksTest {
    private SearcherExtendLinks sel;
    
    private String testUrl;

    public SearcherExtendLinksTest() {
    }
   

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws URISyntaxException {

     
        testUrl = "http://babyplus.ua/component/virtuemart/";
        
        sel = new SearcherExtendLinks(testUrl);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void runSearcherLinks() {
        System.out.println("-------------------------runSearcherLinks------------------------------");
        sel.scan();
        System.out.println("~~~~~~~~~~~~~~~ External links ~~~~~~~~~~~~~~~~~~");
        
        for (String object : sel.getLinks()) {
              System.out.println(object);
        }
      

    }

    @Test
    public void testExcludes() throws URISyntaxException {
        System.out.println("---------------------------testExcludes---------------------------------");
        Set<String> exludes = new LinkedHashSet<>();
        exludes.add(".txt");
        sel.setExcludes(exludes);

        String url = ("http://modxrev.smrtp.ru/robots.txt");
        String url2 = ("http://modxrev.smrtp.ru/system/testcaptcha.html");
        boolean bool = sel.hasExclude(url);
        boolean bool2 = sel.hasExclude(url2);

        System.out.println(url + " - " + bool);
        System.out.println(url2 + " - " + bool2);

        assertNotEquals(bool, bool2);

    }

    @Test
    public void testIncludes() throws URISyntaxException {
        System.out.println("---------------------------testIncludes---------------------------------");
        Set<String> includes = new LinkedHashSet<>();
        includes.add("Sape");

        sel.setIncludes(includes);

        String url = ("https://www.sape.ru/");
        String url2 = ("http://modxrev.smrtp.ru/system/testcaptcha.html");
        boolean bool = sel.hasInclude(url);
        boolean bool2 = sel.hasInclude(url2);

        System.out.println(url.toString());

        System.out.println(url + " - " + bool);
        System.out.println(url2 + " - " + bool2);

        assertNotEquals(bool, bool2);

    }

    @Test
    public void testExternalLink() throws URISyntaxException {
        System.out.println("---------------------------testExternalLink---------------------------------");
        String url = ("https://www.sape.ru/");
        String url2 = (testUrl);

        String url3 = ("/system/testcaptcha.html");
        String url4 = ("system/testcaptcha.html");
        String url5 = ("//ya.ru");
        
        String url6 = ("https://yandex.ua/");
     

        boolean bool = sel.isExternalLink(url);
        boolean bool2 = sel.isExternalLink(url2);
        boolean bool3 = sel.isExternalLink(url3);
        boolean bool4 = sel.isExternalLink(url4);
        boolean bool5 = sel.isExternalLink(url5);
        boolean bool6 = sel.isExternalLink(url6);

        System.out.println(url.toString());

        System.out.println(url + " - " + bool);
        System.out.println(url2 + " - " + bool2);
        System.out.println(url3 + " - " + bool3);
        System.out.println(url4 + " - " + bool4);
        System.out.println(url5 + " - " + bool5);
        System.out.println(url6 + " - " + bool6);

        assertTrue(bool);
        assertFalse(bool2);
        assertFalse(bool3);
        assertFalse(bool4);
        assertTrue(bool5);
        assertTrue(bool6);

    }

    
}
