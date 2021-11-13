package business;

import java.sql.SQLException;

import model.dataccess.LoginDataAccess;
import model.entities.MessageException;
import model.entities.User;
import view.LoginSuccessView;
import model.entities.User;

public class LoginBusiness 
{
	private static LoginDataAccess dataAccess=new LoginDataAccess(); 
	
	public static LoginDataAccess returnDataAccess()
	{
		return dataAccess; 
	}
	public static void validateField(String userName, String password)
	{
		if(userName.equals(""))
		{
			throw new MessageException("Username not informed.");
		}
		else if(password.equals(""))
		{
			throw new MessageException("Password not informed.");
		}
		
	}
	public static void validateUser(String userName, String password) throws ClassNotFoundException, SQLException
	{
		User user = new User(userName, password);
		
		if (!(dataAccess.verifyCredentials(user))) {
			throw new MessageException("Incorrect credentials.");
		}
	}

}
