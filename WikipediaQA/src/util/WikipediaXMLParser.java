/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Alex Ginsca
 */
public class WikipediaXMLParser {

    public static List<WikipediaArticle> parseWikipediaXML(String filePath) throws ParserConfigurationException, SAXException, IOException {

        List<WikipediaArticle> wikiArticles = new ArrayList<WikipediaArticle>();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document dom = db.parse(filePath);

        Node root = dom.getFirstChild();  
        NodeList pageNodes = root.getChildNodes();

        for (int i = 0; i < pageNodes.getLength(); i++) {

            Node pageNode = pageNodes.item(i);
            NodeList pageNodeChildren = pageNode.getChildNodes();

            if (pageNodeChildren != null) {

                String title = null;
                String text = null;

                for (int j = 0; j < pageNodeChildren.getLength(); j++) {

                    if (pageNodeChildren.item(j).getNodeName().equals("title")) {

                        title = pageNodeChildren.item(j).getTextContent();

                    }
                    
                    if (pageNodeChildren.item(j).getNodeName().equals("revision")) {

                        Node textNode = pageNodeChildren.item(j).getFirstChild().getNextSibling();

                        if (textNode != null && textNode.getNodeName().equals("text")) {

                            text = textNode.getTextContent();

                        }
                    }
                }

                if(title != null && text != null) {

                    wikiArticles.add(new WikipediaArticle(title, text));
                    
                }
            }
        }

        return wikiArticles;
    }
}
