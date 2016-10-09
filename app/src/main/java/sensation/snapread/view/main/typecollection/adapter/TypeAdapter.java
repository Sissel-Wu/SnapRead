package sensation.snapread.view.main.typecollection.adapter;

import android.content.Context;
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
import sensation.snapread.model.vopo.TypeItemVO;

/**
 * 收藏列表item
 * Created by Alan on 2016/9/24.
 */
public class TypeAdapter extends ArrayAdapter<TypeItemVO> {

    private ListView listView;
    private int resource;

    public TypeAdapter(Context context, int resource, List<TypeItemVO> objects, ListView listView) {
        super(context, resource, objects);
        this.resource = resource;
        this.listView = listView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        TypeItemVO item = getItem(position);

        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resource, null);
        } else {
            view = convertView;
        }

        TextView mTitleView = (TextView) view.findViewById(R.id.type_title);
        mTitleView.setText(item.getTitle());

        TextView mDesView = (TextView) view.findViewById(R.id.type_preview);
        mDesView.setText(item.getDescription());

        ImageView mImgView = (ImageView) view.findViewById(R.id.type_image);
        Glide.with(getContext()).load(item.getImageUrl()).into(mImgView);

        //CheckBox处理
        CheckBox mCheckBox = (CheckBox) view.findViewById(R.id.checkbox);
        if (listView.getChoiceMode() == AbsListView.CHOICE_MODE_MULTIPLE) {
            mCheckBox.setVisibility(View.VISIBLE);
        } else {
            mCheckBox.setVisibility(View.GONE);
        }
        if (listView.isItemChecked(position)) {
            mCheckBox.setChecked(true);
        } else {
            mCheckBox.setChecked(false);
        }

        return view;
    }
}
