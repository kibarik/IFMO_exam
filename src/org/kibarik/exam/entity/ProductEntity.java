package org.kibarik.exam.entity;

import lombok.Data;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Data
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

    ImageIcon imageIcon = null;

    public ProductEntity(int id, String title, String productType, String articleNumber, String description, String image, int productionPersonCount, int productionWorkshopNumber, Double minCostForAgent){
        this.id = id;
        this.title = title;
        this.productionPersonCount = productionPersonCount;
        this.productType = productType;
        this.articleNumber = articleNumber;
        this.description = description;
        this.image = image;
        this.productionWorkshopNumber = productionWorkshopNumber;
        this.minCostForAgent = minCostForAgent;

        try {
            this.imageIcon = new ImageIcon(ImageIO.read(ProductEntity.class.getClassLoader().getResource("logo.png")).getScaledInstance(50,50, Image.SCALE_DEFAULT));
            try {
                this.imageIcon = new ImageIcon(ImageIO.read(ProductEntity.class.getClassLoader().getResource(image)).getScaledInstance(50,50, Image.SCALE_DEFAULT));
            } catch (Exception ignored) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
