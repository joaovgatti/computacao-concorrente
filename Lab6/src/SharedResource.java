public class SharedResource {

    private final int[] sharedVector;

    public SharedResource(int vectorSize){
        this.sharedVector = new int[vectorSize];
    }

    public void increment(int id, int nthreads){
        int start = getStartIndex(id,getBlockSize(nthreads));
        int end = getEndInterval(id, getBlockSize(nthreads),nthreads,start);

        for(int i = start; i < end; i++){
            this.sharedVector[i] += 1;
        }
    }

    public void showVector(){
        System.out.println("Printing the shared resource");
        for (int j : sharedVector) {
            System.out.println(j + " ");
        }
    }

    private int getBlockSize(int nthread){
        return sharedVector.length / nthread;
    }

    private int getStartIndex(int id, int blockSize){
        return id * blockSize;
    }

    private int getEndInterval(int id, int blockSize, int nthread, int start){
        if(id == nthread - 1) return sharedVector.length;
        return start + blockSize;
    }
}
