package org.kibarik.exam.ui;

import org.kibarik.exam.entity.ProductEntity;
import org.kibarik.exam.manager.ProductEntityManager;
import org.kibarik.exam.utils.CustomTableModel;
import org.kibarik.exam.utils.DialogUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class ProductTableForm extends BaseForm{
    private JTable table;
    private JPanel mainPanel;
    private JButton addButton;

    CustomTableModel<ProductEntity> model;

    public ProductTableForm(){
        super(1000, 600);
        setContentPane(mainPanel);

        initButtons();
        initTables();

        setVisible(true);
    }

    public void initButtons(){
        addButton.addActionListener(e -> {
            dispose();
            new ProductCreateForm();
        });
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

            table.addMouseListener(new MouseAdapter() {
                /**
                 * {@inheritDoc}
                 *
                 * @param e
                 */
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = table.rowAtPoint(e.getPoint());
                    if(e.getClickCount()==2){
                        if(row != -1){
                            new ProductUpdateForm(model.getRows().get(row));
                        }
                    }
                }
            });

        } catch (SQLException e) {
            DialogUtils.showError(this, "Ошибка получения данных: "+e.getMessage());
        }
    }

}
