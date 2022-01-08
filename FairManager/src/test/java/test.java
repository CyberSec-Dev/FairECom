public class test {
    public static void main(String[] args){
        double a=Math.log(30)/Math.log(2);
        // int len = (int) Math.pow(2, a);
        int len=0;
        int b=(int)a;
        if(b==a){
            len=(int)Math.pow(2,b);
        }else{
            len=(int)Math.pow(2,b+1);
        }
        System.out.println(len);
    }
}
