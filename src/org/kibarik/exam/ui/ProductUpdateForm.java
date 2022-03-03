package org.kibarik.exam.ui;

import org.kibarik.exam.entity.ProductEntity;
import org.kibarik.exam.manager.ProductEntityManager;
import org.kibarik.exam.ui.BaseForm;
import org.kibarik.exam.ui.ProductTableForm;
import org.kibarik.exam.utils.DialogUtils;

import javax.swing.*;
import java.sql.SQLException;

public class ProductUpdateForm extends BaseForm {
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
    private JButton deleteButton;
    private JTextField idField;

    ProductEntity product;

    ProductUpdateForm(ProductEntity product){
        super(600, 600);
        this.product = product;

        initFields();
        initButtons();
        setContentPane(mainPanel);

        setVisible(true);
    }

    public void initFields(){
        idField.setText(String.valueOf(product.getId()));
        titleField.setText(product.getTitle());
        productionTypeComoBox.setSelectedItem(product.getProductType());
        articleField.setText(product.getArticleNumber());
        imageField.setText(product.getImage());
        descriptionField.setText(product.getDescription());
        personalCountField.setValue(product.getProductionPersonCount());
        factoryNumberField.setValue(product.getProductionWorkshopNumber());
        minCostField.setText(String.valueOf(product.getMinCostForAgent()));
    }

    public void initButtons(){
        deleteButton.addActionListener(e->{
            try {
                int is_bool = JOptionPane.showConfirmDialog(this, "Вы точно хотите удалить?", "Подтвердите удаление", JOptionPane.YES_NO_OPTION);
                if(is_bool == JOptionPane.YES_OPTION){
                    ProductEntityManager.delete(product);
                    dispose();
                    new ProductTableForm();
                }
            } catch (SQLException ex) {
                DialogUtils.showError(this, "Ошибка удаления:"+ex.getMessage());
            }
        });

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

            product.setTitle(title);
            product.setProductType(productType);
            product.setArticleNumber(articleNumber);
            product.setDescription(description);
            product.setImage(image);
            product.setProductionPersonCount(productionPersonCount);
            product.setProductionWorkshopNumber(productionWorkshopNumber);
            product.setMinCostForAgent(minCostForAgent);

            try {
                ProductEntityManager.update(product);
                DialogUtils.showInfo(this, "Обновление успешно!");
                dispose();
                new ProductTableForm();
            } catch (SQLException ex) {
                DialogUtils.showError(this, "Ошибка добавления:"+ex.getMessage());
            }
        });
    }

}
