package sv.edu.catolica.g07_occasio.activity.ui.mis_eventos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import sv.edu.catolica.g07_occasio.R;
import sv.edu.catolica.g07_occasio.activity.ui.clases.Evento;

public class MisEventosViewModel extends ViewModel {

    public static class MisEventosAdapter extends RecyclerView.Adapter<MisEventosAdapter.MisEventosViewHolder> {

        private Context context;
        private List<Evento> eventos;

        public MisEventosAdapter(Context context, List<Evento> eventos) {
            this.context = context;
            this.eventos = eventos;
        }

        @NonNull
        @Override
        public MisEventosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_card_prueba, parent, false);
            return new MisEventosViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MisEventosViewHolder holder, int position) {
            Evento evento = eventos.get(position);

            holder.titulo.setText(evento.nombreEvento);
            holder.organizador.setText("Fecha: " + evento.fechaEvento);
            holder.fechaHora.setText(evento.horaEvento);
            holder.categoria.setText(evento.categoria);

            // Cargar imagen con Glide
            Glide.with(context)
                    .load(evento.urlImagen)
                    .placeholder(R.drawable.placeholder) // Imagen de carga
                    .error(R.drawable.placeholder) // Imagen de error
                    .into(holder.icono);

            // Elementos clickeables pero sin funcionalidad
            holder.itemView.setOnClickListener(v -> {
                // Actualmente no tiene funcionalidad
            });
        }

        @Override
        public int getItemCount() {
            return eventos.size();
        }

        public static class MisEventosViewHolder extends RecyclerView.ViewHolder {
            TextView titulo, organizador, fechaHora, categoria;
            ImageView icono;

            public MisEventosViewHolder(@NonNull View itemView) {
                super(itemView);

                titulo = itemView.findViewById(R.id.tituloEvento);
                organizador = itemView.findViewById(R.id.organizador);
                fechaHora = itemView.findViewById(R.id.fecha_hora);
                categoria = itemView.findViewById(R.id.txtCategoria);
                icono = itemView.findViewById(R.id.iconImageView);
            }
        }
    }
}