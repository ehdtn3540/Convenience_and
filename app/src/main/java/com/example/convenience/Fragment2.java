package com.example.convenience;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class Fragment2 extends Fragment implements View.OnClickListener  {
    ArrayList<BorderData> arr = new ArrayList<>();

    TextView tvTitle;
    ListView lvMain;
    ImageView ivTitle;
    Button btLogout;

    Fragment2.MyAdapter adapter = null;

    JSONArray json;

    String ip = "121.165.116.253";
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.activity_fragment2, container, false);

        tvTitle = rootView.findViewById(R.id.tv_title);
        lvMain = rootView.findViewById(R.id.lv_main);
        ivTitle = rootView.findViewById(R.id.iv_title);
        btLogout = rootView.findViewById(R.id.bt_logout);

        btLogout.setOnClickListener(this);

        adapter = new Fragment2.MyAdapter(getActivity());
        lvMain.setAdapter(adapter);

        requestdata();

        /* 아이템 클릭시 작동 */
//        lvMain.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView parent, View v, int position, long id) {
//                Intent intent = new Intent(getActivity(), ProductViewActivity.class);
//                /* putExtra의 첫 값은 식별 태그, 뒤에는 다음 화면에 넘길 값 */
//                try {
//                    intent.putExtra("pid", json.getJSONObject(position).getString("pid"));
//                    intent.putExtra("pname", json.getJSONObject(position).getString("pname"));
//                    intent.putExtra("pprice", json.getJSONObject(position).getString("pprice"));
//                    intent.putExtra("pimage1", json.getJSONObject(position).getString("pimage1"));
//                    intent.putExtra("pdetail", json.getJSONObject(position).getString("pdetail"));
//                    intent.putExtra("prdate", json.getJSONObject(position).getString("prdate"));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                startActivity(intent);
//            }
//        });

        return rootView;
    }

    //로그아웃
    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.bt_logout) {
            SharedPreferences auto = getActivity().getSharedPreferences("auto", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = auto.edit();
            editor.clear();
            editor.commit();
            Toast.makeText(getActivity(), "로그아웃.", Toast.LENGTH_SHORT).show();
            getActivity().finish();

            Intent intent = new Intent(getActivity(), com.example.convenience.MainActivity.class);
            startActivity(intent);
        }
    }

    //데이터 받아오는 코드(함수)
    public void requestdata(){
        adapter.notifyDataSetChanged();
        String url = "http://" + ip + ":8080/Convenience/Border?";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
//        StringRequest myReq = new StringRequest(Request.Method.GET, url, successListener, errorListener);
        StringRequest myReq = new StringRequest(Request.Method.GET, url,
                successListener, errorListener);
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
            Log.d("kkk", response);
            try {
                arr.clear();

                json = new JSONArray(response);

                for(int i=0; i<json.length(); i++)
                {
                    String midx = json.getJSONObject(i).getString("midx");
                    String bidx = json.getJSONObject(i).getString("bidx");
                    String btitle = json.getJSONObject(i).getString("btitle");
                    String bcontext = json.getJSONObject(i).getString("bcontext");
                    String bdate = json.getJSONObject(i).getString("bdate");

                    arr.add(new BorderData(midx, bidx, btitle, bcontext, bdate));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    };

    class ItemHolder {
        TextView ivTitleHolder;
        TextView tvDateHolder;
        TextView tvWriterHolder;
    }


    class MyAdapter extends ArrayAdapter {
        LayoutInflater lnf;

        public MyAdapter(Activity context) {
            super(context, R.layout.borderitem, arr);
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
            Fragment2.ItemHolder viewHolder;
            if (convertView == null) {

                convertView = lnf.inflate(R.layout.borderitem, parent, false);
                viewHolder = new Fragment2.ItemHolder();

                viewHolder.ivTitleHolder = convertView.findViewById(R.id.iv_title);
                viewHolder.tvWriterHolder = convertView.findViewById(R.id.tv_name);
                viewHolder.tvDateHolder = convertView.findViewById(R.id.tv_date);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (Fragment2.ItemHolder) convertView.getTag();
            }

//            viewHolder.ivTitleHolder.set(arr.get(position).title);
            viewHolder.tvWriterHolder.setText(arr.get(position).bdate);
            viewHolder.tvDateHolder.setText(arr.get(position).bcontext);
            viewHolder.ivTitleHolder.setText(arr.get(position).btitle);

            return convertView;
        }
    }
}