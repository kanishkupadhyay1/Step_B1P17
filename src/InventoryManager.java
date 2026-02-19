/*
 * Project: E-commerce Flash Sale Inventory Manager
 * Author: Kanishk Upadhyay
 * GitHub: https://github.com/kanishkupadhyay1
 * Created: February 2026
 * Version: 1.0
 * Repository: Step_2026
 *
 * Description:
 * Developed a Java-based concurrent inventory manager
 * for flash sale scenarios using ConcurrentHashMap and
 * AtomicInteger to ensure thread-safe stock updates and
 * prevent overselling.
 * Implemented O(1) stock lookup, FIFO waiting list management,
 * and simulated concurrent user requests using ExecutorService.
 *
 *
 * © 2026 Kanishk Upadhyay. All rights reserved.
 */

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

//purchaseItem
//getWaitingPosition
public class InventoryManager {

    //store stock info
    ConcurrentHashMap<String , AtomicInteger> inventory=new ConcurrentHashMap<>();

    //store waiting list per product
     ConcurrentHashMap<String, ConcurrentLinkedQueue<Long>> Waitinglist=new ConcurrentHashMap<>();

     //store the successfull purchase per product
     ConcurrentHashMap<String, Set<Long>> buyers=new ConcurrentHashMap<>();


//initialize inventory

   public void addProduct(String productID,int stock){
       inventory.put(productID,new AtomicInteger(stock));
       Waitinglist.put(productID,new ConcurrentLinkedQueue<>());
       buyers.put(productID,ConcurrentHashMap.newKeySet());
   }
   public int CheckStock(String productID){
       AtomicInteger stock=inventory.get(productID);
       return (stock==null)? 0 : stock.get();
   }


   //purchase item
   public void PurchaseItem(String ProductID,long UserID){
       AtomicInteger stock=inventory.get(ProductID);
if(stock==null){
    System.out.println("product not found");
    return;
}
while(true) {
    int currentStock=stock.get();
    if (currentStock<=0) {
        System.out.println("product is currently unavailable!... Entering waiting list.");
        ConcurrentLinkedQueue<Long> queue = Waitinglist.get(ProductID);
        queue.add(UserID);
        System.out.println("Current status WL:"+queue.size());
        return;
    }
    if(stock.compareAndSet(currentStock,currentStock-1)){
        buyers.get(ProductID).add(UserID);
        System.out.println("purchase successful !");
        return;
    }

        }
   }

   // get the exact position in waitinglist
   public int getWaitingStatus(String productID,long userID){
       ConcurrentLinkedQueue<Long> queue=Waitinglist.get(productID);
       if(queue==null){
           System.out.println("No queue exist!");
           return -1;
       }
       int position=1;
       for(long id: queue){
           if(userID==id){
               return position;
           }
           position++;
       }
       return -1;
   }

    public static void main(String[] args) throws InterruptedException {

        InventoryManager manager = new InventoryManager();

        // Add product
        manager.addProduct("IPHONE15", 5);

        // Check stock
        System.out.println("Initial stock: " + manager.CheckStock("IPHONE15"));

        // simulate multiple users using thread pool
        ExecutorService executor = Executors.newFixedThreadPool(3);

        for (long user = 1; user <= 10; user++) {

            long userId = user;

            executor.submit(() -> {
                manager.PurchaseItem("IPHONE15", userId);
            });
        }

        executor.shutdown();

        executor.awaitTermination(10, TimeUnit.SECONDS);

        // Final stock
        System.out.println("Final stock: " +
                manager.CheckStock("IPHONE15"));


        // Check waiting list position
        System.out.println("User 7 waiting position: " +
                manager.getWaitingStatus("IPHONE15", 7));

    }
}

