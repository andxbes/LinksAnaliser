/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.andxbes.links_analiser.modules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author andr
 */
public interface Collector_Links {

    String CHARSET = "UTF-8";
    // Запрос и список со ссылоками
    Map <String,Set<Link>> TOP10  = new HashMap<>();
    

    // protected Map<String, List<String>> top10;
//    protected String query;
//    protected String search_machine = "https://www.google.com/search";
    String DOMAIN_NAME_PATTERN = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";
    Pattern patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
    
    Collector_Links collectLinks(int timeout);

    Map<String, List<String>> get_url_list_top();
    Map<String, List<String>> get_top10();

    static String getDomainName(String url) {

        String domainName = "";
        Matcher matcher = patternDomainName.matcher(url);
        if (matcher.find()) {
            domainName = matcher.group(0).toLowerCase().trim();
        }
        return domainName;

    }

    default String showList() {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<String>> entry : get_top10().entrySet()) {
            String key = entry.getKey();
            List<String> addits = entry.getValue();

            sb.append("--------------").append(key).append("----------------");

            for (String addit : addits) {
                sb.append(addit).append("\n");
            }
        }
        return sb.toString();
    }

}
