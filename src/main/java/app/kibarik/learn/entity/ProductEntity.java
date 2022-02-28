package app.kibarik.learn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Data
@AllArgsConstructor
public class ProductEntity {
    //наименование, номер производственного цеха и минимальная стоимость для агента
    int id;
    String title;
    String productType;
    String articleNumber;
    String description;
    String image;
    int productionPersonCount;
    int productionWorkshopNumber;
    Double minCostForAgent;
    ImageIcon imageIcon = null;


    public ProductEntity(int id, String title, String productType, String articleNumber, String description, String image, int productionPersonCount, int productionWorkshopNumber, Double minCostForAgent){
        this.id = id;
        this.title = title;
        this.productType = productType;
        this.articleNumber = articleNumber;
        this.description = description;
        this.image = image.replaceAll("\\\\", "/");
        this.productionPersonCount = productionPersonCount;
        this.productionWorkshopNumber = productionWorkshopNumber;
        this.minCostForAgent = minCostForAgent;

        try {
            this.imageIcon = new ImageIcon(ImageIO.read(ProductEntity.class.getClassLoader().getResource(this.image)).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        } catch (Exception e) {
            try {
                this.imageIcon = new ImageIcon(ImageIO.read(ProductEntity.class.getClassLoader().getResource("picture.png")).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

}
