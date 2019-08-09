package net.androidbootcamp.chatterbox.Requests;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ActiveChatRequest extends StringRequest {

    private static final String GetActiveChat_REQUEST_URL = "http://teamblues.x10host.com/GetActiveChat.php";

    //private static final String GetActiveChat_REQUEST_URL = "http://192.168.1.90/api/GetActiveChat.php";
    private Map<String, String> params;

    public ActiveChatRequest(String user_id, Response.Listener<String> listener) {
        super(Method.POST, GetActiveChat_REQUEST_URL, listener, null);
        params = new HashMap<>();

        params.put("user_id", user_id);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }

}