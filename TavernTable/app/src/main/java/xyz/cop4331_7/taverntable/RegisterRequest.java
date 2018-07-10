package xyz.cop4331_7.taverntable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    private static final String REGISTER_REQ_URL = "http://cop4331-7.xyz/signUp.php";
    private Map<String, String> params;

    public RegisterRequest(String username, String password, String email, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQ_URL, listener, null);
    }
}
