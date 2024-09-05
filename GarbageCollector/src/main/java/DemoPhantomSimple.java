import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;

public class DemoPhantomSimple {
    // Main driver method
    public static void main(String[] args) {
        // Creating a reference queue of HelperClass type
        ReferenceQueue<Object> referenceQueue = new ReferenceQueue<>();

        // Creating a strong object
        Object obj = new Object();

        // Creating a phantom reference object using rq
        Helper helper = new Helper(obj, referenceQueue);
        System.out.println("Calling Display Function using strong object:");
        helper.display();

        //Обязательно поместить в очередь
        helper.enqueue();


//referenceQueue.get() даст null, а referenceQueue.poll() можно вызвать только один раз

        Reference<?> referenceFromQueue = referenceQueue.poll();
        System.out.println("Reference in queue:");
        ((Helper) referenceFromQueue).display();

        // Display message for better readability
        System.out.println("Object set to null");
        obj = null;
        System.gc();
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display status of  objects after fetching
        System.out.println("Calling Display Function after retrieving from weak Object");
        try {
            ((Helper) referenceQueue.poll()).display();
        } catch (Exception E) {
            System.out.println("Error : " + E);
        }
    }
}

class Helper extends PhantomReference<Object> {

    public Helper(Object referent, ReferenceQueue<? super Object> q) {
        super(referent, q);
    }

    // Method inside HelperClass
    void display() {
        // Print statement whenever the function is called
        System.out.println("Display Function invoked ...");
    }
} 