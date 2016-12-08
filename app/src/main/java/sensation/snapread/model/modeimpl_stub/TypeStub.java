package sensation.snapread.model.modeimpl_stub;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import sensation.snapread.model.modelinterface.TypeModel;
import sensation.snapread.model.response.Response;
import sensation.snapread.model.vopo.TypePO;

/**
 * Created by Alan on 2016/10/10.
 */
public class TypeStub implements TypeModel {
    @Override
    public void addType(Subscriber<Response<Object>> subscriber, String userID, String name, String description, String imgUrl) {
        subscriber.onNext(null);
    }

    @Override
    public void getTypeList(Subscriber<Response<List<TypePO>>> subscriber, String userID) {
        List<TypePO> typeList = new ArrayList<>();
        TypePO type1 = new TypePO(
                "1",
                "https://camo.githubusercontent.com/5f260ff56ba9dd4accf22a9572a9874556704bf9/687474703a2f2f75706c6f61642d696d616765732e6a69616e7368752e696f2f75706c6f61645f696d616765732f333037323536362d386564663232356566306266646263332e706e673f696d6167654d6f6772322f6175746f2d6f7269656e742f7374726970253743696d61676556696577322f322f772f31323430",
                "设计",
                "主要是关于Material Design");
        TypePO type2 = new TypePO(
                "2",
                "http://img.taopic.com/uploads/allimg/140226/234991-14022609204234.jpg",
                "城市",
                "旅游城市的种草!");
        TypePO type3 = new TypePO(
                "3",
                "http://img2.3lian.com/2014/f7/11/d/97.jpg",
                "生活",
                "一些生活中的小技术，小技巧");
        typeList.add(type1);
        typeList.add(type2);
        typeList.add(type3);
        Response<List<TypePO>> typeResponse = new Response<>("success", "", "", typeList);
        subscriber.onNext(typeResponse);
    }
}
