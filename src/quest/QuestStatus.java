package quest;

public enum QuestStatus {

	AVAILABLE("available"),
	RUNNING("running"),
	COMPLETED("completed");
	
	private String path;

	QuestStatus(String path) {
		this.path = path;
	}
	
	public String getPath() {
		return path;
	}
	
	public static QuestStatus getByPath(String p) {
		for(QuestStatus qs : values()) {
			if(qs.getPath().equals(p)) {
				return qs;
			}
		}
		return null;
	}
	
}