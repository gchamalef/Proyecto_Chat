package com.example.mychat;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityFragment extends Fragment {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private ImageButton btnEnviar;

    private AdapterMensajes adapter;
    private FirebaseFirestore database;
    private CollectionReference messagesCollection;

    public CommunityFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_community, container, false);

        // Inicializar las vistas
        fotoPerfil = view.findViewById(R.id.fotoPerfil);
        nombre = view.findViewById(R.id.nombre);
        rvMensajes = view.findViewById(R.id.rvMensajes);
        txtMensaje = view.findViewById(R.id.txtMensaje);
        btnEnviar = view.findViewById(R.id.btnEnviar);

        database = FirebaseFirestore.getInstance();
        messagesCollection = database.collection("public_chat");

        adapter = new AdapterMensajes(getContext());
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = txtMensaje.getText().toString();
                String nombreUsuario = nombre.getText().toString();
                Mensaje nuevoMensaje = new Mensaje(mensaje, nombreUsuario, "", "1", "00:00");

                messagesCollection.add(nuevoMensaje)
                        .addOnSuccessListener(documentReference -> {
                            txtMensaje.setText("");
                            adapter.addMensaje(nuevoMensaje);
                            adapter.notifyDataSetChanged();
                        })
                        .addOnFailureListener(e -> {
                            // Manejo de errores
                        });
            }
        });

        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });

        messagesCollection.addSnapshotListener((queryDocumentSnapshots, e) -> {
            if (e != null) {
                // Manejo de errores
                return;
            }
            for (DocumentChange dc : queryDocumentSnapshots.getDocumentChanges()) {
                if (dc.getType() == DocumentChange.Type.ADDED) {
                    Mensaje nuevoMensaje = dc.getDocument().toObject(Mensaje.class);
                    adapter.addMensaje(nuevoMensaje); // Añadir el mensaje al adaptador
                }
            }
            adapter.notifyDataSetChanged(); // Notificar al adaptador sobre el cambio
        });
        return view;
    }

    private void setScrollbar() {
        rvMensajes.scrollToPosition(adapter.getItemCount() - 1);
    }
}
