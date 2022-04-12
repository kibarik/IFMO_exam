package org.kibarik.exam.manager;

import org.kibarik.exam.App;
import org.kibarik.exam.entity.ProductEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductEntityManager {

    public static List<ProductEntity> selectAll() throws SQLException {
        try (Connection c = App.getConnection()){
            String sql = "SELECT * FROM product";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql);

            List<ProductEntity> list = new ArrayList<>();
            while (rs.next()){
                list.add(new ProductEntity(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDouble(9)
                ));
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }

    public static void insert(ProductEntity product) throws SQLException {
        try (Connection c = App.getConnection()){
            String sql = "INSERT INTO product(Title, ProductType, ArticleNumber, Description, Image, ProductionPersonCount, ProductionWorkshopNumber, MinCostForAgent) VALUES(?,?,?,?,?,?,?,?)";
            PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, product.getTitle());
            st.setString(2, product.getProductType());
            st.setString(3, product.getArticleNumber());
            st.setString(4, product.getDescription());
            st.setString(5, product.getImage());
            st.setInt(6, product.getProductionPersonCount());
            st.setInt(7, product.getProductionWorkshopNumber());
            st.setDouble(8, product.getMinCostForAgent());
            st.executeUpdate();

            ResultSet rs = st.getGeneratedKeys();
            while (rs.next()){
                product.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }

    public static void update(ProductEntity product) throws SQLException {
        try (Connection c = App.getConnection()){
            String sql = "UPDATE product SET Title=?, ProductType=?, ArticleNumber=?, Description=?, Image=?, ProductionPersonCount=?, ProductionWorkshopNumber=?, MinCostForAgent=? WHERE ID=?";
            PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, product.getTitle());
            st.setString(2, product.getProductType());
            st.setString(3, product.getArticleNumber());
            st.setString(4, product.getDescription());
            st.setString(5, product.getImage());
            st.setInt(6, product.getProductionPersonCount());
            st.setInt(7, product.getProductionWorkshopNumber());
            st.setDouble(8, product.getMinCostForAgent());
            st.setInt(9, product.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }

     public static void delete(ProductEntity product) throws SQLException {
            try (Connection c = App.getConnection()){
                String sql = "DELETE FROM product WHERE ID=?";
                PreparedStatement st = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                st.setInt(1, product.getId());
                st.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
                throw new SQLException(e.getMessage());
            }
        }


}
