package contactsapp.com;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class RecAdapter extends RecyclerView.Adapter<RecAdapter.MyViewHolder> implements Filterable {
    Context context;
    ArrayList <ContactModel> contactList;
    ArrayList <ContactModel> contactListFull;
    RecInterface recInterface;

    public void setContactList(ArrayList<ContactModel> contactList) {
        this.contactList = contactList;
    }

    public RecAdapter(Context context, ArrayList<ContactModel> conctactModels, RecInterface recInterface) {
        this.context = context;
        this.contactList = conctactModels;
        this.contactListFull = new ArrayList<>(contactList);
        this.recInterface = recInterface;
    }

    @NonNull
    @Override
    public RecAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view, recInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull RecAdapter.MyViewHolder holder, int position) {
        holder.imageView.setImageResource(contactList.get(position).getImage());
        holder.name.setText(contactList.get(position).getName());
        holder.phone.setText(contactList.get(position).getPhone());
        holder.email.setText(contactList.get(position).getEmail());

        ContactModel contact = contactList.get(position);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL); // Use ACTION_DIAL for safety
            intent.setData(Uri.parse("tel:" + contact.getPhone()));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }
    //filter search view
    @Override
    public Filter getFilter() {
        return filter;
    }
    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<ContactModel> filteredList = new ArrayList<>();

            if (constraint.toString().isEmpty()) {
                filteredList.addAll(contactListFull);
            }
            else {
                for (ContactModel item : contactListFull) {
                    if (item.getName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            contactList.clear();
            contactList.addAll((ArrayList<ContactModel>) results.values);
            notifyDataSetChanged();
        }
    };

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name, phone, email;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView, RecInterface recInterface) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phone);
            email = itemView.findViewById(R.id.email);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (recInterface != null){
                        int pos = getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION){
                            recInterface.onItemSelected(pos);
                        }
                    }
                }
            });
        }
    }
    public void filerList(ArrayList<ContactModel> filteredList){
        contactList = filteredList;
        notifyDataSetChanged();
    }
}