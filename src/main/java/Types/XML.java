/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Types;
import Work.ReactorType;
import Work.Reader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author Пользователь
 */
public class XML extends DefaultHandler implements Reader{
    private ArrayList<ReactorType> reactorContainer = new ArrayList<>();
    private ArrayList<String> docData = new ArrayList<>();
    private String ElemenT;
    private Reader nextOne;

    
        @Override
        public void look(File file, String tipe) {
        if(tipe.equals("xml"))    // основной метод класса где мы запускаем парсинг файла
        {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XML saxp = new XML();
            parser.parse(file, saxp);
            reactorContainer = saxp.reactorContainer;    // заполняем массив массивом из парсера
            System.out.println(reactorContainer);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        }
        else  // если xml не может считать файл то пытаемся считать его следущим ридером который мы указывали в fileDispencer
        {
            nextOne.look(file, tipe);
            reactorContainer = nextOne.getReactorContainer();
        }
    }
    
    
    @Override
    public void startDocument() throws SAXException {     // методы из парсера
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        ElemenT = qName;  // считываем элемент стрингом
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException { // проверяем объекты на соответствие атрибутам реактора, если соответсвуют то в массив их
        if(ElemenT.equals("class"))
        {
            this.docData.add(new String(ch, start, length));
        }
        if(ElemenT.equals("burnup"))
        {
            this.docData.add(new String(ch, start, length));
        }
        if(ElemenT.equals("kpd"))
        {
            this.docData.add(new String(ch, start, length));
        }
        if(ElemenT.equals("enrichment"))
        {
            this.docData.add(new String(ch, start, length));
        }
        if(ElemenT.equals("termal_capacity"))
        {
            this.docData.add(new String(ch, start, length));
        }
        if(ElemenT.equals("electrical_capacity"))
        {
            this.docData.add(new String(ch, start, length));
        }
        if(ElemenT.equals("life_time"))
        {
            this.docData.add(new String(ch, start, length));
        }
        if(ElemenT.equals("first_load"))
        {
            this.docData.add(new String(ch, start, length));
        }
    }
   

    
    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        ElemenT = "";  // без этого не работает
    }
    @Override
    public void endDocument() {
        General();  // как только дошли до конца распределяем наши атрибуты по реакторам
    }




    @Override
    public void setNext(Reader neibour) {
        nextOne = neibour;
    }
    
    public void General()
    {
        for(int i=0; i<docData.size(); i+=8)  // создаем реакторы
        {
            reactorContainer.add(new ReactorType(docData.get(i),Double.parseDouble(docData.get(i+1)),Double.parseDouble(docData.get(i+2)),Double.parseDouble(docData.get(i+3)),Integer.parseInt(docData.get(i+4)),Double.parseDouble(docData.get(i+5)),Integer.parseInt(docData.get(i+6)),Double.parseDouble(docData.get(i+7))));           
        }
        for(ReactorType rc : reactorContainer) // проставляем от куда они
        {
            rc.setFrom("xml");
        }
    }

    @Override
    public ArrayList<ReactorType> getReactorContainer() {
        return reactorContainer;
    }
}
