package com.example.hillavas.tipnoo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.hillavas.tipnoo.Adapters.HomeContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Adapters.SearchContentRecyclerAdapter;
import com.example.hillavas.tipnoo.Models.ContentList;
import com.example.hillavas.tipnoo.Models.ContentResult;
import com.example.hillavas.tipnoo.Models.TabList;
import com.example.hillavas.tipnoo.Models.TabResults;
import com.example.hillavas.tipnoo.Models.TagList;
import com.example.hillavas.tipnoo.Models.TagResults;
import com.example.hillavas.tipnoo.Retrofit.FileApi;
import com.example.hillavas.tipnoo.Retrofit.RetroClient;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import fisk.chipcloud.ChipCloud;
import fisk.chipcloud.ChipCloudConfig;
import fisk.chipcloud.ChipDeletedListener;
import fisk.chipcloud.ChipListener;
import fisk.chipcloud.ToggleChip;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.View.*;

public class SearchActivity extends AppCompatActivity {
RecyclerView searchrecyclerView;
Context cx;
    TagCloudView chipCloud;
    Button button;
    List selectedChips;
    List<TagList> contentLists;
    FlexboxLayout flexboxLayout;
    String[] demoArray;
    ArrayList<String> arrayList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

       getAllTags();


       button=findViewById(R.id.button);
        flexboxLayout = (FlexboxLayout) findViewById(R.id.flexbox_layout);

        ChipCloudConfig config = new ChipCloudConfig()
                .selectMode(ChipCloud.SelectMode.multi)
                .checkedChipColor(Color.parseColor("#c43423"))
                .checkedTextColor(Color.parseColor("#ffffff"))
                .uncheckedChipColor(Color.parseColor("#efefef"))
                .uncheckedTextColor(Color.parseColor("#666666"))
                .useInsetPadding(true);
        chipCloud = new TagCloudView(SearchActivity.this, flexboxLayout, config);


       button.setOnClickListener(new OnClickListener() {
           @Override
           public void onClick(View v) {
               selectedChips = chipCloud.getSelectedTagByIndex();
              // getSearchResultdContent();
               Intent intent = new Intent(SearchActivity.this,SearchResultActivity.class);
               Bundle bundle=new Bundle();
               intent.putParcelableArrayListExtra("selectedChips", (ArrayList<? extends Parcelable>) selectedChips);
               startActivity(intent);

           }


       });

}

    private void getAllTags() {

        FileApi fileApi = RetroClient.getApiService();
        final Call<TagResults> contentResultCall=fileApi.getSearchTags();
        contentResultCall.enqueue(new Callback<TagResults>() {
            @Override
            public void onResponse(Call<TagResults> call, Response<TagResults> response) {



                if(response.isSuccessful()){

                    arrayList = new ArrayList<String>();
                    contentLists=  response.body().getResult();

                   chipCloud.addTagsChips(contentLists);


                    Toast.makeText(SearchActivity.this,""+response.body().getIsSuccessful(),Toast.LENGTH_SHORT).show();
                    Log.d("---000",response.body().getIsSuccessful().toString());

                }

            }
            @Override
            public void onFailure(Call<TagResults> call, Throwable t) {
                Toast.makeText(SearchActivity.this,""+t,Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    public class TagCloudView extends ChipCloud {

        private ViewGroup layout;
        private List<TagList> tagListArray;
        public TagCloudView(Context context, ViewGroup layout, ChipCloudConfig config) {
            super(context, layout, config);
            this.layout = layout;
        }

        @Override
        public <T> void addChips(T[] objects) {
            super.addChips(objects);
        }


        public void  addTagsChips(List<TagList> objects) {
             tagListArray=objects;
            Iterator var2 = objects.iterator();

            while(var2.hasNext()) {
                TagList object = (TagList) var2.next();
                this.addChip(object.getText());
            }

        }

        // here
        public List<Integer> getSelectedTagByIndex() {
            List<Integer> index = new ArrayList<>();
            for (int i = 0; i < layout.getChildCount(); i++) {
                ToggleChip chip = (ToggleChip) this.layout.getChildAt(i);
                if (chip.isChecked()) {
                    index.add(tagListArray.get(i).getValue());
                }
            }
            return index;
        }

        private int getIndexByView(View view) {
            for (int i = 0; i < layout.getChildCount(); i++) {
                View child = this.layout.getChildAt(i);
                if (child == view) {
                    return i;
                }
            }
            return -1;
        }

//        private boolean keepLastOneCheck(View view) {
//            List<Integer> selectedTagIndex = getSelectedTagIndex();
//            return !(selectedTagIndex.size() == 1 && selectedTagIndex.contains(getIndexByView(view)));
//        }

//        @Override
//        public void onClick(View view) {
//            if (!keepLastOneCheck(view)) {
//                return;
//            }
//            super.onClick(view);
//        }
    }
}
