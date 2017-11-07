package visitor;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class myclassComparator implements Comparator<myclass> {
	public int compare(myclass o1, myclass o2)
    {
		if(!o1.birth.equals(o2.birth)) return o1.birth.compareTo(o2.birth);
		else if(!o1.death.equals(o2.death)) return o1.death.compareTo(o2.death);
        else return o1.tempno.compareTo(o2.tempno);
    }
}
