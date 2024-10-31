package ie.ul.cs4084;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Toast;
import android.util.Log;
import com.daprlabs.cardstack.SwipeDeck;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SwipeDeck cardStack;
    private ArrayList<UserModal> userModalArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userModalArrayList = new ArrayList<>();
        cardStack = (swipeDeck) findViewById(R.id.swipe_deck);

        userModalArrayList.add(new UserModal("Andy", 21, "Happy"));
        userModalArrayList.add(new UserModal("Andy", 21, "Happy"));
        userModalArrayList.add(new UserModal("Andy", 21, "Happy"));
        userModalArrayList.add(new UserModal("Andy", 21, "Happy"));
        userModalArrayList.add(new UserModal("Andy", 21, "Happy"));

        final DeckAdapter adapter = new DeckAdapter(userModalArrayList, this);

        cardStack.setAdapter(adapter);

        cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback(){
            @Override
            public void cardSwipedLeft(int position) {
                // on card swipe left we are displaying a toast message.
                Toast.makeText(MainActivity.this, "Card Swiped Left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardSwipedRight(int position) {
                // on card swiped to right we are displaying a toast message.
                Toast.makeText(MainActivity.this, "Card Swiped Right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardsDepleted() {
                // this method is called when no card is present
                Toast.makeText(MainActivity.this, "No more courses present", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cardActionDown() {
                // this method is called when card is swiped down.
                Log.i("TAG", "CARDS MOVED DOWN");
            }

            @Override
            public void cardActionUp() {
                // this method is called when card is moved up.
                Log.i("TAG", "CARDS MOVED UP");
            }
        });
    }
}