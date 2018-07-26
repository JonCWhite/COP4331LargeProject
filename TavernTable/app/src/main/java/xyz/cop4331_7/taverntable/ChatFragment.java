package xyz.cop4331_7.taverntable;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatFragment extends Fragment {
    private Button bSend;
    private DatabaseReference roomRoot;
    private EditText etEntry;
    // Best practices for android say padding should be done in multiples of 8. I declared this
    // as a constant to avoid magic numbers.
    private static final int paddingBase = 8;
    private LinearLayout llMessages;
    private String tempKey, user_name, campaign_name;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.chat_fragment, container, false);

            // Initialize variables to corresponding interface components, to database, and to
            // argument variables.
            bSend = (Button) view.findViewById(R.id.bSend);
            etEntry = (EditText) view.findViewById(R.id.etMessageEntry);
            llMessages = (LinearLayout) view.findViewById(R.id.llMessages);
            user_name = getArguments().getString("user_name");
            campaign_name = getArguments().getString("campaign_name");

            roomRoot = FirebaseDatabase.getInstance().getReference().child(campaign_name);

            bSend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Map<String, Object> messageEntry = new HashMap<String, Object>();
                    // Create a new entry for the message in the database with a random key.
                    tempKey = roomRoot.push().getKey();
                    roomRoot.updateChildren(messageEntry);

                    // Create a reference to the entry we just created.
                    DatabaseReference messageRoot = roomRoot.child(tempKey);
                    Map<String, Object> messageContent = new HashMap<String, Object>();
                    messageContent.put("name", user_name);
                    messageContent.put("message", etEntry.getText().toString());

                    // Update the database.
                    messageRoot.updateChildren(messageContent);

                    etEntry.setText("");
                }
            });

            roomRoot.addChildEventListener(new ChildEventListener() {
                // Called when database is first connected too and again any time new child of
                // roomRoot is added.
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    appendChat(dataSnapshot);
                }

                // Called when a child of roomRoot is edited.
                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    appendChat(dataSnapshot);
                }

                // Called when a child of roomRoot is removed (we don't have plans to support this
                // for Tavern Table).
                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                // Called when a child of roomRoot is moved (we don't have plans to support this
                // for Tavern Table).
                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            return view;
    }

    // Populate chat with chat messages.
    private void appendChat(DataSnapshot dataSnapshot) {
        int padding;
        Iterator i = dataSnapshot.getChildren().iterator();
        String message, username;

        while (i.hasNext()) {
            message = (String) ((DataSnapshot)i.next()).getValue();
            username = (String) ((DataSnapshot)i.next()).getValue();

            // StackOverflow said it would be better to use activity context, but monitor
            // application performance in case this causes a memory leak.
            // Can be changed to getActivity().getApplicationContext() if need be.
            TextView usernameView = new TextView(getActivity());
            TextView messageView = new TextView(getActivity());
            if (username.equals(user_name)) {
                // format username
                usernameView.setText(username);
                usernameView.setGravity(android.view.Gravity.RIGHT);

                // format message
                messageView.setText(message);
                LinearLayout.LayoutParams textParam =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                textParam.gravity = android.view.Gravity.RIGHT;
                messageView.setLayoutParams(textParam);
                messageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                messageView.setTextColor(getResources().getColor(R.color.white));
                padding = getDPPaddingInPixels(paddingBase);
                messageView.setPadding(padding, padding, padding, padding);

                // Add user name and message to UI
                llMessages.addView(usernameView);
                llMessages.addView(messageView);
            }
            else {
                // format username
                usernameView.setText(username);
                usernameView.setGravity(android.view.Gravity.LEFT);

                // format message
                messageView.setText(message);
                messageView.setGravity(android.view.Gravity.LEFT);
                LinearLayout.LayoutParams textParam =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                textParam.gravity = android.view.Gravity.LEFT;
                messageView.setLayoutParams(textParam);
                messageView.setBackgroundColor(getResources().getColor(R.color.lightGray));

                // add user name and message to UI
                llMessages.addView(usernameView);
                llMessages.addView(messageView);
            }
        }
    }

    // Converts dp value to actual pixel value.
    private int getDPPaddingInPixels(int paddingDp) {
        float density = getActivity().getApplicationContext().getResources().getDisplayMetrics().density;
        int paddingPixel = (int)(paddingDp * density);
        return paddingPixel;
    }
}
