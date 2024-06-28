package org.yearup.data.mysql;

import org.springframework.stereotype.Component;
import org.yearup.models.Profile;
import org.yearup.data.ProfileDao;

import javax.sql.DataSource;
import java.sql.*;

@Component
public class MySqlProfileDao extends MySqlDaoBase implements ProfileDao
{
    public MySqlProfileDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public Profile create(Profile profile)
    {
        String sql = "INSERT INTO profiles (user_id, first_name, last_name, phone, email, address, city, state, zip) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, profile.getUserId());
            ps.setString(2, profile.getFirstName());
            ps.setString(3, profile.getLastName());
            ps.setString(4, profile.getPhone());
            ps.setString(5, profile.getEmail());
            ps.setString(6, profile.getAddress());
            ps.setString(7, profile.getCity());
            ps.setString(8, profile.getState());
            ps.setString(9, profile.getZip());

            ps.executeUpdate();

            return profile;
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Profile getByUserId(int userId)
    {
        try(Connection connection = getConnection())
        {
            String sql = """
                    SELECT *
                    FROM profiles
                    WHERE user_id = ?;
                    """;
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,userId);

            ResultSet row = statement.executeQuery();
            while(row.next())
            {
                int user_Id = row.getInt("user_id");
                String fistName = row.getString("first_name");
                String lastName = row.getString("last_name");
                String email = row.getString("email");
                String phone = row.getString("phone");
                String state = row.getString("state");
                String zip = row.getString("zip");
                String address = row.getString("address");
                String city = row.getString("city");


                Profile profile = new Profile(user_Id,fistName,lastName,phone,email,address,city,state,zip);
                return profile;

            }


        }catch(SQLException e)
        {
            throw new RuntimeException("Error fetching profile for userId: " + userId, e);
        }
        return null;
    }

    @Override
    public Profile updateProfile(int usedId, Profile profile)
    {
        try(Connection connection = getConnection())
        {
            String sql = """
                    UPDATE profiles
                    SET first_name = ?
                    ,last_name = ?
                    ,phone = ?
                    , email = ?
                    ,address = ?
                    ,city = ?
                    ,state = ?
                    ,zip = ?
                    WHERE user_id = ?;
                    """;

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,profile.getFirstName());
            statement.setString(2,profile.getLastName());
            statement.setString(3,profile.getPhone());
            statement.setString(4,profile.getEmail());
            statement.setString(5,profile.getAddress());
            statement.setString(6,profile.getCity());
            statement.setString(7,profile.getState());
            statement.setString(8,profile.getZip());
            statement.setInt(9, usedId);

            statement.executeUpdate();


            return profile;

        }
        catch(SQLException e)
        {

        }
return  null;
    }

}
