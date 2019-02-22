package Model;

public class Food {
	private String kind;
	private String kategorie;
	private int no;
	private int pkno;
	private String name;
	private int caloriePerGram;
	private int volume;
	private int totalCal;
	
	

	public Food(String kind, String kategorie, int no, int pkno, String name, int caloriePerGram, int volume,
			int totalCal) {
		super();
		this.kind = kind;
		this.kategorie = kategorie;
		this.no = no;
		this.pkno = pkno;
		this.name = name;
		this.caloriePerGram = caloriePerGram;
		this.volume = volume;
		this.totalCal = totalCal;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getKategorie() {
		return kategorie;
	}

	public void setKategorie(String kategorie) {
		this.kategorie = kategorie;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public int getPkno() {
		return pkno;
	}

	public void setPkno(int pkno) {
		this.pkno = pkno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCaloriePerGram() {
		return caloriePerGram;
	}

	public void setCaloriePerGram(int caloriePerGram) {
		this.caloriePerGram = caloriePerGram;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public int getTotalCal() {
		return totalCal;
	}

	public void setTotalCal(int totalCal) {
		this.totalCal = totalCal;
	}
}
