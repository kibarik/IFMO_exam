package app.kibarik.learn.utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class BaseForm extends JFrame {
    String APP_TITLE = "Продукты";
    Image APP_ICON = null;

    public BaseForm(int width, int height){
        setTitle(APP_TITLE);
        setMinimumSize(new Dimension(width, height));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(
                Toolkit.getDefaultToolkit().getScreenSize().width / 2 - width/2,
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - height/2
        );

        try {
            setIconImage(ImageIO.read(BaseForm.class.getClassLoader().getResource("logo.png")));
        } catch (IOException e) {
            System.out.print("Лого не установлен");
        }
    }
}
