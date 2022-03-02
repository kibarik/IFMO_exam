package org.kibarik.exam.utils;

import javax.swing.*;
import java.awt.*;

public class DialogUtils {

    public void  showInfo(Component component, String msg){
        JOptionPane.showMessageDialog(component, msg, "Информация: ", JOptionPane.INFORMATION_MESSAGE);
    }


    public static void  showError(Component component, String msg){
        JOptionPane.showMessageDialog(component, msg, "Ошибка: ", JOptionPane.ERROR_MESSAGE);
    }
}
