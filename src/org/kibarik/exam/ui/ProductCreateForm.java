package org.kibarik.exam.ui;

import org.kibarik.exam.entity.ProductEntity;
import org.kibarik.exam.manager.ProductEntityManager;
import org.kibarik.exam.utils.DialogUtils;

import javax.swing.*;
import java.sql.SQLException;

public class ProductCreateForm extends BaseForm{
    private JButton backButton;
    private JButton saveButton;
    private JTextField titleField;
    private JComboBox productionTypeComoBox;
    private JTextArea descriptionField;
    private JSpinner personalCountField;
    private JTextField articleField;
    private JSpinner factoryNumberField;
    private JTextField minCostField;
    private JPanel mainPanel;
    private JTextField imageField;

    ProductCreateForm(){
        super(600, 600);

        initButtons();
        setContentPane(mainPanel);

        setVisible(true);
    }

    public void initButtons(){
        backButton.addActionListener(e -> {
            dispose();
            new ProductTableForm();
        });

        saveButton.addActionListener(e -> {

            String title = titleField.getText();
            String productType = String.valueOf(productionTypeComoBox.getSelectedItem());
            String articleNumber = articleField.getText();
            String description = descriptionField.getText();
            String image = imageField.getText();
            int productionPersonCount = (int) personalCountField.getValue();
            int productionWorkshopNumber = (int) factoryNumberField.getValue();
            Double minCostForAgent = Double.parseDouble(minCostField.getText());

            try {
                ProductEntityManager.insert(new ProductEntity(
                        -1,
                        title,
                        productType,
                        articleNumber,
                        description,
                        image,
                        productionPersonCount,
                        productionWorkshopNumber,
                        minCostForAgent
                ));
                DialogUtils.showInfo(this, "Добавление успешно!");
                dispose();
                new ProductTableForm();
            } catch (SQLException ex) {
                DialogUtils.showError(this, "Ошибка добавления:"+ex.getMessage());
            }
        });
    }

}
