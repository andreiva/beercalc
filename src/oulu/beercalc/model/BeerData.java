package oulu.beercalc.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BeerData {

	
	private List<Category> categorys;
	private List<Item> items;
	
	private static BeerData beerData = null;


	private BeerData() {
		this.categorys = new ArrayList<Category>();
		this.items = new ArrayList<Item>();
	}

	public static BeerData getInstance() {
		if (beerData == null) {
			beerData = new BeerData();
		}
		return beerData;
	}
	
	public static BeerData getDummyData() {
		if (beerData == null) {
			beerData = new BeerData();
		}
		beerData.generateDummyData();
		return beerData;
	}
	
	private void generateDummyData()
	{
		items.add(new Item(1, "Beer", 12.23, "13:30", "28.05.2013"));
		items.add(new Item(2, "Beer", 22.23, "14:30", "29.05.2013"));
		items.add(new Item(3, "Beer", 32.23, "15:30", "30.05.2013"));
		items.add(new Item(3, "Beer", 32.23, "15:30", "30.05.2013"));
		items.add(new Item(3, "Beer", 32.23, "15:30", "30.05.2013"));
		items.add(new Item(3, "Beer", 32.23, "15:30", "30.05.2013"));
		items.add(new Item(3, "Beer", 32.23, "15:30", "30.05.2013"));
		items.add(new Item(3, "Beer", 32.23, "15:30", "30.05.2013"));

		categorys.add(new Category(1, "Beer"));
		categorys.add(new Category(1, "Beer"));
		categorys.add(new Category(2, "K-Market"));
		categorys.add(new Category(3, "Prisma"));
	}

	public List<Category> getCategorys() {
		return categorys;
	}

	public void setCategorys(List<Category> categorys) {
		this.categorys = categorys;
	}
	
	public boolean deleteCategory(Category cat)
	{
		Iterator<Category> iterator = categorys.iterator();
		while (iterator.hasNext()) {
			Category c = iterator.next();
			if(c.getId() == cat.getId() )
			{
				categorys.remove(c);
				return true;
			}
		}
			return false;
	}

	public boolean deleteItem(Item item)
	{
		Iterator<Item> iterator = items.iterator();
		while (iterator.hasNext()) {
			Item c = iterator.next();
			if(c.getId() == item.getId() )
			{
				items.remove(c);
				return true;
			}
		}
			return false;
	}
	
	public List<Item> getItems() {
		return items;
	}
	
	public void addCategory(Category cat)
	{
		categorys.add(cat);
	}
	
	public void addItem(Item item)
	{
		items.add(item);
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
