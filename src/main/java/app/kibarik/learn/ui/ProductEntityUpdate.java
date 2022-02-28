package app.kibarik.learn.ui;

import app.kibarik.learn.entity.ProductEntity;
import app.kibarik.learn.manager.ProductEntityManager;
import app.kibarik.learn.utils.BaseForm;
import app.kibarik.learn.utils.DialogUtils;

import javax.swing.*;
import java.sql.SQLException;

public class ProductEntityUpdate extends BaseForm {
    private JPanel mainPanel;
    private JTextField titleField;
    private JComboBox ProductTypecomboBox;
    private JSpinner productionPersonCountField;
    private JTextField minCostForAgentField;
    private JTextField imageField;
    private JSpinner productionWorkshopNumberField;
    private JTextArea desctiptionAreaField;
    private JLabel descriptionField;
    private JButton backButton;
    private JButton saveButton;
    private JTextField articleField;
    private JTextField idField;
    private JButton deleteButton;

    private ProductEntity product;

    public ProductEntityUpdate(ProductEntity product){
        super(500, 500);
        setContentPane(mainPanel);
        this.product = product;

        initFields();
        initButtons();

        setVisible(true);
    }

    public void initFields(){
        idField.setText(String.valueOf(product.getId()));
        titleField.setText(String.valueOf(product.getTitle()));
        articleField.setText(String.valueOf(product.getArticleNumber()));
        ProductTypecomboBox.setSelectedItem(String.valueOf(product.getArticleNumber()));
        imageField.setText(String.valueOf(product.getImage()));
        productionPersonCountField.setValue(product.getProductionPersonCount());
        productionWorkshopNumberField.setValue(product.getProductionWorkshopNumber());
        minCostForAgentField.setText(String.valueOf(product.getMinCostForAgent()));
        desctiptionAreaField.setText(String.valueOf(product.getDescription()));
    }

    public void initButtons(){

        backButton.addActionListener(e -> {
            dispose();
            new ProductTable();
        });

        deleteButton.addActionListener(e->{
            try {
                int is_delete = JOptionPane.showConfirmDialog(this, "Точно хотите удалить?", "Подтвердите удаление", JOptionPane.YES_NO_OPTION);
                if (is_delete == JOptionPane.YES_OPTION){
                    ProductEntityManager.delete(product);
                    DialogUtils.showInfo(this, "Удаление успешно!");
                    dispose();
                    new ProductTable();
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
                DialogUtils.showError(this, "Ошибка удаления");
            }
        });




        saveButton.addActionListener(e->{
            try {

                String title = titleField.getText();
                String productType = String.valueOf(ProductTypecomboBox.getSelectedItem());
                String articleNumber = articleField.getText();
                String description = desctiptionAreaField.getText();
                String image = imageField.getText();
                int productionPersonCount = (int) productionPersonCountField.getValue();
                int productionWorkshopNumber = (int) productionWorkshopNumberField.getValue();

                double minCostForAgent = Double.parseDouble(minCostForAgentField.getText());


                ProductEntityManager.insert(
                        new ProductEntity(
                                -1,
                                title,
                                productType,
                                articleNumber,
                                description,
                                image,
                                productionPersonCount,
                                productionWorkshopNumber,
                                minCostForAgent
                        )
                );

                DialogUtils.showInfo(this, "Обновление успешно");
                new ProductTable();
            } catch (SQLException ex) {
                DialogUtils.showError(this, "Ошибка создания: "+ex.getMessage());
            }
        });
    }

}
