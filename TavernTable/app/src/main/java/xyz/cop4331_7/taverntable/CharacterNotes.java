package xyz.cop4331_7.taverntable;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CharacterNotes extends AppCompatActivity implements Parcelable{

    static final String urlGet = "http://cop4331-7.xyz/GET/getNotes.php";
    static final String urlSet = "http://cop4331-7.xyz/SET/setNotes.php";
    EditText content;
    String notes;
    Intent intent;
    int characterID;

    protected CharacterNotes(Parcel in) {
        notes = in.readString();
        intent = in.readParcelable(Intent.class.getClassLoader());
        characterID = in.readInt();
    }

    protected CharacterNotes(){

    }

    public static final Creator<CharacterNotes> CREATOR = new Creator<CharacterNotes>() {
        @Override
        public CharacterNotes createFromParcel(Parcel in) {
            return new CharacterNotes(in);
        }

        @Override
        public CharacterNotes[] newArray(int size) {
            return new CharacterNotes[size];
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //define the text field
        content = (EditText) findViewById(R.id.notesContent);

        //get the character ID
        intent = getIntent();
        characterID = intent.getIntExtra("characterID",-1);

        //if there was a character ID, go get its notes
        if(characterID > -1)
            getNotes(characterID);
    }


    private void getNotes(final int characterID){

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlGet,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response);
                            String note = jsonResponse.getString("notes"),
                                    error = jsonResponse.getString("error");

                            if(error.equals("")){

                                //success
                                content.setText(note);
                            }

                            else{

                                //fail
                            }

                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<>();

                // POST params
                params.put("characterID", String.valueOf(characterID));

                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

    }


    @Override
    public void onBackPressed(){

        //save text currently in the text field
        notes = content.getText().toString();

        StringRequest postRequest = new StringRequest(Request.Method.POST, urlSet,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String error = jsonResponse.getString("error");

                            if(error.equals("")){

                                //success
                                finish();

                            }else{

                                //fail
                                AlertDialog alert = new AlertDialog.Builder(CharacterNotes.this).create();
                                alert.setMessage("Whoops didn't save lol sorry");
                                alert.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        });
                                alert.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        )
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                // POST parameters
                params.put("notes", notes);
                params.put("characterID", String.valueOf(characterID));

                return params;
            }
        };

        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

        finish();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(notes);
    }
}
