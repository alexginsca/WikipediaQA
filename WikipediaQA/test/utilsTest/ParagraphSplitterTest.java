/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilsTest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import org.junit.Test;
import util.WikipediaArticle;

/**
 *
 * @author Alex Ginsca
 */
public class ParagraphSplitterTest {

    @Test
    public void splitTest() throws FileNotFoundException, UnsupportedEncodingException, IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("test files//testParaSplitter.txt"), "UTF-8"));

        String text = "";
        String line = "";
        while((line = br.readLine()) != null ) {
            text += line + "\n";
        }
        br.close();

        WikipediaArticle wikiArticle = new WikipediaArticle("test", text);
        wikiArticle.splitToParagraphs();

        for(String para : wikiArticle.getParagraphs()) {

            System.out.println("para : " + para);
        }
        
    }

}
