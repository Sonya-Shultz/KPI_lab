package main.java.serverPart.specificationConcret;

import java.util.ArrayList;

import main.java.serverPart.Objects.Menu;
import main.java.serverPart.Objects.Product;
import main.java.serverPart.Specification.AbstractSpecification;

public class ProductInNextMenuSpecification extends AbstractSpecification<Product> {
    ArrayList<Menu> menu;
    public ProductInNextMenuSpecification(ArrayList<Menu> menu) {
        this.menu = menu;
	}

	@Override
	public boolean isSatisfiedBy(Product t) {
        for (int i=0; i<menu.size(); i++){
            if (menu.get(i).dish.products_list.contains(t))
                return true;
        }
        return false;
	}
}