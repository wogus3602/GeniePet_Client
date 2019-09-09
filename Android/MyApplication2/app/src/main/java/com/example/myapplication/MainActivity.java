//package com.example.myapplication;
//
//import android.content.Context;
//import android.os.AsyncTask;
//
//import android.os.Bundle;
//
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.SimpleAdapter;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.myapplication.Django.DjangoREST;
//
////JSON 형식 받기
//import org.json.JSONObject;
//import org.json.JSONArray;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//
////listview
//import java.util.HashMap;
//import java.util.ArrayList;
//
//public class MainActivity extends AppCompatActivity {
//
//    DjangoREST djangoREST = new DjangoREST();
//    public static Context mContext;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        mContext = this;
//        Button button1 = findViewById(R.id.button);
//        Button button2 = findViewById(R.id.button2);
//        Button button3 = findViewById(R.id.button3);
//
//        View.OnClickListener listener = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                djangoREST.uploadFoto(null);
//            }
//        };
//
//        View.OnClickListener addpost = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                djangoREST.AddPostServer("test","test2");
//            }
//        };
//
//        View.OnClickListener login = new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                djangoREST.login();
//            }
//        };
//
//        button1.setOnClickListener(listener);
//
//        button2.setOnClickListener(addpost);
//
//        button3.setOnClickListener(login);
//
//
//        new ParseTask().execute();   // Django -> android GET형식으로 받기 실행
//    }
//
//
//    //멀티 쓰레드
//    private class ParseTask extends AsyncTask<Void, Void, String> {
//        HttpURLConnection urlconnection = null;
//        BufferedReader reader = null;
//        String resultJson = "";
//
//        //쓰레드가 수행할 작업(Generated Thread)
//        @Override
//        protected String doInBackground(Void... Params) {
//            try{
//                String site_url_json = "http://121.163.43.131:8000/feed";
//                URL url = new URL(site_url_json);
//
//                urlconnection = (HttpURLConnection) url.openConnection();
//                urlconnection.setRequestMethod("GET");
//                urlconnection.connect();
//
//                InputStream inputStream = urlconnection.getInputStream();
//                StringBuffer buffer = new StringBuffer();
//
//                reader = new BufferedReader(new InputStreamReader(inputStream));
//
//                String line;
//                while ((line = reader.readLine()) != null){
//                    buffer.append(line);
//                }
//                resultJson = buffer.toString();
//            } catch (Exception e){
//                e.printStackTrace();
//            }
//            return resultJson;
//        }
//
//        //쓰레드가 시작하기 전에 수행할 작업(Main Thread)
//        protected void onPostExecute(String strJson){
//            super.onPostExecute(strJson);
//
//            final ListView lView = findViewById(R.id.lvMain2);
//
//            //json 받는 종류
//            String[] from = {"name_item", "price", "rating"};
//            int[] to = {R.id.name_item, R.id.price, R.id.rating};
//            ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
//            HashMap<String,String> hashMap;
//
//            try {
//                JSONArray jArray = new JSONArray(strJson);
//                //JSONObject json = new JSONObject(strJson);
//
//                //JsonArray jArray = json.getJSONObject("");
//                //JSONArray jArray = json.getJSONArray("");
//
//                Log.d("FOR_LOG", ""+jArray);
//                for(int i = 0; i < jArray.length(); i++){
//                    JSONObject friend = jArray.getJSONObject(i);
//
//                    String nameOS = friend.getString("name");
//                    String price = friend.getString("price");
//                    String rating = friend.getString("rating");
//
//                    Log.d("FOR_LOG", nameOS);
//
//                    hashMap = new HashMap<String, String>();
//                    hashMap.put("name_item", "" + nameOS);
//                    hashMap.put("price", "" + price);
//                    hashMap.put("rating", "" + rating);
//                    arrayList.add(hashMap);
//                }
//
//                final SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, arrayList, R.layout.item, from , to);
//                lView.setAdapter(adapter); //뿌려주는 부분
//
////                JSONArray jsonarray = new JSONArray(strJson);
////                JSONObject jsonobj = jsonarray.getJSONObject(0);
////                Log.d("111111111",""+jsonobj.names());
////
////
////                String result_json_text = jsonobj.getString("rating");
////                Log.d("FOR_LOG", result_json_text);
////
////                TextView textView = findViewById(R.id.showtext);
////                textView.setText(result_json_text);
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }
//
//}
