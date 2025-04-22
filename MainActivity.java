package id.genta.ramadhan.latihan9;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView contactListView;
    private RecyclerView contactRecyclerView;
    private SearchView searchView;
    private List<String> contactList;
    private List<String> filteredContactList;
    private ArrayAdapter<String> listViewAdapter;
    private ContactAdapter recyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi komponen UI
        contactListView = findViewById(R.id.contactListView);
        contactRecyclerView = findViewById(R.id.contactRecyclerView);
        searchView = findViewById(R.id.searchView);

        // Inisialisasi daftar kontak
        contactList = new ArrayList<>();
        contactList.add("Ayu");
        contactList.add("Kamal");
        contactList.add("Bejo");
        contactList.add("Anita");
        contactList.add("Surya");

        filteredContactList = new ArrayList<>(contactList);

        // Set up ListView dengan ArrayAdapter
        listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredContactList);
        contactListView.setAdapter(listViewAdapter);

        // Set up RecyclerView dengan Adapter Kustom
        contactRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new ContactAdapter(filteredContactList);
        contactRecyclerView.setAdapter(recyclerViewAdapter);

        // Filter kontak berdasarkan pencarian di SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filteredContactList.clear();
                for (String contact : contactList) {
                    if (contact.toLowerCase().contains(newText.toLowerCase())) {
                        filteredContactList.add(contact);
                    }
                }

                // Update adapter setelah filter
                listViewAdapter.notifyDataSetChanged();
                recyclerViewAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    // Adapter untuk RecyclerView
    private static class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
        private final List<String> contactList;

        public ContactAdapter(List<String> contactList) {
            this.contactList = contactList;
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position) {
            holder.textView.setText(contactList.get(position));
        }

        @Override
        public int getItemCount() {
            return contactList.size();
        }

        public static class ContactViewHolder extends RecyclerView.ViewHolder {
            TextView textView;

            public ContactViewHolder(View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
