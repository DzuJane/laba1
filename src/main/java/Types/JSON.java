/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Types;

import Work.ReactorType;
import Work.Reader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 *
 * @author Пользователь
 */
public class JSON implements Reader, JsonDeserializer<ArrayList<ReactorType>>{
    private ArrayList<ReactorType> ReactorContainer = new ArrayList<>();
    private Reader nextOne;
    
    @Override
    public void look(File file, String tipe) {
        if(tipe.equals("json"))
        {
        Type type = new TypeToken<ArrayList<ReactorType>>(){}.getType();
        Gson g = new GsonBuilder().registerTypeAdapter(type, new JSON()).create();
        ReactorContainer = g.fromJson(getStringFile(file), type);
        System.out.println(ReactorContainer);
        }
        else  // если json не может считать файл то пытаемся считать его следущим ридером который мы указывали в fileDispencer а там null
        {
            try{
            nextOne.look(file, tipe);
            ReactorContainer = nextOne.getReactorContainer();
            }
            catch (NullPointerException e)
            {
                System.out.println("неверный формат");
            }
        }
    }

    @Override
    public void setNext(Reader neibour) {
        nextOne=neibour;
    }

    @Override
    public ArrayList<ReactorType> deserialize(JsonElement je, Type type, JsonDeserializationContext jdc) throws JsonParseException {
        JsonObject jsonObject = je.getAsJsonObject();
        JsonArray joe = jsonObject.getAsJsonArray("params"); // берем элемент соответствующий ключу params
        ArrayList<ReactorType> n = new ArrayList<>();  // готовим элементы для считывания
        ReactorType roxette = null;
        for(JsonElement jo : joe) {  // пробегаем массив json элементов
            JsonObject js = jo.getAsJsonObject();  // выдераем объект из элемента
            String s = "";
            for(char ch : String.valueOf(js.getAsJsonPrimitive("class")).toCharArray()) //убераем кавычки со стрингов
            {
                if(ch != '"')
                {
                    s=s+ch;
                }
            } // парсим значения из джесон объекта
            roxette = new ReactorType(s, Double.parseDouble(String.valueOf(js.getAsJsonPrimitive("burnup"))), Double.parseDouble(String.valueOf(js.getAsJsonPrimitive("kpd"))), Double.parseDouble(String.valueOf(js.getAsJsonPrimitive("enrichment"))), Integer.parseInt(String.valueOf(js.getAsJsonPrimitive("termal_capacity"))), Double.parseDouble(String.valueOf(js.getAsJsonPrimitive("electrical_capacity"))), Integer.parseInt(String.valueOf(js.getAsJsonPrimitive("life_time"))), Double.parseDouble(String.valueOf(js.getAsJsonPrimitive("first_load"))));
            roxette.setFrom("json");
            n.add(roxette);
        }
        return n;
    }
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
           // System.out.println(inputStr);
            return inputStr;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @Override
    public ArrayList<ReactorType> getReactorContainer() {
        return ReactorContainer;
    }
    
}
