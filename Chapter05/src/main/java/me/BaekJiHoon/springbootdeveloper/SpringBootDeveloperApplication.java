package me.BaekJiHoon.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
    1. 테스트 코드 개념 익히기
        테스트 코드는 작성한 코드가 의도대로 잘 동작하고 예상치 못한 문제가 없는지 확인할 목적으로 작성하는 코드.
        장점:
            1) 유지 보수에 편리
            2) 코드 수정 시 기존 기능이 제대로 작동하지 않을까봐 걱정할 필요 없음.
        테스트 코드는 test 디렉토리에서 작업함 -> src의 하위폴더, main과 같은 라인에 있음.
        given-when-then 패턴
            1) given : 테스트 실행을 준비하는 단계
            2) when : 테스트를 진행하는 단계
            3) then : 테스트 결과를 검증하는 단계

        ex) 새로운 메뉴를 저장하는 코드를 테스트한다.
        @Display("새로운 메뉴를 저장한다.")
        @Test
        public void savedMenuTest(){
            //1. given
            final String name = "아메리카노";
            final int price = 2000;
            final Menu americano = new Menu(name, price);
            //2. when
            final long saveId = menuService.save(americano)
            //3. then :
            final Menu savedMenu = menu.Service.findByID(savedId).get();
            assertThat(savedMenu.getName()).isEqualto(name);
            assertThat(savedMenu.getPrice()).isEqualto(price);

    2. 스프링 부트 3과 테스트
        스프링 부트는 애플리케이션을 테스트하기 위한 annotation을 제공.
        spring-boot-start-test 스타터에 테스트를 위한 도구가 모여있음.

        스프링 부트 스타터 테스트 목록
            1) JUnit: 자바 프로그래밍 언어용 단위테스트 프레임워크
            2) AssertJ: 검증문인 Assertion을 작성하는데 사용하는 라이브러리

            JUnit? - 자바 언어를 위한 단위테스트 프레임워크
                * 단위 테스트 - 작성한 코드가 의도대로 작동하는지 작은 단위로 검증하는 것. 보통 메서드 단위.
            특징
                1) 테스트 방식을 구현할 수 있는 Annotation 제공
                2) @Test Annotation으로 메서드를 호출할 때마다 새 인스턴스를 생성, 독립테스트가 가능
                3) 예상 결과를 검증하는 Assertion 메서드 제공
                4) 사용 방법이 단순, 테스트 코드 작성 시간이 적음
                5) 자동 실행, 자체 결과를 확인하고 실패한 지점을 보여줌.
    AssertJ로 검증문 가독성 높이기

        AssertJ는 JUnit과 함께 사용해 검증문의 가독성을 높여주는 라이브러리
        예를 들어 아까 작성한 AssertEquals(sum,a+b)에서 뭐가 기댓값이고 뭐가 비교값인지 구분이 안됨.

        AssertJ를 이용한 코드 라인
        assertThat(a+b).isEqualTo(sum); a+b를 한 값이 sum과 같아야 한다는 가독성이 아까보다 나음.

        자주 사용하는 메서드 목록
        1) isEqualTo(A) 2) isNotEqualTo(A) 3) contains(A) 4) doesNotContain(A)
        5) startsWith(A) -> 접두사 6) endsWith(A) -> 접미사 7) isEmpty() 8) isNotEmpty()
        9) isPositive() 10) isNegative() 11) isGreaterThan(1) 12) isLessThan(1)
        13) isNotNull() 14) isNull()
 */
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}