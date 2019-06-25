package in.co.arcus.texvalley;

class MyData {
    private int id;
    private String date,modeofcntct,visitedusers,stagesofhstry;

    public MyData(int id, String date, String modeofcntct, String visitedusers, String stagesofhstry) {
        this.id = id;
        this.date = date;
        this.modeofcntct = modeofcntct;
        this.visitedusers = visitedusers;
        this.stagesofhstry = stagesofhstry;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getModeofcntct() {
        return modeofcntct;
    }

    public void setModeofcntct(String modeofcntct) {
        this.modeofcntct = modeofcntct;
    }

    public String getVisitedusers() {
        return visitedusers;
    }

    public void setVisitedusers(String visitedusers) {
        this.visitedusers = visitedusers;
    }

    public String getStagesofhstry() {
        return stagesofhstry;
    }

    public void setStagesofhstry(String stagesofhstry) {
        this.stagesofhstry = stagesofhstry;
    }
}
