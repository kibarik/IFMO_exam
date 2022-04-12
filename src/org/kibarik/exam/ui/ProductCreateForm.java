package org.kibarik.exam.ui;

import org.kibarik.exam.entity.ProductEntity;
import org.kibarik.exam.manager.ProductEntityManager;
import org.kibarik.exam.utils.BaseForm;
import org.kibarik.exam.utils.DialogUtils;

import javax.swing.*;
import java.sql.SQLException;

public class ProductCreateForm extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JTextArea descriptionField;
    private JCheckBox booleanField;
    private JTextField productTypeField;
    private JTextField articleNumberField;
    private JTextField imageField;
    private JTextField minCostField;
    private JTextField DateField;
    private JTextField DatetimeField;
    private JButton backButton;
    private JButton addButton;
    private JSpinner productionPersonCountSpinner;
    private JSpinner productionWorkshopNumberSpinner;

    public ProductCreateForm(){
        super(600,600);
        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }

    public void initButtons(){
        backButton.addActionListener(e -> {
            dispose();
            new ProductTableForm();
        });

        addButton.addActionListener(e -> {

            String title = titleField.getText();
            String productType = productTypeField.getText();
            String articleNumber = articleNumberField.getText();
            String description = descriptionField.getText();
            String image = imageField.getText();
            int productionPersonCount = (int) productionPersonCountSpinner.getValue();
            int productionWorkshopNumber = (int) productionWorkshopNumberSpinner.getValue();
            double minCostForAgent = Double.parseDouble(minCostField.getText());


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

                DialogUtils.showInfo(this, "Новый продукт добавлен в таблицу!");
            } catch (SQLException ex) {
                DialogUtils.showInfo(this, "Ошибка SQL: "+ex.getMessage());
            }
        });

    }



}
