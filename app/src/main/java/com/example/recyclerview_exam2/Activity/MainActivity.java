package com.example.recyclerview_exam2.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;

import com.example.recyclerview_exam2.Adapter.ListAdapter;
import com.example.recyclerview_exam2.R;
import com.example.recyclerview_exam2.model.ItemData;
import com.example.recyclerview_exam2.model.ItemImg;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ArrayList<ItemData> itemData;
    private ListAdapter mAdapter;
    private String[] strTitle;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);



            RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLinearLayoutManager);


            itemData = new ArrayList<>();
            itemData.addAll(ItemImg.getListData());

            mAdapter = new ListAdapter( this, itemData);
            mRecyclerView.setAdapter(mAdapter);


            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), mLinearLayoutManager.getOrientation());
            mRecyclerView.addItemDecoration(dividerItemDecoration);

            Button buttonInsert = (Button)findViewById(R.id.button_data);
            buttonInsert.setOnClickListener(new View.OnClickListener() {


                // 1. 화면 아래쪽에 있는 데이터 추가 버튼을 클릭하면
                @Override
                public void onClick(View v) {


                    // 2. 레이아웃 파일 edit_box.xml 을 불러와서 화면에 다이얼로그를 보여줍니다.

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    View view = LayoutInflater.from(MainActivity.this)
                            .inflate(R.layout.edit_box, null, false);
                    builder.setView(view);
                    final Button ButtonSubmit = (Button) view.findViewById(R.id.button_dialog_submit);
                    final EditText editTextTitle = (EditText) view.findViewById(R.id.editText_dialog_title);
                    final EditText editTextDay = (EditText) view.findViewById(R.id.editText_dialog_day);
                    final EditText editTextContent = (EditText) view.findViewById(R.id.editText_dialog_content);

                    ButtonSubmit.setText("삽입");


                    final AlertDialog dialog = builder.create();
                    ButtonSubmit.setOnClickListener(new View.OnClickListener() {
                        public void onClick(View v) {
                            String strTitle = editTextTitle.getText().toString();
                            String strDay = editTextDay.getText().toString();
                            String strContent = editTextContent.getText().toString();

                            ItemData item = new ItemData(strTitle, strDay, strContent);

                            itemData.add(0, item); //첫 줄에 삽입
                            //mArrayList.add(dict); //마지막 줄에 삽입
                            mAdapter.notifyDataSetChanged(); //변경된 데이터를 화면에 반영

                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =(SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });

        return true;
    }
}
