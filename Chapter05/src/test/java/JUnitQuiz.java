import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class JUnitQuiz {
    //문제 1 - String으로 선언한 변수 3개가 있다. 3개 모두 null이 아니고, name1과 name2는 같은 값을 가지고,
    //name3는 다른 나머지 두 변수와 다른 값을 가지는 데, 이를 검증하는 테스트를 작성해보기.
    @Test
    public void junitTest1(){
        String name1="홍길동";
        String name2="홍길동";
        String name3="홍길금";
        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name3).isNotNull();
        assertThat(name1).isEqualTo(name2);
        assertThat(name3).isNotEqualTo(name1);
        assertThat(name3).isNotEqualTo(name2);
    }

    //문제 2 - int로 선언된 변수 3개가 있다. num1=15, num2=0,num3=-5
    //세 변수가 각가 양수,0,음수이고, num1>num2, num3<num2를 보이는 테스트를 작성해보시오.
    @Test
    public void junitTest2(){
        int num1=15,num2=0,num3=-5;
        assertThat(num1).isPositive();
        assertThat(num2).isZero();
        assertThat(num3).isNegative();
        assertThat(num1).isGreaterThan(num2);
        assertThat(num2).isGreaterThan(num3);
    }
}
