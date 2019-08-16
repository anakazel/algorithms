/*
 * Simple marriage problem solved using the Shapley-Gale algorithm
    The stable marriage problem is commonly stated as:

    Given n men and n women, where each person has ranked all members of the 
    opposite sex with a unique number between 1 and n in order of preference, 
    marry the men and women together such that there are no two people of 
    opposite sex who would both rather have each other than their current 
    partners. If there are no such people, all the marriages are "stable".

 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleMarriage {
    
    static int[][] boysPref;
    static int[][] girlsPref;
    static String[] engageStatuses;// ex: 'b0-g1', 'b1-g2', '', ''
    
    private static void match(int[][] boysPref, int n){
        int day = 0;
        List<Integer> freeBoys;
        
        while(!freeBoys().isEmpty()){
            freeBoys = freeBoys();
            System.out.println("Day: " + day);
            day += 1;
            for(int i = 0; i < freeBoys.size(); ++i){
                int boy = freeBoys.get(i);
                int firstGirl = getFirstOption(boy);//first option
                System.out.println("Boy's " + boy + " first preference is " + firstGirl);
                if(isFree(firstGirl)){
                    System.out.println("First girl of boy " + boy + " is free, so engage boy-girl->(" + boy + ":" + firstGirl + ")");
                    engage(boy, firstGirl, true);
                }else{
                    int fiance = fiance(firstGirl);
                    System.out.println("First girl of boy " + boy + " is NOT free cause it already has a fiance " + fiance);
                    if(prefersBetter(firstGirl, boy, fiance)){
                        System.out.println("The girl prefers better this boy than his fiance");
                        engage(boy, firstGirl, true);
                        engage(fiance, firstGirl, false);
                    }else{
                        System.out.println("The girls does NOT prefer this boy than his fiance, "
                                + "so boy can say bye bye to her for good");
                        engage(boy, firstGirl, false);
                        // rejects M, need to remove girl from boys list
                        removeElement(boysPref[boy], firstGirl);
                    }
                }
            }
            System.out.println("");
            System.out.println("Engagements: " + Arrays.asList(engageStatuses));
        }
        System.out.println("The algorithm finished in " + day + " days.");
    }
   
    public static void removeElement(int [] a, int del) {
        for(int idx = 0; idx < a.length; ++idx){
            if(a[idx] == del){
                a[idx] = -1;
            }
        }
    }
    
    private static int getFirstOption(int boy){
        List<Integer> validPreference = new ArrayList<>();
        for(int i = 0; i < boysPref.length; i++){
            if(boysPref[boy][i] != -1){
                validPreference.add(boysPref[boy][i]);
            }
        }
        return validPreference.get(0);
        
    }
    
    /**
     * Returns true if prefers otherBoy, false if prefers currentBoy
     * @param boy1
     * @param boy2
     * @return 
     */
    private static boolean prefersBetter(int girl, int currentBoy, int otherBoy){
        int[] preferences = girlsPref[girl];
        int currentBoyIndex = -1;
        int otherBoyIndex = -1;
        int sum = 0;
        for(int i = 0; i < preferences.length; ++i){
            if(preferences[i] == currentBoy){
                currentBoyIndex = i;
            }
            if(preferences[i] == otherBoy){
                otherBoyIndex = i;
            }
        }
        if(sum == preferences.length){
            return true;
        }else{
            return otherBoyIndex > currentBoyIndex;
        }
    }
    
    private static int fiance(int girl){
         for(int boy = 0; boy < engageStatuses.length; ++boy){
            final String engagement = engageStatuses[boy];
            if(engagement.contains("g" + girl)){
                return boy;
            }
        }
        return -1;
    }
    
    /**
     * Checks if the girl is free: has no fiance
     * @param girl
     * @return 
     */
    private static boolean isFree(int girl){
        for(int boy = 0; boy < engageStatuses.length; ++boy){
            final String engageStatus = engageStatuses[boy];
            if(engageStatus.contains("g" + girl)){
                return false;
            }
        }
        return true;
    }
    
    private static void engage(int boy, int girl, boolean engage){
        if(engage){
            engageStatuses[boy] = "b" + boy + "-" + "g" + girl;
        }else{
            engageStatuses[boy] = "";
        }
    }
    
    /**
     * Returns the first free boy
     * @return 
     */
    private static List<Integer> freeBoys(){
        List<Integer> freeBoys = new ArrayList<>();
        for(int i = 0; i < engageStatuses.length; ++i){
            if(engageStatuses[i].equals("")){
                freeBoys.add(i);
            }
        }
        return freeBoys;// means all boys are engaged
    }
    
    public static void main(String[] args) {
        int n = 5;
        boysPref = new int[n][n];
        girlsPref = new int[n][n];
        engageStatuses = new String[n];
        for(int i = 0; i < engageStatuses.length; ++i){
            engageStatuses[i] = "";
        }
        
        boysPref[0] = new int[]{2, 1, 4, 0, 3};
        boysPref[1] = new int[]{0, 1, 4, 2, 3};
        boysPref[2] = new int[]{3, 2, 1, 0, 4};
        boysPref[3] = new int[]{0, 2, 3, 1, 4};
        boysPref[4] = new int[]{0, 1, 3, 4, 2};
        
        girlsPref[0] = new int[]{2, 4, 1, 0, 3};
        girlsPref[1] = new int[]{4, 1, 0, 3, 2};
        girlsPref[2] = new int[]{3, 2, 4, 0, 1};
        girlsPref[3] = new int[]{0, 1, 2, 3, 4};
        girlsPref[4] = new int[]{1, 2, 3, 0, 4};
        match(boysPref, n);
    }
    
}
