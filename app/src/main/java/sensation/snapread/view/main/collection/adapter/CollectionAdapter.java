package sensation.snapread.view.main.collection.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import sensation.snapread.R;
import sensation.snapread.model.vopo.CollectionListItemVO;

/**
 * 收藏列表item
 * Created by Alan on 2016/9/24.
 */
public class CollectionAdapter extends ArrayAdapter<CollectionListItemVO> {

    private ListView listView;
    private int resource;

    public CollectionAdapter(Context context, int resource, List<CollectionListItemVO> objects, ListView listView) {
        super(context, resource, objects);
        this.resource = resource;
        this.listView = listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        CollectionListItemVO item = getItem(position);

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
        } else {
            view = convertView;
        }

        TextView mTypeView = (TextView) view.findViewById(R.id.tag);
        mTypeView.setText(item.getType());

        TextView mTitleView = (TextView) view.findViewById(R.id.type_title);
        mTitleView.setText(item.getTitle());

        TextView mDesView = (TextView) view.findViewById(R.id.post_preview);
        mDesView.setText(item.getDescription());

        ImageView mImgView = (ImageView) view.findViewById(R.id.type_image);
        if (item.getImgUrl().equals("")) {
            mImgView.setVisibility(View.GONE);
        } else {
            mImgView.setVisibility(View.VISIBLE);
            Glide.with(getContext()).load(item.getImgUrl()).into(mImgView);
        }

        //ShareView处理
        final String title = item.getTitle(),
                url = item.getUrl(),
                imgUrl = item.getImgUrl();
        ImageView mShareView = (ImageView) view.findViewById(R.id.share_icon);
        mShareView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, title);
                intent.putExtra(Intent.EXTRA_STREAM, imgUrl);
                intent.putExtra(Intent.EXTRA_TEXT, title + "\n" + url + "\n\n--来自SnapRead");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(Intent.createChooser(intent, "分享内容到"));
            }
        });

        //CheckBox处理
        CheckBox mCheckBox = (CheckBox) view.findViewById(R.id.checkbox);
        if (listView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {
            mCheckBox.setVisibility(View.VISIBLE);
            mShareView.setVisibility(View.GONE);
        } else {
            mCheckBox.setVisibility(View.GONE);
            mShareView.setVisibility(View.VISIBLE);
        }
        if (listView.isItemChecked(position)) {
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }

        return view;
    }
}
