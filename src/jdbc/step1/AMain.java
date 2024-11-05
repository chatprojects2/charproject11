package jdbc.step1;
class A{

}
public class AMain {
    public static void main(String[] args) {
        A[] as=new A[5];
        System.out.println(as.length);//NullPointerException
        for(A a1:as){
            System.out.println(a1);//5번 출력 - null
        }
        //System.out.println(null.length);
        A a=new A();
        System.out.println(a);//주소번지와 as[0]의 주소번지가 같다.
        as[0]=a;
        System.out.println(as[0]);
        as[0]=new A();
        System.out.println(as[0]);//여기 주소번지가 다르다.
    }
}
