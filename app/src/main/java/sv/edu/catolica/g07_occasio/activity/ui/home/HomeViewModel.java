package sv.edu.catolica.g07_occasio.activity.ui.home;

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

public class HomeViewModel extends ViewModel {

    public static class EventoAdapter extends RecyclerView.Adapter<EventoAdapter.EventoViewHolder> {

        private Context context;
        private List<Evento> eventos;

        public EventoAdapter(Context context, List<Evento> eventos) {
            this.context = context;
            this.eventos = eventos;
        }

        @NonNull
        @Override
        public EventoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_card_prueba, parent, false);
            return new EventoViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull EventoViewHolder holder, int position) {
            Evento evento = eventos.get(position);

            holder.titulo.setText(evento.nombreEvento);
            holder.organizador.setText("Organiza: " + evento.organizador);
            holder.fechaHora.setText(evento.fechaEvento + " " + evento.horaEvento);
            holder.categoria.setText(evento.categoria);

            // Cargar imagen con Glide
            Glide.with(context)
                    .load(evento.urlImagen)
                    .placeholder(R.drawable.placeholder) // Imagen de carga
                    .error(R.drawable.placeholder) // Imagen de error
                    .into(holder.icono);
        }

        @Override
        public int getItemCount() {
            return eventos.size();
        }

        public static class EventoViewHolder extends RecyclerView.ViewHolder {
            TextView titulo, organizador, fechaHora, categoria;
            ImageView icono;

            public EventoViewHolder(@NonNull View itemView) {
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