package com.example.workspacex.callbacks;

import org.json.JSONArray;
import org.json.JSONException;

public interface ServerCallback {
    void onSuccess(JSONArray response);
}
