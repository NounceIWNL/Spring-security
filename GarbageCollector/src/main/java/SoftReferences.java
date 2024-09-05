import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class SoftReferences {
    public static void main(String[] args) {
     new SoftReferences().demoOutOfMemory();


    }

    public void demo() {
        /*
        1. Создать объект
        2. Поместить его в контейнер SoftReference
        3. Присвоить объекту значение null
         */

        //2 способа создания soft reference
        String str1 = "String";
        SoftReference<String> ref1 = new SoftReference<>(str1);

        ReferenceQueue<String> referenceQueue = new ReferenceQueue<>();
        SoftReference<String> ref2 = new SoftReference<>(str1, referenceQueue);


        str1 = null;
        String s1 = ref1.get();
        System.out.println(s1);
//        ref1.clear();
        String s2 = ref1.get();
        System.out.println(s2);

        String s3 = ref1.get();
        if (s3 != null)
            System.out.println(s3);
        else throw new NullPointerException("String is null");
    }

    public void demoOutOfMemory() {
        System.out.println(Runtime.getRuntime().freeMemory());
        BigInt big = new BigInt();
        System.out.println(Runtime.getRuntime().freeMemory());

        SoftReference<BigInt> ref1 = new SoftReference<>(big);
        big = null; //Хотя объект удален, но памяти не прибавилось
        System.out.println(Runtime.getRuntime().freeMemory());

        List<Long[]> list = new ArrayList<>();

        long i = 0;
        while (true) {

            i++;
            Long[] arr = new Long[1024 * 1024];
            //Long[] arr = new Long[1024*1024]; //8 MB - сборщик не успевает удалять SoftReference
            list.add(arr);
            if (Runtime.getRuntime().freeMemory() < 1024 * 1024 * 8) {
                System.out.println(Runtime.getRuntime().freeMemory());
                System.gc();
            }
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            BigInt bigObj = ref1.get();
            if (bigObj == null) {
                System.out.println("Big is removed by GC on " + i + " iteration");
                System.out.println(Runtime.getRuntime().freeMemory());
                break;
            }
            System.out.println(Runtime.getRuntime().freeMemory());
        }
    }
}

class BigInt {
    private int[] arr = new int[1024 * 1024 * 25]; //400Mb
}

class BigStr {
    private String str = "a";

    BigStr() {
        for (int i = 0; i < 20; i++) //1 Мб
            str = str + str;
    }
}
