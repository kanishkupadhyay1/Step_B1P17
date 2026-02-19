/*
 * Project: Social Media Username Availability Checker
 * Author: Kanishk Upadhyay
 * GitHub: https://github.com/kanishkupadhyay1
 * Created: February 2026
 * Version: 1.0
 * Repository: Step_B1P17
 *
 * Description:
 * A Java-based system that allows username registration,
 * availability checking, suggestion generation, and tracks
 * the most popular username attempts using HashMap.
 *
 * © 2026 Kanishk Upadhyay. All rights reserved.
 */



import java.util.*;

public class Social_Media_Username_Availability_Checker {

    Map<String,Integer> UserNameMap = new HashMap<>();
    Map<String,Integer> attempt = new HashMap<>();

    public String register(String UserName,int UserID){
        UserNameMap.put(UserName,UserID);
        return "Registration Successful";
    }

    public Boolean CheckAvailable(String Name){
        attempt.put(Name,attempt.getOrDefault(Name,0)+1);
        return !UserNameMap.containsKey(Name);
    }

    //
    public List<String> SuggestName(String Name){

        List<String> suggestions=new ArrayList<>();

        for(int count = 0 ; count < 3 ; count++){
            String update = Name + count;
            if(!UserNameMap.containsKey(update)){
                suggestions.add(update);
            }
        }
        suggestions.add(Name+"_");
        suggestions.add(Name+".");
        return suggestions;
    }
    public String MostPopularUserName(){

        String Popular=null;
        int MaxAttempt=0;

        for(Map.Entry<String,Integer> entry : attempt.entrySet()){

            if(entry.getValue() > MaxAttempt){

                MaxAttempt = entry.getValue();
                Popular = entry.getKey();
            }
        }
        return Popular;
    }

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
        Social_Media_Username_Availability_Checker system =
                new Social_Media_Username_Availability_Checker();
        System.out.println("Enter new registration count");
        int count = sc.nextInt();
        for(int userID = 1 ; userID <= count ; userID++){
            System.out.println("Enter User name");
            String name = sc.next();
            if(system.CheckAvailable(name)){
                system.register(name,userID);
                System.out.println("Registered Successfully");
            }
            else{
                System.out.println("Username '"+name+"' is not Available !");
                System.out.println("Suggestions: "+system.SuggestName(name));
                userID--; // re enter the name
            }
        }
        System.out.println("The most Popular username is: "+ system.MostPopularUserName());
    }
}
