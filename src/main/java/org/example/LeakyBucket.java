package org.example;

import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;


public class LeakyBucket {
    private static PriorityQueue<Request> queue = new PriorityQueue<>((a,b)->(int)(a.getTimeStamp()-b.getTimeStamp()));
    private static final long timeFrame = 5;
    private static final long maxRequests=5;
    private static ReentrantLock lock = new ReentrantLock();

    public static void rateLimit(String message){

        Request request = new Request(message,System.currentTimeMillis());
        lock.lock();

        if(queue.size() < maxRequests){
            queue.add(request);
            System.out.println("REQUEST PROCESSED : STATUS 200 OK "+request.getMessage());
        }else{
            System.out.println("Too many requests : STATUS 429 "+request.getMessage());
        }
        lock.unlock();
    }
    public static void leakFromBucket(){
        lock.lock();
        long now = System.currentTimeMillis();
        while(queue.size()!=0){
            if(now - queue.peek().getTimeStamp()<timeFrame){
                break;
            }
            queue.poll();
            System.out.println("CLEARED THE OLD REQUESTS");
        }
        lock.unlock();
    }
}
