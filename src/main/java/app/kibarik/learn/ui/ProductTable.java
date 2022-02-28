package app.kibarik.learn.ui;

import app.kibarik.learn.entity.ProductEntity;
import app.kibarik.learn.manager.ProductEntityManager;
import app.kibarik.learn.utils.BaseForm;
import app.kibarik.learn.utils.CustomTableModel;
import app.kibarik.learn.utils.DialogUtils;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.sql.SQLException;

public class ProductTable extends BaseForm {

    private JPanel mainPanel;
    private JTable table;

    public ProductTable() {
        super(800, 600);
        setContentPane(mainPanel);
        initTables();

        setVisible(true);
    }

    public void initTables(){
        try {
            CustomTableModel<ProductEntity> model = new CustomTableModel(
                    new String[] {"ID", "Название", "Тип продукции", "Артикул", "Описание", "Путь к изображению", "Изображение", "Количество пользователей цеха", "Номер производственного цеха",  "Минимальная стоимость для агента", "Изображение"},
                    ProductEntity.class,
                    ProductEntityManager.selectAll()
            );
            table.setModel(model);
            table.setRowHeight(50);

            TableColumn column = table.getColumn("Путь к изображению");
            column.setMaxWidth(0);
            column.setMinWidth(0);
            column.setWidth(0);


        } catch (SQLException e) {
            DialogUtils.showError(this, "Ошибка получения данных: "+e.getMessage());
        }
    }
}
