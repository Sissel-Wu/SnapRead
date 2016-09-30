package sensation.snapread.view.main.typecollection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import butterknife.BindView;
import butterknife.ButterKnife;
import sensation.snapread.R;
import sensation.snapread.contract.TypeContract;
import sensation.snapread.view.NavigationInterface;

/**
 * 类别碎片
 */
public class TypeFragment extends Fragment implements TypeContract.View {

    @BindView(R.id.type_list)
    ListView mTypeListView;

    NavigationInterface navigationInterface;

    public static TypeFragment newInstance() {
        return new TypeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type, container, false);
        ButterKnife.bind(this, view);
        initViews();
        startPresenter();
        return view;
    }

    private void initViews() {
//TODO listview监听
    }

    private void startPresenter() {
//TODO type presenter
    }

    public void setNavigationInterface(NavigationInterface navigationInterface) {
        this.navigationInterface = navigationInterface;
    }

    @Override
    public void setPresenter(TypeContract.Presenter presenter) {

    }
}
