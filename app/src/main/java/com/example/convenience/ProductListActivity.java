package com.example.convenience;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductListActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<ItemData> arr = new ArrayList<>();

    TextView tvTitle;
    ListView lvMain;
    ImageView ivTitle;
    Spinner spinner;
    Button btLogout;

    ProductListActivity.MyAdapter adapter = null;

    JSONArray json;

    String spinner_str;

    String ip = "121.165.116.253";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        tvTitle = findViewById(R.id.tv_title);
        lvMain = findViewById(R.id.lv_main);
        ivTitle = findViewById(R.id.iv_title);
        spinner = findViewById(R.id.spinner);
        btLogout = findViewById(R.id.bt_logout);

        btLogout.setOnClickListener(this);

        adapter = new ProductListActivity.MyAdapter(this);
        lvMain.setAdapter(adapter);

        //스피너(드롭다운) 선택 리스너
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinner_str = parent.getItemAtPosition(position).toString();
                requestdata(spinner_str);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        /* 아이템 클릭시 작동 */
        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProductViewActivity.class);
                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
                try {
                    intent.putExtra("pid", json.getJSONObject(position).getString("pid"));
                    intent.putExtra("pname", json.getJSONObject(position).getString("pname"));
                    intent.putExtra("pprice", json.getJSONObject(position).getString("pprice"));
                    intent.putExtra("pimage1", json.getJSONObject(position).getString("pimage1"));
                    intent.putExtra("pdetail", json.getJSONObject(position).getString("pdetail"));
                    intent.putExtra("prdate", json.getJSONObject(position).getString("prdate"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
    }

    //로그아웃
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bt_logout) {
            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = auto.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(ProductListActivity.this, "로그아웃.", Toast.LENGTH_SHORT).show();
            finish();

            Intent intent = new Intent(this, com.example.convenience.MainActivity.class);
            startActivity(intent);
        }
    }

    //데이터 받아오는 코드(함수)
    public void requestdata(final String spinner_str){
        adapter.notifyDataSetChanged();
        String url = "http://" + ip + ":8080/Convenience/testservlet?";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//        StringRequest myReq = new StringRequest(Request.Method.GET, url, successListener, errorListener);
        StringRequest myReq = new StringRequest(Request.Method.POST, url,
                successListener, errorListener) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("spinner_str", spinner_str);
                return params;
            }
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        requestQueue.add(myReq);
    }

    Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    };

    Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
//            Log.d("kkk", response);
            try {
                arr.clear();

                json = new JSONArray(response);

                for(int i=0; i<json.length(); i++)
                {
                    String pid = json.getJSONObject(i).getString("pid");
                    String pname = json.getJSONObject(i).getString("pname");
                    String pprice = json.getJSONObject(i).getString("pprice");
                    String pimage1 = json.getJSONObject(i).getString("pimage1");
                    String pdetail = json.getJSONObject(i).getString("pdetail");
                    String prdate = json.getJSONObject(i).getString("prdate");

                    arr.add(new ItemData(pid, pname, pprice, pimage1, pdetail, prdate));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    };

    class ItemHolder {
        ImageView ivTitleHolder;
        TextView tvDateHolder;
        TextView tvWriterHolder;
    }


    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter(Activity context) {
            super(context, R.layout.item, arr);
            lnf = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arr.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return arr.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ProductListActivity.ItemHolder viewHolder;
            if (convertView == null) {

                convertView = lnf.inflate(R.layout.item, parent, false);
                viewHolder = new ProductListActivity.ItemHolder();

                viewHolder.ivTitleHolder = convertView.findViewById(R.id.iv_title);
                viewHolder.tvWriterHolder = convertView.findViewById(R.id.tv_name);
                viewHolder.tvDateHolder = convertView.findViewById(R.id.tv_date);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ProductListActivity.ItemHolder) convertView.getTag();
            }

//            viewHolder.ivTitleHolder.set(arr.get(position).title);
            viewHolder.tvWriterHolder.setText(arr.get(position).pname);
            viewHolder.tvDateHolder.setText(arr.get(position).pprice);

            Glide.with(ProductListActivity.this)
                    .load("http://" + ip + ":8080/Convenience/img/"+arr.get(position).pimage1)
                    .into(viewHolder.ivTitleHolder);

            return convertView;
        }
    }
}