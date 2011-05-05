/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package search;

import java.io.File;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import util.ArticleField;

/**
 *
 * @author Alex Ginsca
 */
public class WikipediaLuceneSearcher {

    private Searcher searcher;
    private Analyzer analyzer;
    private static int hitsPerPage = 10;

    public void setIndexesFolder(String folder) throws IOException {

        Directory luceneIndex = FSDirectory.open(new File(folder));
        IndexReader reader = IndexReader.open(luceneIndex, true);
        searcher = new IndexSearcher(reader);
        analyzer = new StandardAnalyzer(Version.LUCENE_30);

    }

    public void search(String queryStr, String field) throws CorruptIndexException, IOException, ParseException {

        QueryParser parser = new QueryParser(Version.LUCENE_30, field, analyzer);
        Query query = parser.parse(queryStr);

        TopScoreDocCollector collector = TopScoreDocCollector.create(hitsPerPage, true);

        searcher.search(query, collector);

        printResults(collector);

    }

    private void printResults(TopScoreDocCollector collector) throws CorruptIndexException, IOException {

        ScoreDoc[] hits = collector.topDocs().scoreDocs;

        System.out.println("Found " + hits.length + " hits.");
        for (int i = 0; i < hits.length; ++i) {
            int docId = hits[i].doc;
            Document d = searcher.doc(docId);
            System.out.println((i + 1) + ". " + d.get(ArticleField.title.toString()));
        }

        if (hits.length > 0) {

            int docId = hits[0].doc;
            Document d = searcher.doc(docId);
            System.out.println("\n" + d.get(ArticleField.text.toString()));

        }
    }
}
