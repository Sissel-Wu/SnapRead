package sensation.snapread.model.response;

import java.util.List;

import sensation.snapread.model.vopo.RegionPO;

/**
 * Microsoft OCR response
 * Created by Alan on 2016/10/1.
 */
public class OcrResponse {
    String language;
    double textAngle;
    String orientation;
    List<RegionPO> regions;

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public double getTextAngle() {
        return textAngle;
    }

    public void setTextAngle(double textAngle) {
        this.textAngle = textAngle;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    public List<RegionPO> getRegions() {
        return regions;
    }

    public void setRegions(List<RegionPO> regions) {
        this.regions = regions;
    }
}
