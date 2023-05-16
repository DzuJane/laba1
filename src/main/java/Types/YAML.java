/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Types;

import Work.ReactorType;
import Work.Reader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author Пользователь
 */
public class YAML implements Reader{
    private ArrayList<ReactorType> reactorContainer = new ArrayList<>();
    private ArrayList<String> docData = new ArrayList<>();
    private Reader nextOne;

    @Override
    public void look(File file, String tipe) {
        if(tipe.equals("yaml"))    // основной метод класса где мы запускаем парсинг файла
        {
        Yaml yaml = new Yaml();
        Map<String, ArrayList<Map<String,String>>> myMap = yaml.load(getStringFile(file)); // получаем файл как элемент и массив еще элементов
        ArrayList<Map<String,String>> recievedData = myMap.get("params"); // забираем этот массив из элементов
        
        for(Map<String, String> elem : recievedData)   // разбиваем массив элементов (мапу) на массив String атрибутов реактора
        {
            for(Map.Entry<String,String> node : elem.entrySet())
            {
                docData.add(String.valueOf(node.getValue()));
            }
        }
        General(); // создаем реакторы
        System.out.println(reactorContainer);
        }
        else // если yaml не может считать файл то пытаемся считать его следущим ридером который мы указывали в fileDispencer
        {
            nextOne.look(file, tipe);
            reactorContainer = nextOne.getReactorContainer();
        }
    }

    public ArrayList<ReactorType> getReactorContainer() {
        return reactorContainer;
    }
    

    @Override
    public void setNext(Reader neibour) {
        this.nextOne = neibour;
    }
    
    
    public void General()
    {
        for(int i=0; i<docData.size(); i+=8)
        {
            reactorContainer.add(new ReactorType(docData.get(i),Double.parseDouble(docData.get(i+1)),Double.parseDouble(docData.get(i+2)),Double.parseDouble(docData.get(i+3)),Integer.parseInt(docData.get(i+4)),Double.parseDouble(docData.get(i+5)),Integer.parseInt(docData.get(i+6)),Double.parseDouble(docData.get(i+7))));           
        }
        for(ReactorType rc : reactorContainer)
        {
            rc.setFrom("yaml");
        }
    }
     // получаем yaml как String 
    public String getStringFile(File f) {
        try {
            BufferedReader file = new BufferedReader(new FileReader(f.getAbsolutePath()));
            StringBuffer inputBuffer = new StringBuffer();
            String line;

            while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');
            }
            file.close();
            String inputStr = inputBuffer.toString();
            return inputStr;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
