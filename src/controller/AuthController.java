package controller;
import data.DataStore;
import model.User;

public class AuthController {
    private DataStore ds = DataStore.getInstance();
    public User login(String email, String password) {
        return ds.login(email, password);
    }
}
