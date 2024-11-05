package jdbc.step1;
// 오라클 서버와 자바 연동하기
// VO(Valued Object)클래스 설계가 필요한 이유
// DTO(Data Transfer Object)클래스 - 부서테이블, 사원테이블, 회원테이블
// 대화 내용을 오라클 서버에 저장하기를 구현해보면 어떨까?
public class EmpPTOMain {
    // 메소드 m은 메소드 오버로딩이다.
    // 메소드 오버로딩이란 파라미터의 갯수가 다르거나 타입이 다르다.
    void m(int empno,String ename,int deptno){ //나열하는건 권장하지 않는다.
        //7566 scott 10
        System.out.println(empno+" "+ename+" "+deptno);
    }
    // 클래스 EmpDTO에는 변수가 세개 선언되어있다.
    // 메소드의 파라미터 자리는 사용자가 입력한 값을 받는 자리 입니다.
    // 사용자와 어플리케이션과 소통이 시작된다.
    void m(EmpDTO edto){
        //0 null 0
        //8000 king 50
        System.out.println(edto.getEmpno()+" "+edto.getEname()+" "+edto.getDeptno());
    }
    public static void main(String[] args) {
        EmpPTOMain etm=new EmpPTOMain();
        EmpDTO dto2=new EmpDTO();
        dto2.setEmpno(8000);//private int empno=8000 - 캡슐화
        dto2.setEname("king");
        dto2.setDeptno(50);
        etm.m(dto2);
        etm.m(7566,"scott",10);
        EmpDTO dto=new EmpDTO();
        dto.setEmpno(7566);
        dto.setEmpno(7499);
        System.out.println(dto.getEmpno());
    }
}
