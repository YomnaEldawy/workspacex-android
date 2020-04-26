package com.example.workspacex.data;

import com.example.workspacex.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        return null;
    }

    public void logout() {
        // TODO: revoke authentication
    }
}
