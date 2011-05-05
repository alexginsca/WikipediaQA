package indexTests;

import index.WikipediaLuceneIndexer;
import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;
import util.WikipediaArticle;
import util.WikipediaXMLParser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex Ginsca
 */
public class IndexerTest {

    @Test
    public void testArticleIndexer() throws ParserConfigurationException, SAXException, IOException {

        List<WikipediaArticle> wikiArticles =  WikipediaXMLParser.parseWikipediaXML("resources\\curatitor\\rez-xaa.xml");

        WikipediaLuceneIndexer.indexAtArticleLevel("results\\wikipedia_pages_indexes", wikiArticles);

    }

    @Test
    public void testParagraphIndexer() throws ParserConfigurationException, SAXException, IOException {

        List<WikipediaArticle> wikiArticles =  WikipediaXMLParser.parseWikipediaXML("resources\\curatitor\\rez-xaa.xml");

        WikipediaLuceneIndexer.indexAtParagraphLevel("results\\wikipedia_paragraphs_indexes", wikiArticles);

    }

}
