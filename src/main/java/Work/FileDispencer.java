/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Work;

import Types.JSON;
import Types.YAML;
import Types.XML;
import java.io.File;
import javax.swing.tree.DefaultMutableTreeNode;
      
/**
 *
 * @author Пользователь
 */
public class FileDispencer {
    private Reader container = new YAML();
    
    public void letsStart(File file)
    {
        Reader json = new JSON();  // создаем парсеры под каждый тип и задаем последовательность считывания
        Reader xml = new XML();
        json.setNext(null);
        xml.setNext(json);
        container.setNext(xml);
        
        System.out.println("start");
        container.look(file, getFileType(file)); // запускаем считывание
        
    }
    public String getFileType(File file) {  // получение типа файла
        String fileName = file.getAbsolutePath();
        String extension = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            extension = fileName.substring(i+1);
        }
        return extension;
    }
    
     public DefaultMutableTreeNode prepareTree() // подготовка дерева
        {
            DefaultMutableTreeNode units = new DefaultMutableTreeNode("Типы реакторов");
            for(ReactorType rx : container.getReactorContainer())
            {
                DefaultMutableTreeNode unit = new DefaultMutableTreeNode(rx.getName());
                unit.add(new DefaultMutableTreeNode("Burnup is "+rx.getKpd()));
                unit.add(new DefaultMutableTreeNode("KPD is "+rx.getKpd()));
                unit.add(new DefaultMutableTreeNode("Enrichment is "+rx.getEnrichment()));
                unit.add(new DefaultMutableTreeNode("Termal_capacity is "+rx.getTermal_capacity()));
                unit.add(new DefaultMutableTreeNode("Electrical_capacity is "+rx.getElectrical_capacity()));
                unit.add(new DefaultMutableTreeNode("Life_time is "+rx.getLife_time()));
                unit.add(new DefaultMutableTreeNode("First_load is "+rx.getFirst_load()));
                unit.add(new DefaultMutableTreeNode("From "+rx.getFrom()));
                units.add(unit);
            }
            return units;
        }
    
    
}
