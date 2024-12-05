package ie.ul.cs4084;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ChatActivity extends AppCompatActivity {

    // Static method to start the activity with UserModel data
    public static void start(Context context, UserModel user) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("first", user.getFirst());
        intent.putExtra("last", user.getLast());
        intent.putExtra("email", user.getEmail());
        intent.putExtra("phone_number", user.getPhoneNumber());
        intent.putExtra("bio", user.getBio());
        intent.putExtra("interests", user.getInterests());
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // Ensure this layout exists

        // Retrieve user details passed via intent
        String firstName = getIntent().getStringExtra("first");
        String lastName = getIntent().getStringExtra("last");
        String email = getIntent().getStringExtra("email");
        String phoneNumber = getIntent().getStringExtra("phone_number");
        String bio = getIntent().getStringExtra("bio");
        String interests = getIntent().getStringExtra("interests");

        // Handle null or missing data gracefully
        if (firstName == null) firstName = "Unknown";
        if (lastName == null) lastName = "User";
        if (email == null) email = "Not Provided";
        if (phoneNumber == null) phoneNumber = "Not Provided";
        if (bio == null) bio = "No bio available";
        if (interests == null) interests = "No interests provided";

        // Update UI with user details
        TextView userInfo = findViewById(R.id.userInfo); // Ensure this TextView exists in activity_chat.xml
        userInfo.setText(
                "Chatting with: " + firstName + " " + lastName +
                        "\nEmail: " + email +
                        "\nPhone: " + phoneNumber +
                        "\nBio: " + bio +
                        "\nInterests: " + interests
        );

        // Additional setup for the chat interface can be added here (e.g., message list, send button)
    }
}
