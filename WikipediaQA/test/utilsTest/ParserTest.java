package utilsTest;


import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import org.xml.sax.SAXException;
import util.WikipediaXMLParser;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Alex Ginsca
 */
public class ParserTest {

    @Test
    public void testWikiXMLParser() throws ParserConfigurationException, SAXException, IOException {

        WikipediaXMLParser.parseWikipediaXML("resources\\curatitor\\rez test.xml");

    }

}
