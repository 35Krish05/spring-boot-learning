//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
      for (int i=1;i<=4;i++){
          //space
          for (int j=4;j>=i;j--){
              System.out.print(" ");
          }

          //star
          System.out.print("*");

          //space
          for (int k=1;k<=i*2-1;k++){
              System.out.print(" ");
          }
          System.out.print("*");


          //star
          System.out.println();


      }
      for (int i=1;i<=11;i++){
          System.out.print("*");
      }
    }
}