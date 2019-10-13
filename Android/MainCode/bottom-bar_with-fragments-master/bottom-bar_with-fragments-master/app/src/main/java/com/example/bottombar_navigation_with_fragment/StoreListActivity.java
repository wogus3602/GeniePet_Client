package com.example.bottombar_navigation_with_fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bottombar_navigation_with_fragment.Adapter.ListViewAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class StoreListActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.store_list);
        new ParseTask().execute();
    }

    //멀티 쓰레드
    private class ParseTask extends AsyncTask<Void, Void, String> {
        HttpURLConnection urlconnection = null;
        BufferedReader reader = null;
        String resultJson = "";

        //쓰레드가 수행할 작업(Generated Thread)
        @Override
        protected String doInBackground(Void... Params) {
            try{
                String site_url_json = "http://d8f79318.ngrok.io/feed/";
                URL url = new URL(site_url_json);

                urlconnection = (HttpURLConnection) url.openConnection();
                urlconnection.setRequestMethod("GET");
                urlconnection.connect();

                InputStream inputStream = urlconnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }
                resultJson = buffer.toString();
            } catch (Exception e){
                e.printStackTrace();
            }
            return resultJson;
        }

        //쓰레드가 시작하기 전에 수행할 작업(Main Thread)
        protected void onPostExecute(String strJson){
            super.onPostExecute(strJson);

            ListViewAdapter adapter;
            ListView listView;

            adapter = new ListViewAdapter();
            listView = findViewById(R.id.List_view);
            listView.setAdapter(adapter);

            //final ListView lView = findViewById(R.id.lvMain);
            ////json 받는 종류
            //String[] from = {"id","text","name", "price", "image"};
            //int[] to = {R.id.name_item, R.id.price, R.id.imageView,R.id.id,R.id.text1};

            //ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String, String>>();
            //HashMap<String,String> hashMap;



            try {
                JSONObject jsonObject = new JSONObject(strJson);
               JSONArray jArray = jsonObject.getJSONArray("results");
                //JSONArray jArray = new JSONArray(strJson);

                Log.d("FOR_LOG", ""+jArray);
                for(int i = 0; i < jArray.length(); i++){
                    JSONObject friend = jArray.getJSONObject(i);

                    //String nameOS = friend.getString("name");
                    //String price = friend.getString("price");
                    //String rating = friend.getString("image");
                    //String id = friend.getString("id");
                    //String text = friend.getString("text");

                    //Drawable img = drawableFromUrl(friend.getString("image"));
                    String img = friend.getString("image");
                    String title = friend.getString("name");
                    String context = friend.getString("text");
                    String price = friend.getString("price");

                    adapter.addVO(img,title,context,price);
//                    Log.d("FOR_LOG", nameOS);
//
//                    hashMap = new HashMap<String, String>();
//                    hashMap.put("id", "" + rating);
//                    hashMap.put("name", "" + nameOS);
//                    hashMap.put("price", "" + price);
//                    hashMap.put("image", "" + id);
//                    hashMap.put("text", "" + text);
//                    arrayList.add(hashMap);
                }

//                final SimpleAdapter adapter = new SimpleAdapter(StoreListActivity.this, arrayList, R.layout.store_item, from , to);
//                lView.setAdapter(adapter); //뿌려주는 부분

            }catch (Exception e){
                e.printStackTrace();
            }
        }

//        private Drawable drawableFromUrl(String url)
//                throws IOException {
//            Bitmap x;
//
//            HttpURLConnection connection =
//                    (HttpURLConnection) new URL(url).openConnection();
//            connection.connect();
//            InputStream input = connection.getInputStream();
//
//            x = BitmapFactory.decodeStream(input);
//            return new BitmapDrawable(getResources(),x);
//        }
    }
}
