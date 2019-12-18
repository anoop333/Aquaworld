package gocars.mainproject.aquaworld;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart extends AppCompatActivity implements PaymentResultListener {


    //a list to store all the products
    List<Cheque> productList;
    int s=0;
    //the recyclerview
    RecyclerView recyclerView;
    String ok;
    Button pay;
ProgressBar p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
                WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_cart);

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


        recyclerView = findViewById(R.id.recylcerViewcart);
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "https://anoopsuvarnan1.000webhostapp.com/fetchcart.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        p.setVisibility(
                                View.INVISIBLE
                        );

if(response.equals("null"))
{
    Toast.makeText(Cart.this,"no products",Toast.LENGTH_LONG).show();
}
else {
    try {
        //converting the string to json array object
        JSONArray array = new JSONArray(response);

        //traversing through all the object
        for (int i = 0; i < array.length(); i++) {


            //getting product object from json array
            JSONObject product = array.getJSONObject(i);
            s = s + Integer.parseInt(product.getString("price"));

            //adding the product to product list
            productList.add(new Cartdata(
                    product.getString("image"),
                    product.getString("des"),
                    product.getString("price"),
                    product.getString("price"),
                    product.getString("product_id")

            ));

        }

        Toast.makeText(Cart.this, String.valueOf(s), Toast.LENGTH_LONG).show();

        Chequeadaptercart adapter = new Chequeadaptercart(Cart.this, productList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


    } catch (JSONException e) {
        e.printStackTrace();
    }
}
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }


                })

                    {
                        @Override
                        protected Map<String, String> getParams () throws AuthFailureError {
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
        inflater.inflate(R.menu.pa, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.panow:
                // write your code here
startPayment();
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
    public void startPayment() {
        /*
          You need to pass current activity in order to let Razorpay create CheckoutActivity
         */

        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Razorpay Corp");
            options.put("description", "Demoing Charges");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", Integer.parseInt(String.valueOf(s))*100);

            JSONObject preFill = new JSONObject();
            preFill.put("email", "test@razorpay.com");
            preFill.put("contact", "9876543210");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {

    }

    @Override
    public void onPaymentError(int i, String s) {

    }
}