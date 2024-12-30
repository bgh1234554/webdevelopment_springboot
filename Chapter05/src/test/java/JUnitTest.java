import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JUnitTest {
    @DisplayName("1+2=3.") //이름을 명시하는 Annotation
    @Test //테스트를 수행하기 위한 Annotation
    public void junitTest(){
        int a=1,b=2;
        int sum=3;
        assertEquals(sum,a+b); //assertEquals(expected,actual) -> 근데 이거 말고 레벨 업 버전을 사용할 예정.
    }
    @DisplayName("1+3=4.")
    @Test
    public void junitFailedTest(){
        int a=1,b=3;
        int sum=3;
        assertEquals(sum,a+b);
    }
}
