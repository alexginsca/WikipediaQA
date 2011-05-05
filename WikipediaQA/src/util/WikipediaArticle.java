/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Alex Ginsca
 */
public class WikipediaArticle {

    private String title;
    private String text;
    private List<String> paragraphs;

    public WikipediaArticle(String title, String text) {

        this.title = title;
        this.text = text;

        paragraphs = new ArrayList<String>();
        
    }

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
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }  

 

    /**
     * @return the paragraphs
     */
    public List<String> getParagraphs() {
        return paragraphs;
    }

    /**
     * @param paragraphs the paragraphs to set
     */
    public void setParagraphs(List<String> paragraphs) {
        this.paragraphs = paragraphs;
    }

       public void splitToParagraphs() {
        Pattern PARAGRAPH_REGEXP = Pattern.compile("[ \\t]*(\r\n|\r|\n)(?:[ \\t]*\\1+)+", Pattern.MULTILINE);

        Matcher matcher = PARAGRAPH_REGEXP.matcher(text);       

        int offset = 0;
        String inputText = text;

        while (matcher.find()) {

            getParagraphs().add(text.substring(offset, matcher.start()));

            offset = matcher.end();
            inputText = text.substring(offset, text.length());
        }

    }

    
}
