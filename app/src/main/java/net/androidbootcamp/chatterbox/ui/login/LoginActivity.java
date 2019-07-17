package net.androidbootcamp.chatterbox.ui.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import net.androidbootcamp.chatterbox.HelpActivity;
import net.androidbootcamp.chatterbox.MenuActivity;
import net.androidbootcamp.chatterbox.R;
import net.androidbootcamp.chatterbox.RegistrationActivity;
import net.androidbootcamp.chatterbox.encryption.Encrypt256;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {



    private TextView registerLink;
    private TextView helpLink;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);




        //Temp Chat Button
        final Button chatButton = findViewById(R.id.chatbutton);
        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //after click start start chat activity
                startActivity(new Intent(LoginActivity.this, MenuActivity.class));
            }
        });
        //End Temp Chat Button

        //this is the reference for the registration textview
        registerLink = (TextView)findViewById(R.id.registerLink);
        helpLink = (TextView)findViewById(R.id.helpLink);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = Encrypt256.getSHA(passwordEditText.getText().toString());



                //todo need to add data validation to username and password to make sure they are not blank and email is a valid address

                //Response from server
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        Log.e("onResponse", "Made it in onResponse");
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            int success = jsonResponse.getInt("success");


                            if (success == 1){
                                String username = jsonResponse.getString("userID");

                                Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                                intent.putExtra("username", username); // passes the username received from userID to the MenuActivity
                                LoginActivity.this.startActivity(intent);
                            }else{
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage(jsonResponse.getString("message"))
                                        .setNegativeButton("OK", null)
                                        .create()
                                        .show();

                            }






                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Error logging in!", Toast.LENGTH_SHORT).show();
                        }

                    }


                };




                LoginRequest loginRequest = new LoginRequest(username, password, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });//end new OnClickListener



        //this is the onClickListener for the registration textview
        //also set clickable to true in activity_login.xml
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //after click start start registration activity
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });//end onCreate

        helpLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //after click start start help activity
                startActivity(new Intent(LoginActivity.this, HelpActivity.class));
            }
        });//end onCreate

    }//end LoginActivity










}//end class