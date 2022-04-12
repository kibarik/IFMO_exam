package org.kibarik.exam.ui;

import org.kibarik.exam.entity.ProductEntity;
import org.kibarik.exam.manager.ProductEntityManager;
import org.kibarik.exam.utils.BaseForm;
import org.kibarik.exam.utils.DialogUtils;

import javax.swing.*;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
            if(title.isBlank() || title.length() > 100){
                DialogUtils.showError(this, "Ошибка валидации! Title должен быть заполнен или длина меньше 100 символов");
                return;
            }

            String productType = productTypeField.getText();
            if(productType.isBlank() || productType.length() > 100){
                DialogUtils.showError(this, "Ошибка валидации! productType должен быть заполнен или длина меньше 100 символов");
                return;
            }

            String articleNumber = articleNumberField.getText();
            if(articleNumber.isBlank() || articleNumber.length() > 100){
                DialogUtils.showError(this, "Ошибка валидации! articleNumber должен быть заполнен или длина меньше 100 символов");
                return;
            }

            String description = descriptionField.getText();
            if(description.isBlank() || description.length() > 500){
                DialogUtils.showError(this, "Ошибка валидации! description должен быть заполнен или длина меньше 100 символов");
                return;
            }

            String image = imageField.getText();
            if(image.isBlank() || image.length() > 500){
                DialogUtils.showError(this, "Ошибка валидации! image должен быть заполнен или длина меньше 100 символов");
                return;
            }

            int productionPersonCount = (int) productionPersonCountSpinner.getValue();
            if(productionPersonCount < 0){
                DialogUtils.showError(this, "Ошибка валидации! productionPersonCount должен быть больше нуля");
                return;
            }

            int productionWorkshopNumber = (int) productionWorkshopNumberSpinner.getValue();
            if(productionWorkshopNumber < 0){
                DialogUtils.showError(this, "Ошибка валидации! productionWorkshopNumber должен быть больше нуля");
                return;
            }


            double minCostForAgent = 0.0;
            try {
                minCostForAgent = Double.parseDouble(minCostField.getText());
            } catch (NumberFormatException ex){
                DialogUtils.showError(this, "Ошибка валидации! minCostForAgent не соответствует типу Double");
                return;
            }

            String dftext = DateField.getText();
            try {
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                dateFormat.parse(dftext);
            } catch (ParseException ex) {
                DialogUtils.showError(this, "Ошибка валидации! DateField не соответствует дате");
                ex.printStackTrace();
                return;
            }


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
