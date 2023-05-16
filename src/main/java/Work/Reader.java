/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Work;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author Пользователь
 */
public interface Reader {
    public void look(File file, String type);
    public void setNext(Reader reader);

    public ArrayList<ReactorType> getReactorContainer();
}
