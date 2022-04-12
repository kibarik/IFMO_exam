package org.kibarik.exam.ui;

import org.kibarik.exam.entity.ProductEntity;
import org.kibarik.exam.manager.ProductEntityManager;
import org.kibarik.exam.utils.BaseForm;
import org.kibarik.exam.utils.DialogUtils;

import javax.swing.*;
import java.sql.SQLException;

public class ProductUpdateForm extends BaseForm {
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
    private JButton deleteButton;
    private JTextField idField;

    ProductEntity product;

    public ProductUpdateForm(ProductEntity product){
        super(600,600);
        setContentPane(mainPanel);
        this.product = product;

        initFields();
        initButtons();

        setVisible(true);
    }

    public void initFields(){
        idField.setText(String.valueOf(product.getId()));
        titleField.setText(String.valueOf(product.getTitle()));
        productTypeField.setText(String.valueOf(product.getProductType()));
        articleNumberField.setText(String.valueOf(product.getArticleNumber()));
        descriptionField.setText(String.valueOf(product.getDescription()));
        imageField.setText(String.valueOf(product.getImage()));
        productionPersonCountSpinner.setValue( product.getProductionPersonCount() );
        productionWorkshopNumberSpinner.setValue( product.getProductionWorkshopNumber() );
        minCostField.setText(String.valueOf(product.getMinCostForAgent()));
    }

    public void initButtons(){
        backButton.addActionListener(e -> {
            dispose();
            new ProductTableForm();
        });

        deleteButton.addActionListener(e -> {
            int isTrue = JOptionPane.showConfirmDialog(this, "Вы уверены, что хотите удалить продукт?", "Подтвердить удаление", JOptionPane.YES_NO_OPTION);
            if(isTrue == JOptionPane.YES_OPTION){
                try {
                    ProductEntityManager.delete(product);
                    DialogUtils.showInfo(this, "Удаление прошло успешно!");
                } catch (SQLException ex) {
                    DialogUtils.showInfo(this, "Ошибка SQL: "+ex.getMessage());
                }
            }
        });

        addButton.addActionListener(e -> {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            String productType = productTypeField.getText();
            String articleNumber = articleNumberField.getText();
            String description = descriptionField.getText();
            String image = imageField.getText();
            int productionPersonCount = (int) productionPersonCountSpinner.getValue();
            int productionWorkshopNumber = (int) productionWorkshopNumberSpinner.getValue();
            double minCostForAgent = Double.parseDouble(minCostField.getText());


            try {
                ProductEntityManager.update(new ProductEntity(
                    id,
                    title,
                    productType,
                    articleNumber,
                    description,
                    image,
                    productionPersonCount,
                    productionWorkshopNumber,
                    minCostForAgent
                ));

                DialogUtils.showInfo(this, "Продукт обновлен!");
            } catch (SQLException ex) {
                DialogUtils.showInfo(this, "Ошибка SQL: "+ex.getMessage());
            }
        });

    }



}
