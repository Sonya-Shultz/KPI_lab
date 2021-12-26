package main.java.serverPart.specificationConcret;

import java.util.ArrayList;

import main.java.serverPart.Objects.Reserve;
import main.java.serverPart.Objects.Table;
import main.java.serverPart.Specification.AbstractSpecification;

public class FreeTableScecification extends AbstractSpecification<Table> {

	private ArrayList<Reserve> reserve;
    private String date;

    public FreeTableScecification(ArrayList<Reserve> res, String date) {
		this.reserve=res;
        this.date=date;
	}

	@Override
	public boolean isSatisfiedBy(Table t) {
        for (int i=0; i<this.reserve.size(); i++){
            if (this.reserve.get(i).id==t.id && this.reserve.get(i).date_time.substring(0, 10).equals(this.date) &&
            this.reserve.get(i).special_description.equals(t.from))
                return false;
        }
        return true;
	}
}
