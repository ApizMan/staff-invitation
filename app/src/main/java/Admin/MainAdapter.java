package Admin;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.staff_invitation.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    Vibrator vibrator;

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position, @NonNull MainModel model) {
        holder.eventname.setText(model.getEventname());
        holder.eventdate.setText(model.getEventdate());
        holder.eventtime.setText(model.getEventtime());
        holder.eventlocation.setText(model.getEventlocation());

        //This is an action of button edit
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true,1200)
                        .create();

                View view = dialogPlus.getHolderView();

                EditText eventname = view.findViewById(R.id.txtEvent);
                EditText eventdate = view.findViewById(R.id.txtDate);
                EditText eventtime = view.findViewById(R.id.txtTime);
                EditText eventlocation = view.findViewById(R.id.txtlocation);
                EditText eventdescription = view.findViewById(R.id.txtEventDescription);
                EditText email = view.findViewById(R.id.txtEmail);
                EditText contactnumber = view.findViewById(R.id.txtContact);
                EditText companyname = view.findViewById(R.id.txtCompany);

                Button btnUpdate = view.findViewById(R.id.btnUpdate);

                eventname.setText(model.getEventname());
                eventdate.setText(model.getEventdate());
                eventtime.setText(model.getEventtime());
                eventlocation.setText(model.getEventlocation());
                eventdescription.setText(model.getEventdescription());
                email.setText(model.getEmail());
                contactnumber.setText(model.getContactnumber());
                companyname.setText(model.getCompanyname());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("eventname",eventname.getText().toString());
                        map.put("eventdate",eventdate.getText().toString());
                        map.put("eventtime",eventtime.getText().toString());
                        map.put("eventlocation",eventlocation.getText().toString());
                        map.put("eventdescription",eventdescription.getText().toString());
                        map.put("email",email.getText().toString());
                        map.put("contactnumber",contactnumber.getText().toString());
                        map.put("companyname",companyname.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("events")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.eventname.getContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                        vibrateDevice();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.eventname.getContext(), "Error While Updating Data", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                });
                    }
                });
            }
        });

        //This is an action of button delete
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.eventname.getContext());
                builder.setTitle("Are you Sure?");
                builder.setMessage("Deleted data can't be Undo");

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("events")
                                .child(getRef(position).getKey()).removeValue();

                        vibrateDevice();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.eventname.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent, false);
        return null;
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        CircleImageView img;
        TextView companyname, contactnumber, email, eventdate, eventdescription, eventlocation, eventname, eventtime;

        Button btnEdit,btnDelete;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView)itemView.findViewById(R.id.img1);
            eventname = (TextView)itemView.findViewById(R.id.txtEvent);
            eventdate = (TextView)itemView.findViewById(R.id.txtDate);
            eventtime = (TextView)itemView.findViewById(R.id.txtTime);
            eventlocation = (TextView)itemView.findViewById(R.id.txtlocation);
            eventdescription = (TextView)itemView.findViewById(R.id.txtEventDescription);
            email = (TextView)itemView.findViewById(R.id.txtEmail);
            contactnumber = (TextView)itemView.findViewById(R.id.txtContact);
            companyname = (TextView)itemView.findViewById(R.id.txtCompany);

            btnEdit = (Button)itemView.findViewById(R.id.btnEdit);
            btnDelete = (Button)itemView.findViewById(R.id.btnDelete);
        }
    }
    private void vibrateDevice() {
        // Vibrate for 500 milliseconds (0.5 seconds)
        vibrator.vibrate(500);
    }
}
