import org.w3c.dom.Document;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(4005);
        while (true) {
            try {
                handleRequest(server.accept());
            } catch (Throwable e) {
                e.printStackTrace();
                System.out.print(e);
            }
        }
    }

    protected static void handleRequest(Socket socket) throws IOException, ParserConfigurationException, TransformerConfigurationException, SAXException, TransformerException {
        try (
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        ) {
            // Scanner s = new Scanner(inputStream).useDelimiter("\\A");
            // String result = s.hasNext() ? s.next() : "";
            // System.out.println(result);

            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();
            DefaultHandler handler = new DefaultHandler() {
                @Override
                public void startDocument() throws SAXException {

                    super.startDocument();
                    System.out.println("start document");
                }
                @Override
                public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
                    System.out.println("start element " + qName);
                    super.startElement(uri, localName, qName, attributes);//enter code here
                    if (qName.equals("frameit")) {
                        // compare element name and get value/ for further help read saxparser
                    }
                    System.out.println(qName);

                }

                @Override
                public void endElement(String uri, String localName, String qName)
                    throws SAXException {

                    super.endElement(uri, localName, qName);
                    System.out.println("end element");
                }

                @Override
                public void characters(char[] ch, int start, int length)
                    throws SAXException {

                    super.characters(ch, start, length);
                    System.out.println("characters");
                }

            };
            sp.parse(inputStream, handler);

            // StreamResult sr = new StreamResult(outputStream);
            // DOMSource ds = new DOMSource(document);
            // Transformer tf = TransformerFactory.newInstance().newTransformer();


            // tf.transform(ds, sr);

            outputStream.flush();
        }
    }
}