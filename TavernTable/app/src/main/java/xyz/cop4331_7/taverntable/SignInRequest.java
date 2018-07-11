package xyz.cop4331_7.taverntable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class SignInRequest extends StringRequest
{
    private static final String LOGIN_REQUEST_URL = "http://cop4331-7.xyz/tavernTable/Login.php";
    private Map<String, String> params;


    // Might have to update later for signing in with email
    public SignInRequest(String username, String password, Response.Listener<String> listener)
    {
        super(Method.POST, LOGIN_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("username", username);
        params.put("password", password);
        //params.put("email", email);
    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
