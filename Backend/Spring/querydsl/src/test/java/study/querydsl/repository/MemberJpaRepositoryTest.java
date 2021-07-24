package study.querydsl.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.querydsl.dto.MemberSearchCondition;
import study.querydsl.dto.MemberTeamDto;
import study.querydsl.entity.Member;
import study.querydsl.entity.Team;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberJpaRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void basicTest() {
        Member member = Member.createMember("member1", 10);
        memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.findById(member.getId()).get();
        assertEquals(member.getId(), findMember.getId());

        List<Member> result1 = memberJpaRepository.findAll();
        Assertions.assertThat(result1).containsExactly(member);

        List<Member> result2 = memberJpaRepository.findByUsername("member1");
        Assertions.assertThat(result2).containsExactly(member);
    }


    @Test
    void searchTest() {
        Team teamA = Team.createTeam("teamA");
        Team teamB = Team.createTeam("teamB");
        em.persist(teamA);
        em.persist(teamB);

        Member member1 = Member.createMember("member1", 10, teamA);
        Member member2 = Member.createMember("member2", 20, teamA);
        Member member3 = Member.createMember("member3", 30, teamB);
        Member member4 = Member.createMember("member4", 40, teamB);
        em.persist(member1);
        em.persist(member2);
        em.persist(member3);
        em.persist(member4);

        MemberSearchCondition condition = new MemberSearchCondition();
        condition.setAgeGoe(35);
        condition.setAgeLoe(40);
        condition.setTeamName("teamB");

        List<MemberTeamDto> result = memberJpaRepository.findByBuilder(condition);

        assertEquals("member4", result.get(0).getUsername());
    }


}