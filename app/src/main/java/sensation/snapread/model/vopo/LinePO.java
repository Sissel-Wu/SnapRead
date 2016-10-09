package sensation.snapread.model.vopo;

import java.util.List;

/**
 * Created by Alan on 2016/10/1.
 */
public class LinePO {
    String boundingBox;
    List<WordPO> words;

    public String getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<WordPO> getWords() {
        return words;
    }

    public void setWords(List<WordPO> words) {
        this.words = words;
    }
}
