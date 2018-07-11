package xyz.cop4331_7.taverntable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest
{
    private static final String REGISTER_REQUEST_URL = "http://cop4331-7.xyz/tavernTable/SignUp.php";
    private Map<String, String> params;

    // Will have to update later for email
    public RegisterRequest(String username, String password, Response.Listener<String> listener)
    {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);

        // Send the parameters
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
