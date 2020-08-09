import java.util.ArrayList;
import java.util.Iterator;

public class set<T> implements SetInterface<T>{

    ArrayList<T> a;

    public set()
    {
        a = new ArrayList<T>();
    }

    @Override
    public boolean add(T o) {
        for (int x = 0; x < a.size(); x++) {
            if(a.get(x).equals(o))
                return false;
        }
        a.add(o);
        return true;
    }

    @Override
    public boolean contains(T o) {
        return a.contains(o);
    }

    @Override
    public boolean remove(T o) {
        return a.remove(o);
    }

    @Override
    public boolean isEmpty() {
        return a.isEmpty();
    }

    @Override
    public Iterator<T> iterator() {
        return a.iterator();
    }

    @Override
    public void clear()
    {
        a.clear();
    }

    @Override
    public int size()
    {
        return a.size();
    }

    @Override
    public Object[] toArray() {
        return a.toArray();
    }


}
