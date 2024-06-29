package Entity;

public enum EStatus {
    C("Completed"), P("Pending"), R("Reject");
    private String status;
    EStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return status;
    }
}
