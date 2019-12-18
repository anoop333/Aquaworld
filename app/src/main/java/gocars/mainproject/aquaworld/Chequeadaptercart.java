package gocars.mainproject.aquaworld;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chequeadaptercart extends RecyclerView.Adapter<Chequeadaptercart.ProductViewHolder> {

    private Context mCtx;
    private List<Cheque> productList;
    int sum=0,i=1;
    public Chequeadaptercart(Context mCtx, List<Cheque> productList) {
        this.mCtx = mCtx;
        this.productList = productList;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.adapterfor_cart, null);
        return new ProductViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, final int position) {
        final Cheque cheque = productList.get(position);

        //loading the image
        Glide.with(mCtx)
                .load(cheque.getImage())
                .into(holder.imageView);
        holder.text.setText(cheque.getUser());
holder.txtt.setText(cheque.getStatus().toUpperCase());

holder.plus.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        i++;
        Toast.makeText(mCtx,String.valueOf(i),Toast.LENGTH_LONG).show();
        holder.ts.setText("\u20B9"+Integer.parseInt(cheque.getPrize())*i);
    }
});

holder.del.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://anoopsuvarnan1.000webhostapp.com/cartdel.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

Toast.makeText(mCtx,response,Toast.LENGTH_LONG).show();


                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

params.put("iddel",cheque.getPid());
                //returning parameter
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        requestQueue.add(stringRequest);
    }
});
        //Toast.makeText(mCtx,total,Toast.LENGTH_LONG).show();

      //  SharedPreferences sharedPreferences = mCtx.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Creating editor to store values to shared preferences


    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {


        ImageView imageView,del,plus;
        TextView text,txtt,ts,f;

        public ProductViewHolder(View itemView) {
            super(itemView);
plus=itemView.findViewById(R.id.imageView);

            imageView = itemView.findViewById(R.id.imageView21);
            text=itemView.findViewById(R.id.textView21);
            txtt=itemView.findViewById(R.id.restname1);
            ts=itemView.findViewById(R.id.mm1);
            del=itemView.findViewById(R.id.del);

        }

    }
}