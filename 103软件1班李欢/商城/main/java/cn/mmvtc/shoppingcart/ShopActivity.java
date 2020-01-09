package cn.mmvtc.shoppingcart;

import android.content.Intent;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;




public class ShopActivity extends Activity implements View.OnClickListener{
    private ListView mListView;
    private ImageView add,car;
    //商品名称与价格数据集合
    private String[] titles = {"大白兔", "阿尔卑斯", "夹心巧克力", "牛轧糖麦片巧克力", "士力架",
            "奶糖", "薄荷糖", "黑糖话梅", "牛轧糖", "绿茶含片"};

    private String[] prices = {"1元/Kg", "1元/Kg", "2元/Kg", "3元/Kg", "9元/Kg",
            "8元/Kg", "6元/Kg", "9元/Kg", "0元/Kg", "3元/Kg"};

    private int[] icons = {R.drawable.aw,R.drawable.bh,R.drawable.ca,R.drawable.wd,R.drawable.js,
            R.drawable.gg,R.drawable.ff,R.drawable.ga,R.drawable.wd,R.drawable.kg};

    SQLiteHelper sqLiteHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        mListView = (ListView) findViewById(R.id.lv);//初始化ListView控件
        add = (ImageView) findViewById(R.id.add);
        car = (ImageView) findViewById(R.id.car);
        MyBaseAdapter mAdapter = new MyBaseAdapter();
        mListView.setAdapter(mAdapter);
        initData();
    }
    protected void initData(){sqLiteHelper = new SQLiteHelper(this);}

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.car:
                Intent intent = new Intent(ShopActivity.this,CarActivity.class);
                startActivityForResult(intent,1);
        }
    }
     class MyBaseAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return titles.length;
        }

        @Override
        public Object getItem(int position) {
            return titles[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null){
                convertView = View.inflate(ShopActivity.this,R.layout.shop_item,null);
                vh = new ViewHolder();
                vh.tvShopTitle = (TextView) convertView.findViewById(R.id.title);
                vh.tvShopPrice = (TextView) convertView.findViewById(R.id.price);
                vh.tvShopiv = (ImageView) convertView.findViewById(R.id.iv);
                vh.tvShopadd = (ImageView) convertView.findViewById(R.id.add);
                convertView.setTag(vh);
                final ViewHolder finalVh = vh;
                vh.tvShopadd.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        String CarTitle = finalVh.tvShopTitle.getText().toString();
                        String CarPrice = finalVh.tvShopPrice.getText().toString();
                        if (sqLiteHelper.insertData(CarTitle,CarPrice)){
                            Toast.makeText(ShopActivity.this,"添加成功",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ShopActivity.this,"添加失败",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }else{
                vh = (ViewHolder)convertView.getTag();
            }
            vh.tvShopTitle.setText(titles[position]);
            vh.tvShopPrice.setText(prices[position]);
            vh.tvShopiv.setBackgroundResource(icons[position]);
            return convertView;
        }
        class ViewHolder {
            TextView tvShopTitle;
            TextView tvShopPrice;
            ImageView tvShopadd;
            ImageView tvShopiv;
        }
    }
}

