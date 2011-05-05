package searchTests;


import java.io.IOException;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.junit.Test;
import search.WikipediaLuceneSearcher;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex Ginsca
 */
public class SearcherTest {

    @Test
    public void testPageIndexer() throws IOException, CorruptIndexException, ParseException  {

        WikipediaLuceneSearcher wikiSearcher = new WikipediaLuceneSearcher();

        wikiSearcher.setIndexesFolder("results\\wikipedia_pages_indexes");

        wikiSearcher.search("România", "text");

    }

    @Test
    public void testParagraphIndexer() throws IOException, CorruptIndexException, ParseException  {

        WikipediaLuceneSearcher wikiSearcher = new WikipediaLuceneSearcher();

        wikiSearcher.setIndexesFolder("results\\wikipedia_paragraphs_indexes");

        wikiSearcher.search("România", "paragraph");

    }


}
