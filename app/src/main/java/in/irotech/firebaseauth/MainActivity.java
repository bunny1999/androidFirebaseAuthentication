package in.irotech.firebaseauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    Button button;
    Button button1;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser=null;
    @Override
    public void onStart() {
        super.onStart();
        currentUser = mAuth.getCurrentUser();
        if(currentUser!=null){
            moveToWelcomeIntent();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1=findViewById(R.id.editText);
        editText2=findViewById(R.id.editText2);
        button=findViewById(R.id.button);
        button1=findViewById(R.id.button2);

        mAuth=FirebaseAuth.getInstance();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            final String usr=editText1.getText().toString();
            final String pass=editText2.getText().toString();
            Log.d("Tag",usr+" "+pass);
            mAuth.createUserWithEmailAndPassword(usr, pass)
             .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Authentication Successful.",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(MainActivity.this, "Authentication failed."+ task.getException(),
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
            }
        });
        signin();
    }

    public void signin(){
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              mAuth.signInWithEmailAndPassword(editText1.getText().toString(),editText2.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                  @Override
                  public void onComplete(@NonNull Task<AuthResult> task) {
                      moveToWelcomeIntent();
                  }
              }).addOnFailureListener(new OnFailureListener() {
                  @Override
                  public void onFailure(@NonNull Exception e) {
                      Toast.makeText(MainActivity.this, "SignIn Failed.",
                              Toast.LENGTH_SHORT).show();
                  }
              });
            }
        });
    }
    public void moveToWelcomeIntent(){
        Intent intent=new Intent(MainActivity.this,WelcomePage.class);
        startActivity(intent);
    }
}
