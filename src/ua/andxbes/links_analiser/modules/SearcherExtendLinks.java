/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.andxbes.links_analiser.modules;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 *
 * @author andr
 */
public class SearcherExtendLinks {
    public static final String CHARSET = "UTF-8";;

    private static Set<String> excludes;
    private static Set<String> includes;
    private final String url;
    private final String base_url;

    private static final Logger log = Logger.getLogger(SearcherExtendLinks.class.getName());

    private final ExecutorService task = Executors.newFixedThreadPool(3);
    private final Set<Runnable> listOfTasks = Collections.synchronizedSet(new LinkedHashSet<>());

    private Set<String> externalLinks;
    private List<String> keywords;

    //Статический конструктор 
    {
        excludes = readRules(new File("./excludes.txt"));
        includes = readRules(new File("./includes.txt"));
    }

    public SearcherExtendLinks(String siteUrl) throws URISyntaxException {

        this.url = siteUrl.toLowerCase();
        URI urlob = new URI(this.url);

        this.base_url = urlob.getScheme() + "://" + urlob.getHost();

        this.externalLinks = Collections.synchronizedSet(new LinkedHashSet<>());
    }

    //
    public boolean scan() {
        this.collectLinks();

        this.runTasks();

        while (!task.isTerminated() && listOfTasks.size() > 0) {
            try {
                log.log(Level.INFO, "task  = {0}", listOfTasks.size());
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                log.log(Level.SEVERE, null, ex);
            }
        }

        return false;
    }

    private void runTasks() {

        for (Runnable listofTask : listOfTasks) {
            task.submit(listofTask);
        }

    }

    public void collectLinks() {
        Connection con = Jsoup.connect(this.url)
                .method(Connection.Method.GET)
                .userAgent("Mozilla/5.0").timeout(3000);

        try {
            Document doc = (Document) con.get();

            Elements links = doc.select("a");
            //System.out.println(doc.body());
            for (Element link : links) {
                String title = link.text();
                String url = link.absUrl("href");

                if (!url.startsWith("http")) {
                    continue; // Ads/news/etc.
                }

                //собираем внешние ссылки
                if ((this.isExternalLink(url) & !this.hasExclude(url)) || this.hasInclude(url)) {
                    externalLinks.add(url);
                } else {
//                    log.log(Level.INFO, "~~~~~~~~~~~~~~~ Internal links ~~~~~~~~~~~~~~");
//                    log.log(Level.INFO, new String(title.getBytes(Searcher_External_Links.CHARSET), System.getProperty("file.encoding")));
//                    log.log(Level.INFO, url);
                    
                    log.log(Level.INFO, "Internal link \n name = {0}\n url={1}",new String[]{
                        new String(title.getBytes(SearcherExtendLinks.CHARSET), System.getProperty("file.encoding"))
                        ,url});

                    if (listOfTasks.size() < 50) {

                        listOfTasks.add(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    SearcherExtendLinks sel = new SearcherExtendLinks(url);
                                    sel.collectLinks();
                                    externalLinks.addAll(sel.getLinks());

                                } catch (URISyntaxException ex) {
                                    Logger.getLogger(SearcherExtendLinks.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                listOfTasks.remove(this);
                            }
                        });
                    }

                }

            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SearcherExtendLinks.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SearcherExtendLinks.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param link
     * @return
     */
    protected boolean isExternalLink(String link) {

        boolean result = false;
        link = link.toLowerCase();

        //Возможно нужно на регулярку перевести 
        if ((link.startsWith(this.base_url)
                | link.startsWith("/")
                | !link.contains("http"))
                & (!link.startsWith("//"))) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    protected boolean hasExclude(String url) {
        boolean result = false;

        for (String exclude : excludes) {
            if (url.contains(exclude) || url.contains(exclude.toLowerCase())) {
                result = true;
            }
        }
        return result;
    }

    protected boolean hasInclude(String url) {
        boolean result = false;

        for (String include : includes) {
            if (url.contains(include) || url.contains(include.toLowerCase())) {
                result = true;
            }
        }
        return result;
    }

    //Читаем файл с Правилами  , если его нет создаем . 
    private static Set<String> readRules(File file) {
        Set<String> rules = new LinkedHashSet<>();
        StringBuilder sb = new StringBuilder();
        if (file.isFile()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file));) {

               
                sb.append("---------rules from ").append(file.getName()).append("-----------------\n");
                String line;
                while ((line = br.readLine()) != null) {
                    rules.add(line);
                    sb.append(" * ").append(line).append("\n");
                    
                }

            } catch (FileNotFoundException ex) {
                Logger.getLogger(SearcherExtendLinks.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(SearcherExtendLinks.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(SearcherExtendLinks.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }

        log.log(Level.INFO, sb.toString());
        return rules;

    }

    public Set<String> getLinks() {
        return this.externalLinks;
    }

    /**
     * @param excludes the excludes to set
     */
    public void setExcludes(Set<String> excludes) {
        this.excludes = excludes;
    }

    /**
     * @param includes the includes to set
     */
    public void setIncludes(Set<String> includes) {
        this.includes = includes;
    }

}
