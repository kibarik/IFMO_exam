package app.kibarik.learn.ui;

import app.kibarik.learn.entity.ProductEntity;
import app.kibarik.learn.manager.ProductEntityManager;
import app.kibarik.learn.utils.BaseForm;
import app.kibarik.learn.utils.DialogUtils;

import javax.swing.*;
import java.sql.SQLException;

public class ProductEntityCreate extends BaseForm {
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

    public ProductEntityCreate(){
        super(500, 500);
        setContentPane(mainPanel);


        initButtons();

        setVisible(true);
    }

    public void initButtons(){



        backButton.addActionListener(e -> {
            dispose();
            new ProductTable();
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

                DialogUtils.showInfo(this, "Добавление успешно");
                dispose();
                new ProductTable();
            } catch (SQLException ex) {
                DialogUtils.showError(this, "Ошибка создания: "+ex.getMessage());
            }
        });
    }

}
