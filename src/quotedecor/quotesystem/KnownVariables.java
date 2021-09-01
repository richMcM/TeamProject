package quotedecor.quotesystem;

public class KnownVariables {
    public int coverage1LtrPaintMtr2;
    public double coverageWallpaperRollM2;
    public double aveCeilingHeightM;
    public double aveWoodtrimWidthM;
    public double costWallpaperRoll;
    public double costWhite10ltr;
    public double costWhite5ltr;
    public double costColor10ltr;
    public double costColor5ltr;
    public double costColor2point5ltr;
    public double costOilBase5ltr;
    public double costOilBase2point5ltr;
    public double costOilBase1ltr;

    public KnownVariables() {
        // TODO retrieve known_variables from DB and set KnownVariables properties locally
        this.coverage1LtrPaintMtr2 = 7;
        this.coverageWallpaperRollM2 = 2.5;
        this.aveCeilingHeightM = 2.5;
        this.aveWoodtrimWidthM = 0.175;
        this.costWallpaperRoll = 20;
        this.costWhite10ltr = 20;
        this.costWhite5ltr = 28;
        this.costColor10ltr = 55;
        this.costColor5ltr = 45;
        this.costColor2point5ltr = 28;
        this.costOilBase5ltr = 40;
        this.costOilBase2point5ltr = 25;
        this.costOilBase1ltr = 15;
    }

    public int coverage1LtrPaintM2() {
        return coverage1LtrPaintMtr2;
    }

    public double coverageWallpaperRollM2() {
        return coverageWallpaperRollM2;
    }

    public double aveCeilingHeightM() {
        return aveCeilingHeightM;
    }

    public double aveWoodtrimWidthM() {
        return aveWoodtrimWidthM;
    }

    public double costWallpaperRoll() {
        return costWallpaperRoll;
    }

    public double costWhite10ltr() {
        return costWhite10ltr;
    }

    public double costWhite5ltr() {
        return costWhite5ltr;
    }

    public double costColor10ltr() {
        return costColor10ltr;
    }

    public double costColor5ltr() {
        return costColor5ltr;
    }

    public double costColor2point5ltr() {
        return costColor2point5ltr;
    }

    public double costOilBase5ltr() {
        return costOilBase5ltr;
    }

    public double costOilBase2point5ltr() {
        return costOilBase2point5ltr;
    }

    public double costOilBase1ltr() {
        return costOilBase1ltr;
    }
}
