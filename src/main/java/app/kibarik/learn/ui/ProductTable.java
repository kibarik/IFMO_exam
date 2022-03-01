package app.kibarik.learn.ui;

import app.kibarik.learn.entity.ProductEntity;
import app.kibarik.learn.manager.ProductEntityManager;
import app.kibarik.learn.utils.BaseForm;
import app.kibarik.learn.utils.CustomTableModel;
import app.kibarik.learn.utils.DialogUtils;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ProductTable extends BaseForm {

    private JPanel mainPanel;
    private JTable table;
    private JButton createButton;
    private JButton фильтроватьПоТипуПродуктаButton;
    private JComboBox typeFilterBox;
    private JComboBox sortBox;

    CustomTableModel<ProductEntity> model;

    public ProductTable() {
        super(800, 600);
        setContentPane(mainPanel);

        initButtons();
        initTables();
        initBoxes();

        setVisible(true);
    }

    public void initButtons(){
        createButton.addActionListener(e -> {
            dispose();
            new ProductEntityCreate();
        });

    }

    public void initBoxes(){
        typeFilterBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                applyFilters();
            }
        });

        sortBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                applyFilters();
            }
        });
    }

    public void applyFilters(){
        try {
            List<ProductEntity>  list = ProductEntityManager.selectAll();

            switch (typeFilterBox.getSelectedIndex()){
                case 1:
                    list.removeIf(s -> !s.getProductType().equals("Держители"));
                    break;
                case 2:
                    list.removeIf(s -> !s.getProductType().equals("Запасные части"));
                case 3:
                    list.removeIf(s -> !s.getProductType().equals("Маски"));
                    break;
                case 4:
                    list.removeIf(s -> !s.getProductType().equals("На лицо"));
                    break;
                case 5:
                    list.removeIf(s -> !s.getProductType().equals("Повязки"));
                    break;
                case 6:
                    list.removeIf(s -> !s.getProductType().equals("Полнолицевые"));
                    break;
            }

            model.setRows(list);
            model.fireTableDataChanged();
        } catch (SQLException e) {
            e.printStackTrace();
            DialogUtils.showError(null, "Невозможно применить фильтрацию по типу");
        }

        switch (sortBox.getSelectedIndex()){

            //По возрастанию "номер производственного цеха"
            //По убыванию "номер производственного цеха"
            //По возрастанию "Минимальная стоимость агента"
            //По убыванию "Минимальная стоимость агента"
            //По возрастанию "наименование"
            //По убыванию "наименование"
            case 1:
                Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return Integer.compare(o1.getProductionWorkshopNumber(), o2.getProductionWorkshopNumber());
                    }
                });
            break;

            case 2:
                Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return Integer.compare(o2.getProductionWorkshopNumber(), o1.getProductionWorkshopNumber());
                    }
                });
                break;


            case 3:
                Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return Double.compare(o1.getMinCostForAgent(), o2.getMinCostForAgent());
                    }
                });
                break;


            case 4:
                Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return Double.compare(o2.getMinCostForAgent(), o1.getMinCostForAgent());
                    }
                });
                break;


            case 5:
                Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return o1.getTitle().compareTo(o2.getTitle());
                    }
                });
                break;

            case 6:
                Collections.sort(model.getRows(), new Comparator<ProductEntity>() {
                    @Override
                    public int compare(ProductEntity o1, ProductEntity o2) {
                        return o2.getTitle().compareTo(o1.getTitle());
                    }
                });
                break;
        }




    }

    public void initTables(){

        table.addMouseListener(new MouseAdapter() {
            /**
             * {@inheritDoc}
             *
             * @param e
             */
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if(e.getClickCount() == 2 ){
                    if(row!=-1){
                        dispose();
                        new ProductEntityUpdate(model.getRows().get(row));
                    }
                }
                super.mouseClicked(e);
            }
        });

        try {
            model = new CustomTableModel(
                    new String[] {"ID", "Название", "Тип продукции", "Артикул", "Описание", "Путь к изображению", "Количество пользователей цеха", "Номер производственного цеха",  "Минимальная стоимость для агента", "Изображение"},
                    ProductEntity.class,
                    ProductEntityManager.selectAll()
            );
            table.setModel(model);
            table.setRowHeight(50);

//            TableColumn column = table.getColumn("Путь к изображению");
//            column.setMaxWidth(0);
//            column.setMinWidth(0);
//            column.setWidth(0);


        } catch (SQLException e) {
            DialogUtils.showError(this, "Ошибка получения данных: "+e.getMessage());
        }
    }
}

//артикул, наименование, тип продукта (выпадающий список), изображение, количество человек для производства, номер производственного цеха, минимальная стоимость для агента и подробное описание (с возможностью многострочного ввода).
