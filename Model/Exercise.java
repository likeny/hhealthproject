package Model;

public class Exercise {
	private String kind;
	private int no;
	private int pkno;
	private String name;
	private int firecal;
	private int time;
	private int totalcal;
	private String images;
	
	public Exercise(String kind, int no, int pkno, String name, int firecal, int time, int totalcal, String images) {
		super();
		this.kind = kind;
		this.no = no;
		this.pkno = pkno;
		this.name = name;
		this.firecal = firecal;
		this.time = time;
		this.totalcal = totalcal;
		this.images = images;
	}
	
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
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
	public int getFirecal() {
		return firecal;
	}
	public void setFirecal(int firecal) {
		this.firecal = firecal;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getTotalcal() {
		return totalcal;
	}
	public void setTotalcal(int totalcal) {
		this.totalcal = totalcal;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	
}
