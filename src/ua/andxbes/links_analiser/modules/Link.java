/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ua.andxbes.links_analiser.modules;

import java.util.Set;

/**
 *
 * @author andr
 */
public class Link {
    private String title,
                   domain;
    private Set<String> externalLink;

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * @param domain the domain to set
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * @return the externalLink
     */
    public Set<String> getExternalLink() {
        return externalLink;
    }

    /**
     * @param externalLink the externalLink to set
     */
    public void setExternalLink(Set<String> externalLink) {
        this.externalLink = externalLink;
    }
    
    public int get_amount_external_links(){
        return externalLink.size();
    }
    
}
