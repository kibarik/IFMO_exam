package org.kibarik.exam.ui;

import org.kibarik.exam.entity.ProductEntity;
import org.kibarik.exam.manager.ProductEntityManager;
import org.kibarik.exam.utils.CustomTableModel;
import org.kibarik.exam.utils.DialogUtils;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductTableForm extends BaseForm{
    private JTable table;
    private JPanel mainPanel;
    private JButton addButton;
    private JComboBox typeComboBox;
    private JComboBox sortComboBox;

    CustomTableModel<ProductEntity> model;

    public ProductTableForm(){
        super(1000, 600);
        setContentPane(mainPanel);

        initBoxes();
        initButtons();
        initTables();

        setVisible(true);
    }

//    public void initBoxes(){
//        typeComboBox.addItemListener(e -> {
//            if(e.getStateChange() == ItemEvent.SELECTED){
//                applyFilters();
//            }
//        });
//    }
//
//    public void applyFilters(){
//        try {
//            List<ProductEntity> list = ProductEntityManager.selectAll();
//
//            switch (typeComboBox.getSelectedIndex()) {
//                case 1 -> list.removeIf(s -> Objects.equals(s.getProductType(), "Полумаски"));
//                case 2 -> list.removeIf(s -> s.getProductType().equals("Защитные маски"));
//            }
//
//            model.setRows(list);
//            model.fireTableDataChanged();
//
//            if(list.isEmpty()){
//                DialogUtils.showInfo(null, "В Таблице не осталось значений!");
//            }
//
//        } catch (SQLException e) {
//            DialogUtils.showError(null, "Ошибка получения данных.");
//            e.printStackTrace();
//        }
//
//    }

    public void initBoxes(){
        typeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange()==ItemEvent.SELECTED){
                    applyFilters();
                }
            }
        });

        sortComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    applyFilters();
                }
            }
        });
    }

    public void applyFilters(){
        try {
            List<ProductEntity> list = ProductEntityManager.selectAll();
            switch (typeComboBox.getSelectedIndex()) {
                case 1 -> list.removeIf(p -> !p.getProductType().equals("Повязки"));
                case 2 -> list.removeIf(p -> !p.getProductType().equals("Полумаски"));
                case 3 -> list.removeIf(p -> !p.getProductType().equals("Маски"));
            }

            switch (sortComboBox.getSelectedIndex()){
                case 1:
                    Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                        @Override
                        public int compare(ProductEntity o1, ProductEntity o2) {
                            return o1.getId() > o2.getId();
                        }
                    });
                break;

                case 2:
                    Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                        @Override
                        public int compare(ProductEntity o1, ProductEntity o2) {
                            return 0;
                        }
                    });
            }

        model.setRows(list);
        model.fireTableDataChanged();

        } catch (SQLException e) {
            e.printStackTrace();
        }
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
