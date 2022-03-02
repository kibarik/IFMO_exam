package org.kibarik.exam.manager;

import org.kibarik.exam.App;
import org.kibarik.exam.entity.ProductEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductEntityManager {

    public static List<ProductEntity> selectAll() throws SQLException {
        try(Connection c = App.getConnection()){
            String sql = "SELECT * FROM product";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);

            List<ProductEntity> list = new ArrayList<ProductEntity>();
            while (rs.next()){
                list.add(
                        new ProductEntity(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getInt(7),
                                rs.getInt(8),
                                rs.getDouble(9)
                        )
                );
            }
            return list;


        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void insert(ProductEntity product) throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "INSERT INTO product(Title, ProductType, ArticleNumber, Description, Image,  ProductionPersonCount, ProductionWorkshopNumber, MinCostForAgent) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getTitle());
            ps.setString(2, product.getProductType());
            ps.setString(3, product.getArticleNumber());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getImage());
            ps.setInt(6, product.getProductionPersonCount());
            ps.setInt(7, product.getProductionWorkshopNumber());
            ps.setDouble(8, product.getMinCostForAgent());

            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                product.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }


    public static void update(ProductEntity product) throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "UPDATE product(Title=?, ProductType=?, ArticleNumber=?, Description=?, Image=?,  ProductionPersonCount=?, ProductionWorkshopNumber=?, MinCostForAgent=?) WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getTitle());
            ps.setString(2, product.getProductType());
            ps.setString(3, product.getArticleNumber());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getImage());
            ps.setInt(6, product.getProductionPersonCount());
            ps.setInt(7, product.getProductionWorkshopNumber());
            ps.setDouble(8, product.getMinCostForAgent());
            ps.setInt(9, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }


    public static void delete(ProductEntity product) throws SQLException {
        try (Connection c = App.getConnection()) {
            String sql = "DELETE FROM product WHERE ID=?";
            PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, product.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException(e.getMessage());
        }
    }
}
