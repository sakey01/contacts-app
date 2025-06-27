package contactsapp.com;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecInterface {
    Intent intent;
    String[] names, numbers, emails;
    ArrayList<ContactModel> contactList = new ArrayList<>();
    int[] contactImages = {R.drawable.avatar1, R.drawable.avatar1, R.drawable.avatar2,
            R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar1, R.drawable.avatar1, R.drawable.avatar1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RecyclerView recView = findViewById(R.id.recycler_view);
        setUpContactModels();
        RecAdapter MyAdapter = new RecAdapter(this, contactList, this);
        recView.setAdapter(MyAdapter);
        recView.setLayoutManager(new LinearLayoutManager(this));

        //filters contacts from recycler view
        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                MyAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    //populates the array list
    private void setUpContactModels(){
        names = getResources().getStringArray(R.array.names);
        numbers = getResources().getStringArray(R.array.numbers);
        emails = getResources().getStringArray(R.array.emails);

        for (int i = 0; i < names.length; i++) {
            contactList.add(new ContactModel(names[i], numbers[i], emails[i], contactImages[i]));
        }
    }
    //give on click functionality to the cardview so it can open an intent
    @Override
    public void onItemSelected(int pos) {
        intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        //startActivityForResult(intent, PICK_CONTACT);
        Toast.makeText(this, "Position: " + pos, Toast.LENGTH_SHORT).show();
    }
}