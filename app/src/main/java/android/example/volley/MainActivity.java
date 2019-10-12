package android.example.volley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.awt.font.TextAttribute;

public class MainActivity extends AppCompatActivity {
    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextViewResult=findViewById(R.id.text_view_result);
        Button buttonParse=findViewById(R.id.button_parse);
       mQueue= Volley.newRequestQueue(this);
       buttonParse.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               jsonParse();
           }
       });
    }
    private void jsonParse()
    {
        String url="https://contesttrackerapi.herokuapp.com/";
        JsonObjectRequest request=new JsonObjectRequest(Request.Method.GET, url, null
                , new Response.Listener<JSONObject>() {
            @Override

            public void onResponse(JSONObject response) {
                JSONArray jsonArray= null;
                try {
                    JSONObject list=response.getJSONObject("result");
                    jsonArray = list.getJSONArray("ongoing");
                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject ongoing=jsonArray.getJSONObject(i);
                        String endTime=ongoing.getString("EndTime");
                        String mail=ongoing.getString("Platform");
                        mTextViewResult.append(endTime+","+mail+"\n\n");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              error.printStackTrace();

            }
        });

         mQueue.add(request);


    }
}
