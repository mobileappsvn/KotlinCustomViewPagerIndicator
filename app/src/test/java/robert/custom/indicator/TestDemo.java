package robert.custom.indicator;

import org.junit.Test;

import java.util.Set;
import java.util.HashSet;

/**
 * Created by robert on 11/29/17.
 */
public class TestDemo {
    @Test
    public void uniqueVal() throws Exception {

        class UniqueVal {
            int a;

            public UniqueVal(int a) {
                this.a = a;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;

                UniqueVal uniqueVal = (UniqueVal) o;

                return a == uniqueVal.a;
            }

            @Override
            public int hashCode() {
                return a;
            }

            @Override
            public String toString() {
                return String.valueOf(a);
            }

        }

        Set<UniqueVal> q = new HashSet<UniqueVal>();
        q.add(new UniqueVal(1));
        q.add(new UniqueVal(1));
        q.add(new UniqueVal(10));
        q.add(new UniqueVal(4));
        q.add(new UniqueVal(4));
        q.add(new UniqueVal(8));
        q.add(new UniqueVal(3));
        q.add(new UniqueVal(33));
        q.add(new UniqueVal(8));
        q.add(new UniqueVal(99));
        q.add(new UniqueVal(11));
        q.add(new UniqueVal(99));

        for(Object object : q) {
            System.out.println("===>element=" + object);
        }
        //System.out.println(q);
    }

}
