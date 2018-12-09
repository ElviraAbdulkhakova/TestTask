package content;

public enum SortingType {
	ASCENDING_PRICE("По возрастанию цены"), DESCENDING_PRICE("По убыванию цены"), BY_NAME("По наименованию"),
	DISCUSSED("Обсуждаемые"), BY_RATING("По рейтингу");

	String value;

	private SortingType(String value) {
		this.value = value;
	}

	public String getValue() throws Exception {
		return this.value;
	}

}
