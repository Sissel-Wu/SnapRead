package sensation.snapread.model.vopo;

import java.util.List;

/**
 * Created by Alan on 2016/10/1.
 */
public class RegionPO {
    String boundingBox;
    List<LinePO> lines;

    public String getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(String boundingBox) {
        this.boundingBox = boundingBox;
    }

    public List<LinePO> getLines() {
        return lines;
    }

    public void setLines(List<LinePO> lines) {
        this.lines = lines;
    }
}
