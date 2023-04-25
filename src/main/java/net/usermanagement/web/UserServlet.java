package net.usermanagement.web;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.usermanagement.dao.UserDao;
import net.usermanagement.model.User;
/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       private UserDao userDao;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserServlet() {
       this.userDao = new UserDao();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
		
		switch (action) {
		case "/new":
			showNewForm(request, response);
			break;
		case "/insert":
			try {
				insertUser(request, response);
			} catch (SQLException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			} catch (IOException e4) {
				// TODO Auto-generated catch block
				e4.printStackTrace();
			}
			break;
		case "/delete":
			 try {
				deleteUser(request, response);
			} catch (SQLException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			break;
		case "/edit" :
			try {
				showEditForm(request, response);
			} catch (SQLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (ServletException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			break;
		case "/update":
			try {
				updateUser(request, response);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			break;
		 default :
			 try {
				listUser(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		}
	}
	 
	 
	// Starting to write methods for execution!
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
		    throws ServletException, IOException {
		        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
		        dispatcher.forward(request, response);
		    }
	
	 private void insertUser(HttpServletRequest request, HttpServletResponse response)
			    throws SQLException, IOException {
			        String name = request.getParameter("name");
			        String email = request.getParameter("email");
			        String country = request.getParameter("country");
			        User newUser = new User(name, email, country);
			        userDao.insertUser(newUser);
			        response.sendRedirect("list");
			    }
	 
	 private void deleteUser(HttpServletRequest request, HttpServletResponse response)
			    throws SQLException, IOException {
			        int id = Integer.parseInt(request.getParameter("id"));
			        userDao.deleteUser(id);
			        response.sendRedirect("list");

			    }
	 
	 private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			    throws SQLException, ServletException, IOException {
			        int id = Integer.parseInt(request.getParameter("id"));
			        User existingUser = userDao.selectUser(id);
			        RequestDispatcher dispatcher = request.getRequestDispatcher("user-form.jsp");
			        request.setAttribute("user", existingUser);
			        dispatcher.forward(request, response);

			    }
	
	 private void updateUser(HttpServletRequest request, HttpServletResponse response)
			    throws SQLException, IOException {
			        int id = Integer.parseInt(request.getParameter("id"));
			        String name = request.getParameter("name");
			        String email = request.getParameter("email");
			        String country = request.getParameter("country");

			        User book = new User(id, name, email, country);
			        userDao.updateUser(book);
			        response.sendRedirect("list");
			    }
	
	 private void listUser(HttpServletRequest request, HttpServletResponse response)
			    throws SQLException, IOException, ServletException {
			        List < User > listUser = userDao.selectAllUsers();
			        request.setAttribute("listUser", listUser);
			        RequestDispatcher dispatcher = request.getRequestDispatcher("user-list.jsp");
			        dispatcher.forward(request, response);
			    }
}
