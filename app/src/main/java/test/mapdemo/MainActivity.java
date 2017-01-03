package test.mapdemo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import test.mapdemo.basic.BasicMap2DActivity;
import test.mapdemo.location.LocationSourceActivity;

public class MainActivity extends AppCompatActivity {

    private RecyclerView list;

    private Items[] items = {
        new Items("基本2D地图", BasicMap2DActivity.class),
        new Items("基本定位", LocationSourceActivity.class)
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        list = (RecyclerView) findViewById(R.id.list);
        ListAdapter adapter = new ListAdapter();
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(adapter);
    }

    private class ListAdapter extends RecyclerView.Adapter<ListViewHolder> {
        @Override
        public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ListViewHolder(getLayoutInflater().inflate(R.layout.view_list_item, parent, false));
        }

        @Override
        public void onBindViewHolder(ListViewHolder holder, int position) {
            holder.title.setText(items[position].title);
        }

        @Override
        public int getItemCount() {
            return items.length;
        }
    }

    private class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        public ListViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(items[getLayoutPosition()]);
                }
            });
        }
    }

    private class Items {
        private String title;
        private Class<? extends android.app.Activity> activityClass;

        private Items(String title, Class<? extends android.app.Activity> activityClass) {
            this.title = title;
            this.activityClass = activityClass;
        }
    }

    private void onItemClick(Items items) {
        startActivity(new Intent(this, items.activityClass));
    }

    private void checkPermission() {
        PackageManager pm = getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.ACCESS_FINE_LOCATION", "test.mapdemo"));
        if (permission) {

        }else {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            }, 0x11);
        }
    }
}