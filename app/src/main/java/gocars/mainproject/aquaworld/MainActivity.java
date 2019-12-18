package gocars.mainproject.aquaworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    //a list to store all the products
    List<Cheque> productList;

    //the recyclerview
    RecyclerView recyclerView;
    String ok;
    Button bcart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_c);
        loadProducts();


       /* mySwipeRefreshLayout=findViewById(R.id.swipe);
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadProducts();
            }
        });*/
        // SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //  ok = sharedPreferences.getString("referral", null);
        //getting the recyclerview from xml


        recyclerView = findViewById(R.id.recylcerViewc);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //initializing the productlist
        productList = new ArrayList<>();

        //this method will fetch and parse json
        //to display it in recyclerview

    }


    private void loadProducts() {

        /*
         * Creating a String Request
         * The request type is GET defined by first parameter
         * The URL is defined in the second parameter
         * Then we have a Response Listener and a Error Listener
         * In response listener we will get the JSON response as a String
         * */
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://anoopsuvarnan1.000webhostapp.com/aqua.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {
                            //converting the string to json array object
                            JSONArray array = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i < array.length(); i++) {


                                //getting product object from json array
                                JSONObject product = array.getJSONObject(i);
                                //adding the product to product list
                                productList.add(new Cheque(
                                        product.getString("image"),
                                        product.getString("name"),
                                        product.getString("des"),
                                        product.getString("price"),
                                        product.getString("product_id")

                                ));
                            }


                            Chequeadapter adapter = new Chequeadapter(MainActivity.this, productList);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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


                //returning parameter
                return params;
            }

        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.cart, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.cartview:
                // write your code here
                Intent intent
                        =new Intent(MainActivity.this,Cart.class);
                startActivity(intent);
                return true;

            case 2:
                // write your code here
                return true;

            case 3:
                // write your code here
                return true;

            case 4:
                // write your code here
                return true;

            case 5:
                // write your code here
                return true;

            case 6:
                // write your code here
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}