package org.example;

import java.util.Calendar;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class SlidingWindow {

    private static  ConcurrentLinkedQueue<Request> queue = new ConcurrentLinkedQueue<>();
    private static final long timeFrameInMillis=5;
    private static final long numOfRequest=5;
    private static final ReentrantLock reentrantLock = new ReentrantLock();

    public static  void addRequest(String requestMessage){
        long currentTime = Calendar.getInstance().getTimeInMillis();

        reentrantLock.lock();
        int size=queue.size();

        if(size<numOfRequest){


            queue.add(new Request(requestMessage, currentTime));

            System.out.println("REQUEST RECEIVED:  STATUS 200 OK "+requestMessage);
        }else{
            if(canAddRequest(currentTime)){
                Request req=queue.poll();

                queue.add(new Request(requestMessage, currentTime));


                System.out.println("REQUEST RECEIVED: STATUS 200 OK "+requestMessage+" AFTER CLEARING "+req.getMessage());
            }else{
                System.out.println("REQUEST CAN NOT BE PROCESSED : STATUS 429 TOO MANY REQUESTS");

            }
        }
        reentrantLock.unlock();
    }
    public static  boolean canAddRequest(long requestTime){
        if(requestTime-queue.peek().getTimeStamp()>timeFrameInMillis){
//            queue.poll();
            return true;
        }
        return false;

    }
}
