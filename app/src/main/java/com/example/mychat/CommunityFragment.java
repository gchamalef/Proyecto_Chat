package com.example.mychat;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityFragment extends Fragment {

    private CircleImageView fotoPerfil;
    private TextView nombre;
    private RecyclerView rvMensajes;
    private EditText txtMensaje;
    private ImageButton btnEnviar;
    private ImageButton btnEnviarFoto;

    private AdapterMensajes adapter;
    private FirebaseFirestore database;
    private CollectionReference messagesCollection;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private static final int PHOTO_SEND = 1;

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
        btnEnviarFoto = view.findViewById(R.id.btnEnviarFoto);

        database = FirebaseFirestore.getInstance();
        messagesCollection = database.collection("public_chat");
        storage = FirebaseStorage.getInstance();

        adapter = new AdapterMensajes(getContext());
        LinearLayoutManager l = new LinearLayoutManager(getContext());
        rvMensajes.setLayoutManager(l);
        rvMensajes.setAdapter(adapter);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = txtMensaje.getText().toString();
                String nombreUsuario = nombre.getText().toString();
                String timestamp = String.valueOf(System.currentTimeMillis());

                Mensaje nuevoMensaje = new Mensaje(mensaje, nombreUsuario, "", "1", "");

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

        btnEnviarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("image/jpeg");
                i.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                startActivityForResult(Intent.createChooser(i,"Selecciona una fotografía"),PHOTO_SEND);
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
                    //MensajeRecibir nuevoMensaje = (MensajeRecibir) dc.getDocument().toObject(Mensaje.class);
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

    @Override
    /*public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK){
            Uri u = data.getData();
            storageReference = storage.getReference("imagenes_chat_publico"); // Imagenes del chat público
            final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
            fotoReferencia.putFile(u).addOnSuccessListener(taskSnapshot -> {
                fotoReferencia.getDownloadUrl().addOnSuccessListener(uri -> {
                    String url = uri.toString();
                    String timestamp = String.valueOf(System.currentTimeMillis());
                    Mensaje m = new Mensaje("Usuario te ha enviado una foto",u.toString(), nombre.getText().toString(),url, "2");
                    messagesCollection.add(m)
                            .addOnSuccessListener(documentReference -> {
                                // Mensaje enviado exitosamente
                            })
                            .addOnFailureListener(e -> {
                                // Error al enviar el mensaje
                            });
                });
            });
        }
    }*/

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == Activity.RESULT_OK && data != null) {
            Uri u = data.getData();
            if (u != null) {
                storageReference = storage.getReference("imagenes_chat_publico");
                final StorageReference fotoReferencia = storageReference.child(u.getLastPathSegment());
                fotoReferencia.putFile(u).addOnSuccessListener(taskSnapshot -> {
                    fotoReferencia.getDownloadUrl().addOnSuccessListener(uri -> {
                        String url = uri.toString();
                        String timestamp = String.valueOf(System.currentTimeMillis());
                        Mensaje m = new Mensaje("Usuario te ha enviado una foto", u.toString(), nombre.getText().toString(), url, "2", "");
                        messagesCollection.add(m)
                                .addOnSuccessListener(documentReference -> {
                                    // Mensaje enviado exitosamente
                                })
                                .addOnFailureListener(e -> {
                                    e.printStackTrace();
                                });
                    });
                });
            } else {
                // Handle null Uri
            }
        }
    }

}
