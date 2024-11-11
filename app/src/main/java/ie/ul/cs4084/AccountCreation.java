package ie.ul.cs4084;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AccountCreation extends AppCompatActivity {

    FirebaseAuth mAuth;
    TextInputEditText editFirstName, editMiddleName, editSurname, editInterest, editGender, editAge, editBio, editPhoneNumber;
    Button buttonCreate;
    String email;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_account_creation);

        mAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        editFirstName = findViewById(R.id.firstNameEditText);
        editMiddleName = findViewById(R.id.middleNameEditText);
        editSurname = findViewById(R.id.surnameEditText);
        editInterest = findViewById(R.id.interestsEditText);
        editGender = findViewById(R.id.genderEditText);
        editAge = findViewById(R.id.ageEditText);
        editBio = findViewById(R.id.bioEditText);
        editPhoneNumber = findViewById(R.id.phoneNumberEditText);
        buttonCreate = findViewById(R.id.createAccountButton);

        buttonCreate.setOnClickListener(v -> {
            String firstName, middleName, surname, interest, gender, age, bio, phoneNumber;
            firstName = String.valueOf(editFirstName.getText());
            middleName = String.valueOf(editMiddleName.getText());
            surname = String.valueOf(editSurname.getText());
            interest = String.valueOf(editInterest.getText());
            gender = String.valueOf(editGender.getText());
            age = String.valueOf(editAge.getText());
            bio = String.valueOf(editBio.getText());
            phoneNumber = String.valueOf(editPhoneNumber.getText());
            email = Objects.requireNonNull(mAuth.getCurrentUser()).getEmail();

            if(TextUtils.isEmpty(firstName)){
                Toast.makeText(AccountCreation.this, "Enter First Name", Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(surname)){
                Toast.makeText(AccountCreation.this, "Enter Surname", Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(interest)){
                Toast.makeText(AccountCreation.this, "Enter Interests", Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(gender)){
                Toast.makeText(AccountCreation.this, "Enter Gender", Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(age)){
                Toast.makeText(AccountCreation.this, "Enter Age", Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(bio)){
                Toast.makeText(AccountCreation.this, "Enter Bio", Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(phoneNumber)){
                Toast.makeText(AccountCreation.this, "Enter Phone Number", Toast.LENGTH_LONG).show();
                return;
            }

            Map<String, Object> user = new HashMap<>();
            user.put("email", email);
            user.put("first", firstName);
            user.put("middle", middleName);
            user.put("last", surname);
            user.put("interests", interest);
            user.put("gender", gender);
            user.put("age", age);
            user.put("bio", bio);
            user.put("phone_number", phoneNumber);

// Add a new document with a generated ID
            db.collection("users")
                    .add(user)
                    .addOnSuccessListener(documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w(TAG, "Error adding document", e));

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        });

    }
}