import java.util.Arrays;
import java.util.Scanner;

public class Task1 {
    public static void  main(String[] args){
        Scanner in = new Scanner(System.in);
        System.out.print("Input words: ");
        String text = in.nextLine();
        String[] wordsArray = text.split(" ");
        String simplifiedText = "";
        for(String it: wordsArray){
            simplifiedText += textSimplifier(it)+ " ";
        }
        System.out.println(simplifiedText);

    }


    public static  String textSimplifier(String text){
        if(text.length()==0) return null;
        StringBuilder result = new StringBuilder(text);

        if(result.toString().equalsIgnoreCase("THE") || result.toString().equalsIgnoreCase("A") || result.toString().equalsIgnoreCase("AN")){
            return "";
        }

        for(int i = 0;i<result.length()-1;i++) {
            if (result.charAt(i) == 'c') {
                switch (result.charAt(i + 1)) {
                    case 'k' -> result.deleteCharAt(i);
                    case 'i', 'e' -> result.setCharAt(i, 's');
                    default -> result.setCharAt(i, 'k');
                }
            }
        }

        if (result.charAt(result.length()-1)=='c'){
            result.setCharAt(result.length()-1,'k');
        }

        for(int i =0;i<result.length()-1;) {
            if (result.charAt(i) == result.charAt(i + 1)) {
                switch (result.charAt(i)) {
                    case 'o' -> result.replace(i, i + 2, "u");
                    case 'e' -> result.replace(i, i + 2, "i");
                    default -> result.deleteCharAt(i);
                }

            } else i++;

            if(i!= 0 && result.charAt(i)==result.charAt(i-1)){
                    switch (result.charAt(i)) {
                        case 'o' -> result.replace(i-1, i + 1, "u");
                        case 'e' -> result.replace(i-1, i + 1, "i");
                        default -> result.deleteCharAt(i);
                    }
            }

        }

        if(result.charAt(result.length()-1)=='e') result.deleteCharAt(result.length()-1);

        return result.toString();
    }


}
