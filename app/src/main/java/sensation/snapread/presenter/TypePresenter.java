package sensation.snapread.presenter;

import java.util.ArrayList;
import java.util.List;

import sensation.snapread.contract.TypeContract;
import sensation.snapread.model.ModelRepository;
import sensation.snapread.model.vopo.TypeItemVO;

/**
 * Created by Alan on 2016/10/7.
 */
public class TypePresenter implements TypeContract.Presenter {

    TypeContract.View view;

    public TypePresenter(ModelRepository repository, TypeContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void getTypeList(String userID) {
        view.showLoading();
        //TODO 获取typeList

        final String imgUrl1 = "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430",
                imgUrl2 = "http://img5.imgtn.bdimg.com/it/u=3504299437,2517153506&fm=21&gp=0.jpg",
                imgUrl3 = "http://c.hiphotos.baidu.com/image/pic/item/f636afc379310a55515bfd76b54543a982261030.jpg",
                imgUrl4 = "http://e.hiphotos.baidu.com/image/pic/item/29381f30e924b899659202a26c061d950b7bf6fa.jpg";
        List<TypeItemVO> typeList = new ArrayList<>();
        TypeItemVO vo1 = new TypeItemVO("1", imgUrl1, "Design", "主要关于Material Design"),
                vo2 = new TypeItemVO("2", imgUrl2, "Tool", "一些生活工作常用工具"),
                vo3 = new TypeItemVO("3", imgUrl3, "People", "一些照片"),
                vo4 = new TypeItemVO("4", imgUrl4, "Work", "工作上的文章和事情");
        typeList.add(vo1);
        typeList.add(vo2);
        typeList.add(vo3);
        typeList.add(vo4);

        view.setTypesList(typeList);
        view.hideLoading();
    }

    @Override
    public void addType(String typeName, String description, String imgPath) {

    }

    @Override
    public void deleteTypes(List<String> typeID) {

    }

    @Override
    public void start() {
        getTypeList(PresenterCache.getInstance().getUserID());
    }
}
