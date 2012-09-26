package com.example.dmtest;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
 
public class GridActivity extends Activity {
  
 public class MyAdapter extends BaseAdapter {
   
  final int NumberOfItem = 30;
  private Bitmap[] bitmap = new Bitmap[NumberOfItem];
   
  private Context context;
  private LayoutInflater layoutInflater;
   
  MyAdapter(Context c){
   context = c;
   layoutInflater = LayoutInflater.from(context);
    
   //init dummy bitmap,
   //using R.drawable.icon for all items
   for(int i = 0; i < NumberOfItem; i++){
    bitmap[i] = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher);
   }
  }
 
  public int getCount() {
   // TODO Auto-generated method stub
   return bitmap.length;
  }
 
  public Object getItem(int position) {
   // TODO Auto-generated method stub
   return bitmap[position];
  }
 
  public long getItemId(int position) {
   // TODO Auto-generated method stub
   return position;
  }
 
  public View getView(int position, View convertView, ViewGroup parent) {
   // TODO Auto-generated method stub
    
   View grid;
   if(convertView==null){
    grid = new View(context);
    grid = layoutInflater.inflate(R.layout.grid_item, null);
   }else{
    grid = (View)convertView;
   }
    
   ImageView imageView = (ImageView)grid.findViewById(R.id.image);
   imageView.setImageBitmap(bitmap[position]);
   TextView textView = (TextView)grid.findViewById(R.id.text);
   textView.setText(String.valueOf(position));
    
   return grid;
  }
 
 }
 
 GridView gridView;
  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid);
        gridView = (GridView)findViewById(R.id.grid);
 
        MyAdapter adapter = new MyAdapter(this);
        gridView.setAdapter(adapter);
    }
     
}
