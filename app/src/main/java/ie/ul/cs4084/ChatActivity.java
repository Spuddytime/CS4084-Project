package ie.ul.cs4084;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.Arrays;

public class ChatActivity extends AppCompatActivity {

    EditText messageInput;
    Button sendMessageBtn;
    TextView otherUser;
    RecyclerView recyclerView;
    String chatroomId;
    ChatModel chatModel;
    ChatRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat); // Ensure this layout exists

        Intent intent = getIntent();
        String otherEmail = intent.getStringExtra("email");
        String otherFirst = intent.getStringExtra("first");
        String otherLast = intent.getStringExtra("last");
        String otherName = otherFirst + " " + otherLast;



        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        String currentEmail = user.getEmail();

        chatroomId = getChatroomId(currentEmail, otherEmail);

        messageInput = findViewById(R.id.messageInput);
        sendMessageBtn = findViewById(R.id.message_send_btn);
        otherUser = findViewById(R.id.other_username);
        recyclerView = findViewById(R.id.recycler_view);
        otherUser.setText(otherName);
        sendMessageBtn.setOnClickListener((v -> {
            String message = messageInput.getText().toString().trim();
            if (message.isEmpty()){
                return;
            } else {
                sendMessageToUser(message, currentEmail);
            }
        }));
        setupChatRecyclerView();
        getOrCreateChatRoomModel(getChatroomId(user.getEmail(), otherEmail), currentEmail, otherEmail);
    }

    void sendMessageToUser(String message, String currentEmail) {
        chatModel.setLastMessageTimestamp(Timestamp.now());
        chatModel.setLastMessageSenderId(currentEmail);
        getChatroomReference(chatroomId).set(chatModel);

        MessageModel messageModel = new MessageModel(message, currentEmail, Timestamp.now());
        getChatroomMessageReference(chatroomId).add(messageModel).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {
                if(task.isSuccessful()){
                    messageInput.setText("");
                }
            }
        });
    }



    void setupChatRecyclerView(){
        Query query = getChatroomMessageReference(chatroomId)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<MessageModel> options = new FirestoreRecyclerOptions.Builder<MessageModel>()
                .setQuery(query,MessageModel.class).build();

        adapter = new ChatRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }

    public static void start(Context context, UserModel user) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("first", user.getFirst());
        intent.putExtra("last", user.getLast());
        intent.putExtra("email", user.getEmail());
        context.startActivity(intent);
    }
    public String getChatroomId(String userId1,String userId2) {
        if (userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2;
        }else {
            return userId2+"_"+userId1;
        }
    }

    public void getOrCreateChatRoomModel(String chatroomId, String user1, String user2){
        getChatroomReference(chatroomId).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                chatModel = task.getResult().toObject(ChatModel.class);
                if (chatModel == null){
                    chatModel = new ChatModel(
                            chatroomId,
                            Arrays.asList(user1,user2),
                            Timestamp.now(),
                            ""
                            );
                    getChatroomReference(chatroomId).set(chatModel);
                }
            }
        });
    }
    public static CollectionReference getChatroomMessageReference(String chatroomId){
        return getChatroomReference(chatroomId).collection("chats");
    }
    public static DocumentReference getChatroomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }

}
