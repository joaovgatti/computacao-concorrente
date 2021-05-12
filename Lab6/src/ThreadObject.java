public class ThreadObject extends Thread {

    private final int tid;
    private final int nthread;
    SharedResource resource;

    public ThreadObject(int tid,int nthread, SharedResource resource){
        this.tid = tid;
        this.nthread = nthread;
        this.resource = resource;
    }

    public void run(){
        System.out.println("Thread " + this.tid + " start");
        this.resource.increment(this.tid, this.nthread);
        System.out.println("Thread " + this.tid + " end");
    }

}
