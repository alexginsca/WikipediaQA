/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package index;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import util.ArticleField;
import util.WikipediaArticle;

/**
 *
 * @author Alex Ginsca
 */
public class WikipediaLuceneIndexer {

    public static void indexAtArticleLevel(String indexFolder, List<WikipediaArticle> wikiArticles) throws IOException {
        Directory index = FSDirectory.open(new File(indexFolder));

        IndexWriter writer = new IndexWriter(index, new StandardAnalyzer(Version.LUCENE_30), true,
                IndexWriter.MaxFieldLength.UNLIMITED);

        for (WikipediaArticle wikiArticle : wikiArticles) {

            Document doc = new Document();

            doc.add(new Field(ArticleField.title.toString(), wikiArticle.getTitle(),
                    Field.Store.YES, Field.Index.ANALYZED));
            doc.add(new Field(ArticleField.text.toString(), wikiArticle.getText(),
                    Field.Store.YES, Field.Index.ANALYZED));

            writer.addDocument(doc);
        }

        writer.close();

    }

    public static void indexAtParagraphLevel(String indexFolder, List<WikipediaArticle> wikiArticles) throws IOException {
        Directory index = FSDirectory.open(new File(indexFolder));

        IndexWriter writer = new IndexWriter(index, new StandardAnalyzer(Version.LUCENE_30), true,
                IndexWriter.MaxFieldLength.UNLIMITED);

        for (WikipediaArticle wikiArticle : wikiArticles) {

            wikiArticle.splitToParagraphs();

            List<String> paragraphs = wikiArticle.getParagraphs();

            for (int i = 0 ; i < paragraphs.size() ; i++) {

                if (!paragraphs.get(i).equals("")) {

                    Document doc = new Document();

                    doc.add(new Field(ArticleField.title.toString(), wikiArticle.getTitle(),
                            Field.Store.YES, Field.Index.ANALYZED));
                    doc.add(new Field(ArticleField.paragraphIndex.toString(), i + "",
                            Field.Store.YES, Field.Index.ANALYZED));
                    doc.add(new Field(ArticleField.paragraph.toString(), paragraphs.get(i),
                            Field.Store.YES, Field.Index.ANALYZED));

                    writer.addDocument(doc);
                }
            }
        }

        writer.close();

    }
}
