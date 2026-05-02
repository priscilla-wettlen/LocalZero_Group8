package main.java.client.user_actions;


/// The idea with this abstract class is to set a base command for all requests
/// and then allow each command (the user actions) to implement execute() however they need.
public abstract class BaseUserAction implements IUserAction {



    public void execute(){};
}
