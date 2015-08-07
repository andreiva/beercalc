package oulu.beercalc.model;

public class Item {
	
	private long id;
	private String name;
	private double cost;
	private String time;
	private String date;

	public Item(long id, String name, double cost, String time, String date) {
		super();
		this.id = id;
		this.name = name;
		this.cost = cost;
		this.time = time;
		this.date = date;
	}

	public Item(String name, double cost, String time, String date) {
		super();
		this.name = name;
		this.cost = cost;
		this.time = time;
		this.date = date;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return name + ", "+ cost + "€, "+ date + "-" + time;
	}


}
