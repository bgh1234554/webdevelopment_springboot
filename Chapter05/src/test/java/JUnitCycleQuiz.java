import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JUnitCycleQuiz {
    /*
    각각의 테스트를 시작하기 전에 "Hello!"를 출력하는 메서드와 모든 테스트를 끝마치고 "Bye!"를 출력하는 메서드를 추가하기.
     */
    @BeforeEach
    public void printHello(){
        System.out.println("Hello!");
    }
    @Test
    public void JUnitQuiz3(){
        System.out.println("this is the first test.");
    }
    @Test
    public void JUnitQuiz4(){
        System.out.println("this is the second test.");
    }
    @AfterAll
    static void printBye(){
        System.out.println("Bye!");
    }
    /*
    여기에서 JUnitCycleQuiz 클래스를 전체 실행하면 콘솔에 다음과 같이 출력되려면 어떻게 할지 작성해보기
    Hello!
    this is the first test.
    Hello!
    this is the second test.
    Bye!
     */
}
