package ie.ul.cs4084;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class fragment2 extends Fragment {

    private ArrayAdapter<String> adapter;
    private ArrayList<String> userNames; // Displayed names
    private ArrayList<UserModel> users;  // Full user objects

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout
        View view = inflater.inflate(R.layout.fragment_fragment2, container, false);

        // Initialize views and data structures
        ListView listViewUsers = view.findViewById(R.id.listViewUsers);
        userNames = new ArrayList<>();
        users = new ArrayList<>();

        // Set up adapter for the ListView
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, userNames);
        listViewUsers.setAdapter(adapter);

        // Fetch users from Firestore
        fetchUsers();

        // Set up item click listener
        listViewUsers.setOnItemClickListener((parent, view1, position, id) -> {
            if (position >= 0 && position < users.size()) {
                UserModel selectedUser = users.get(position);
                if (selectedUser != null) {
                    openChat(selectedUser);
                } else {
                    Log.e("ChatFragment", "Clicked user is null at position: " + position);
                }
            }
        });

        return view;
    }

    private void fetchUsers() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    userNames.clear();
                    users.clear();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        UserModel user = document.toObject(UserModel.class);

                        users.add(user);
                        String firstName = user.getFirst(); // Assumes UserModel has this field
                        userNames.add(firstName != null ? firstName : "Unknown User");
                    }

                    // Notify the adapter that the data has changed
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Log.e("ChatFragment", "Error fetching users", e));
    }

    private void openChat(UserModel user) {
        // Navigate to ChatActivity and pass the user details
        ChatActivity.start(requireContext(), user);
    }
}
