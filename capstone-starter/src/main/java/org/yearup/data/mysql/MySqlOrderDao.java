package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.data.OrderDao;
import org.yearup.data.ProductDao;
import org.yearup.models.Order;
import org.yearup.models.Product;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
//import java.util.Date;


@Component
public class MySqlOrderDao extends MySqlDaoBase implements OrderDao
{
    ProductDao productDao;

    public MySqlOrderDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Order createOrder(Order order)
    {
        try(Connection connection = getConnection())
        {
            String sql =
                    """
                    INSERT INTO orders (user_id, date, address, city, state, zip, shipping_amount)
                    VALUES (?, ?, ?, ?, ?, ?, ?);
                    """;

            PreparedStatement pstmt = connection.prepareStatement(sql);

            pstmt.setInt(1,order.getUser_id());
            pstmt.setDate(2, Date.valueOf(order.getDate()));
            pstmt.setString(3,order.getAddress());
            pstmt.setString(4, order.getCity());
            pstmt.setString(5, order.getState());
            pstmt.setString(6, order.getZip());
            pstmt.setDouble(7, order.getShipping_amount());

           int result =  pstmt.executeUpdate();

            System.out.println(result);

            return order;


        }catch(Exception e)
        {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }



    public Order getOrderByUserId(int userId){

        String sql = "select * from orders where user_id = ?";

        Order order = new Order();

        try(Connection connection = getConnection()){

            PreparedStatement pstmt = connection.prepareStatement((sql));
            pstmt.setInt(1,userId);

            ResultSet rs = pstmt.executeQuery();

            while ((rs.next())) {
                order = mapRow(rs);
                     return order;
            }

        } catch(Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

return null;
    }


    protected static Order mapRow(ResultSet row) throws SQLException
    {
        int order_id = row.getInt("order_id");
        int userId = row.getInt("user_id");
        LocalDate date = row.getDate("date").toLocalDate();
        String address = row.getString("address");
        String city= row.getString("city");
        String state = row.getString("state");
        String zip = row.getString("zip");
        double shipping_Amount = row.getDouble("shipping_amount");

        return new Order(order_id, userId, date,address, city, state, zip, shipping_Amount);

        //System.out.println("Testing to see if this works");
       // return new Order();
    }




}
