package org.kibarik.exam.ui;

import org.kibarik.exam.entity.ProductEntity;
import org.kibarik.exam.manager.ProductEntityManager;
import org.kibarik.exam.utils.BaseForm;
import org.kibarik.exam.utils.CustomTableModel;
import org.kibarik.exam.utils.DialogUtils;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class ProductTableForm extends BaseForm {
    private JTable table;
    private JButton addButton;
    private JPanel mainPanel;
    private JComboBox sortBox;

    CustomTableModel<ProductEntity> model;

    public ProductTableForm(){
        super(1000,600);
        setContentPane(mainPanel);

        initButtons();
        initTables();
        initBoxes();

        setVisible(true);
    }

    public void initTables(){
        try {
            model = new CustomTableModel(
                    ProductEntity.class,
                    new String[]{"ID", "Название", "Тип продукции", "Артикул", "Описание", "Путь к фото", "Количество персонала", "Номер цеха", "Минимальная цена для агента", "Изображение"},
                    ProductEntityManager.selectAll()
            );
            table.setModel(model);
            table.setRowHeight(50);

        } catch (SQLException e) {
            DialogUtils.showError(this, "Ошибка: "+e.getMessage());
        }
    }

//    По-умолчанию
//    По возрастанию ID
//    По убыванию ID
//    По возрастанию Title
//    По убыванию Title
//    По возрастанию minCostForAgent
//    По убыванию minCostForAgent
//    По возрастанию Номер цеха
//    По убыванию Номер цеха
    public void initBoxes(){
        switch (sortBox.getSelectedIndex()){
            case 1:
                Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return Integer.compare(o1.getId(), o2.getId());
                    }
                });
                break;

            case 2:
                Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return Integer.compare(o2.getId(), o1.getId());
                    }
                });
                break;

            case 3:
                Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                });
                break;

            case 4:
                Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return o2.getTitle().compareTo(o1.getTitle());
                    }
                });
                break;
        }

    }

    public void initButtons(){
        addButton.addActionListener(e -> {
            new ProductCreateForm();
            dispose();
        });



        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2){
                    int row = table.rowAtPoint(e.getPoint());
                    if(row != -1) {
                        new ProductUpdateForm(model.getRows().get(row));
                        dispose();
                    }
                }

                super.mouseClicked(e);
            }
        });
    }
}
