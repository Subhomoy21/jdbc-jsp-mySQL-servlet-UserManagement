package net.usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.usermanagement.model.User;

// This Dao class provides CRUD database operations for the table users in the database
public class UserDao {
	static final String DB_URL = "jdbc:mysql://localhost:3306/crud";
	static final String USER = "root";
	static final String PASS = "";
	
	private static final String INSERT_USERS_SQL = "INSERT INTO users" + "  (name, email, country) VALUES " +
	        " (?, ?, ?);";

	    private static final String SELECT_USER_BY_ID = "select id,name,email,country from users where id =?";
	    private static final String SELECT_ALL_USERS = "select * from users";
	    private static final String DELETE_USERS_SQL = "delete from users where id = ?;";
	    private static final String UPDATE_USERS_SQL = "update users set name = ?,email= ?, country =? where id = ?;";
	    
	    public UserDao() {}

	    protected Connection getConnection() {
	        Connection connection = null;
	        try {
	            Class.forName("com.mysql.jdbc.Driver");
	            connection = DriverManager.getConnection(DB_URL, USER, PASS);
	        } 
	        catch (SQLException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        catch (ClassNotFoundException e) {
	            // TODO Auto-generated catch block
	            e.printStackTrace();
	        }
	        return connection;
	    }
	   //	PROCESS NUMBER - 1
	    // Create or insert user
	    // User user means inserting the Class methods in the user table!
	    public  void insertUser(User user) throws SQLException {
	    	 try(Connection connection = getConnection();
	    			 			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)){
	    		 preparedStatement.setString(1, user.getName());
	    		 preparedStatement.setString(2, user.getEmail());
	    		 preparedStatement.setString(3, user.getCountry());
	    		 preparedStatement.executeUpdate();
	    	 }
	    	 catch(Exception e) {
	    		 e.printStackTrace();
	    	 }
	    }
	    // PROCESS NUMBER - 2
	    // Update user 
	    public boolean updateUser (User user) throws SQLException {
	    	boolean rowUpdated;
	    	try(Connection connection = getConnection();
	    						PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);){
	    		statement.setString(1, user.getName());
	    		statement.setString(2, user.getEmail());
	    		statement.setString(3, user.getCountry());
	    		statement.setInt(4, user.getId());
	    		
	    		rowUpdated = statement.executeUpdate() > 0;
	    	}
	    	return rowUpdated;
	    }
	    // PROCESS NUMBER 3	
	    // Select user by id
	    public User selectUser(int id) {
	        User user = null;
	        // Step 1: Establishing a Connection
	        try (Connection connection = getConnection();
	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);) {
	            preparedStatement.setInt(1, id);
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String country = rs.getString("country");
	                user = new User(id, name, email, country);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return user;
	    }
	    // PROCESS NUMBER 4
	    // MOST IMPORTANT PART
	    // Select users
	    public  List <User> selectAllUsers() {
	        List <User> users = new ArrayList<>();
	        // Step 1: Establishing a Connection
	        try (Connection connection = getConnection();
	            // Step 2:Create a statement using connection object
	            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS );) {
	            System.out.println(preparedStatement);
	            // Step 3: Execute the query or update query
	            ResultSet rs = preparedStatement.executeQuery();

	            // Step 4: Process the ResultSet object.
	            while (rs.next()) {
	            	int id  =  rs.getInt("id");
	                String name = rs.getString("name");
	                String email = rs.getString("email");
	                String country = rs.getString("country");
	                users.add( new User(id, name, email, country));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return users;
	    }
	    // PROCESS NUMBER 5
	    // Delete User
	    public boolean deleteUser(int id) throws SQLException {
	        boolean rowDeleted;
	        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
	            statement.setInt(1, id);
	            rowDeleted = statement.executeUpdate() > 0;
	        }
	        return rowDeleted;
	    }
}
