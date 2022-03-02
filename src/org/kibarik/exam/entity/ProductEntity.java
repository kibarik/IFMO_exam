package org.kibarik.exam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Data
@AllArgsConstructor
public class ProductEntity {
    int id;
    String title;
    String productType;
    String articleNumber;
    String description;
    String image;
    int productionPersonCount;
    int productionWorkshopNumber;
    Double minCostForAgent;

    ImageIcon imageIcon;

    public ProductEntity(int id, String title, String productType, String articleNumber, String description, String image, int productionPersonCount, int productionWorkshopNumber, Double minCostForAgent) {
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
            try {
                this.imageIcon = new ImageIcon(ImageIO.read(ProductEntity.class.getClassLoader().getResource(this.image)).getScaledInstance(50, 50, Image.SCALE_DEFAULT));
            } catch (Exception e) {
                this.imageIcon = new ImageIcon(ImageIO.read(ProductEntity.class.getClassLoader().getResource("picture.png")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
