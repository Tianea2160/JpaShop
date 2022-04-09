package jpagroup.jpashop.service;

import com.fasterxml.jackson.databind.deser.std.StdKeyDeserializer;
import jpagroup.jpashop.domain.Member;
import jpagroup.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {
    Logger LOG = Logger.getLogger("log");

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    void 회원가입() throws Exception {
        //given
        Member member = Member.builder()
                .name("kim")
                .build();

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();
        Member findOne = memberRepository.findOne(savedId);
        assertEquals(member, findOne);
    }

    @Test
    void 중복_회원_예외() throws Exception {
        //given
        Member member1 = Member.builder()
                .name("kim")
                .build();

        Member member2 = Member.builder()
                .name("kim")
                .build();
        //when

        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, ()->{
            memberService.join(member2);
        });
    }
}