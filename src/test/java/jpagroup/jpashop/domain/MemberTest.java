package jpagroup.jpashop.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    @Test
    void 멤버빌더_테스트() throws Exception{
        assertThrows(IllegalArgumentException.class,()->{
            Member m = Member.builder().build();
        });
    }

}