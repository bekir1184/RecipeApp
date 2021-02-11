import android.content.Context
import android.content.Intent
import android.graphics.ColorSpace
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.Model
import com.example.recipeapp.R
import com.example.recipeapp.Recipe
import com.squareup.picasso.Picasso


class RecipeAdapter(val recipes : ArrayList<Recipe>, val context: Context) : RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recipe_item,parent,false)
        return RecipeViewHolder(v)

    }

    override fun getItemCount(): Int {
        return recipes.size
    }
    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bindItem(recipes.get(position),context)

    }


    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view){
        val recipeLabel : TextView = itemView.findViewById(R.id.recipeLabel)
        val calories :TextView = itemView.findViewById(R.id.calories)
        val recipeimg:ImageView =itemView.findViewById(R.id.recipeimg)
        val button :Button =itemView.findViewById(R.id.viewRecipe)
        fun bindItem(item: Recipe, context: Context){
            recipeLabel.setText(item.label)
            calories.setText(String.format("%.2f",item.calories))
            Picasso.get().load(item.image).resize(250,250).centerCrop().into(recipeimg)
            button.setOnClickListener({
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse(item.url)
                context.startActivity(i)
            })
        }
    }
}