package org.kibarik.exam.ui;

import org.kibarik.exam.entity.ProductEntity;
import org.kibarik.exam.manager.ProductEntityManager;
import org.kibarik.exam.utils.CustomTableModel;
import org.kibarik.exam.utils.DialogUtils;

import javax.swing.*;
import java.sql.SQLException;

public class ProductTableForm extends BaseForm{
    private JTable table;
    private JPanel mainPanel;

    CustomTableModel<ProductEntity> model;

    public ProductTableForm(){
        super(1000, 600);
        setContentPane(mainPanel);

        initTables();

        setVisible(true);
    }

    public void initTables(){
        try {
            model = new CustomTableModel<>(
                    ProductEntity.class,
                    new String[]{"ID", "Название", "Тип продукции", "Артикул", "Описание", "Путь к фото", "Количество персонала", "Номер цеха", "Минимальная цена для агента", "Изображение"},
                    ProductEntityManager.selectAll()
                    );

            table.setModel(model);
            table.setRowHeight(50);

        } catch (SQLException e) {
            DialogUtils.showError(this, "Ошибка получения данных: "+e.getMessage());
        }
    }

}
