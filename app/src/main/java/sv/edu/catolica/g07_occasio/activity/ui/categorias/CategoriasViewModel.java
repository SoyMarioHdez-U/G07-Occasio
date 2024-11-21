package sv.edu.catolica.g07_occasio.activity.ui.categorias;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import sv.edu.catolica.g07_occasio.R;
import sv.edu.catolica.g07_occasio.activity.ui.clases.Categoria;

public class CategoriasViewModel extends ViewModel {

    public static class CategoriasAdapter extends RecyclerView.Adapter<CategoriasAdapter.CategoriaViewHolder> {

        private final List<Categoria> categorias;
        private final OnCategoriaClickListener listener;
        private final Context context;

        public interface OnCategoriaClickListener {
            void onCategoriaClick(Categoria categoria);
        }

        public CategoriasAdapter(Context context, List<Categoria> categorias, OnCategoriaClickListener listener) {
            this.context = context;
            this.categorias = categorias;
            this.listener = listener;
        }

        @NonNull
        @Override
        public CategoriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_card_categorias, parent, false);
            return new CategoriaViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull CategoriaViewHolder holder, int position) {
            Categoria categoria = categorias.get(position);

            Glide.with(context)
                    .load(categoria.getUrlCategoria())
                    .placeholder(R.drawable.placeholder) // Imagen de carga
                    .error(R.drawable.placeholder)       // Imagen de error
                    .into(holder.imageButton);

            // Configurar el evento de clic en el ImageButton
            holder.imageButton.setOnClickListener(v -> listener.onCategoriaClick(categoria));
        }

        @Override
        public int getItemCount() {
            return categorias.size();
        }

        public static class CategoriaViewHolder extends RecyclerView.ViewHolder {
            ImageButton imageButton;

            public CategoriaViewHolder(@NonNull View itemView) {
                super(itemView);
                imageButton = itemView.findViewById(R.id.ib_deportes); // Asegúrate de que este ID coincide con tu diseño
            }
        }
    }
}