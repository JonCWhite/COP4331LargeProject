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
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class CampaignNotes extends AppCompatActivity implements Parcelable{

    static final String urlGet = "http://cop4331-7.xyz/GET/getNotesDM.php";
    static final String urlSet = "http://cop4331-7.xyz/SET/setNotesDM.php";
    EditText content;
    String notes;
    Intent intent;
    int campaignID;

    protected CampaignNotes(Parcel in) {
        notes = in.readString();
        intent = in.readParcelable(Intent.class.getClassLoader());
        campaignID = in.readInt();
    }

    protected CampaignNotes(){

    }

    public static final Creator<CampaignNotes> CREATOR = new Creator<CampaignNotes>() {
        @Override
        public CampaignNotes createFromParcel(Parcel in) {
            return new CampaignNotes(in);
        }

        @Override
        public CampaignNotes[] newArray(int size) {
            return new CampaignNotes[size];
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_notes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("DM Notes");

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        //define the text field
        content = (EditText) findViewById(R.id.notesContent);

        //get the character ID
        intent = getIntent();
        campaignID = intent.getIntExtra("campaignID",-1);

        //if there was a character ID, go get its notes
        if(campaignID > -1)
            getNotes(campaignID);
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
                params.put("campaignID", String.valueOf(campaignID));

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
                                AlertDialog alert = new AlertDialog.Builder(CampaignNotes.this).create();
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
                params.put("campaignID", String.valueOf(campaignID));

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